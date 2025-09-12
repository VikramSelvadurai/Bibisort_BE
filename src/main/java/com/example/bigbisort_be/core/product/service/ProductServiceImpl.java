package com.example.bigbisort_be.core.product.service;

import com.example.bigbisort_be.core.product.assembler.ProductAssembler;
import com.example.bigbisort_be.core.product.bean.request.ProductRequestBean;
import com.example.bigbisort_be.core.product.utils.CriteriaUtils;
import com.example.bigbisort_be.exception.ProductAlreadyExistException;
import com.example.bigbisort_be.exception.ProductIdNotFoundException;
import com.example.bigbisort_be.persistence.product.entity.ProductEntity;
import com.example.bigbisort_be.persistence.product.model.ProductsRepository;
import com.example.bigbisort_be.persistence.product.model.ProductsRepositoryService;
import com.example.bigbisort_be.persistence.varieties.entity.VarietiesEntity;
import com.example.bigbisort_be.core.product.bean.request.ProductFilterRequestBean;
import com.example.bigbisort_be.core.product.bean.response.ProductResponseBean;
import com.example.bigbisort_be.core.varieties.assembler.VarietiesAssembler;
import com.example.bigbisort_be.core.varieties.service.VarietiesService;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_NAME = "productName";
    public static final String AUTHENTICATION_TYPE = "authentication_type";
    public static final String PRODUCT_CATEGORY = "category";
    public static final String VARIETIES_ENTITY = "varietiesEntitySet";
    private static final String LIKE_OPERATOR = "%";
    private final VarietiesAssembler varietiesAssembler;

    private final static String PRODUCT_ALREADY_EXIST ="Product already Exist ";

    private final VarietiesService varietiesService;
    private final ProductsRepositoryService productsRepositoryService;
    private final ProductsRepository productsRepository;
    private final ProductAssembler productAssembler;
    private final PagedResourcesAssembler<ProductEntity> pagedResourcesAssembler;


    @Override
    public CollectionModel<ProductResponseBean> addProduct(List<ProductRequestBean> productRequestBeanList) throws ProductAlreadyExistException {
        List<ProductEntity> productsEntities = new ArrayList<>();
        for (ProductRequestBean productRequestBean : productRequestBeanList) {
            if(productsRepositoryService.existsByProductNameIgnoreCase(productRequestBean.getProductName())){
                throw new ProductAlreadyExistException("Product '"+productRequestBean.getProductName()+"' already Exist");
            }
            ProductEntity productEntity = ProductEntity.builder()
                    .productName(productRequestBean.getProductName())
                    .description(productRequestBean.getDescription())
                    .quantity(productRequestBean.getQuantity())
                    .category(productRequestBean.getCategory())
                    .subcategory(productRequestBean.getSubcategory())
                    .image_url(productRequestBean.getImage_url())
                    .build();
            productEntity = productsRepositoryService.save(productEntity);
            if(CollectionUtils.isNotEmpty(productRequestBean.getVarietiesRequestBeanList())){
                Set<VarietiesEntity> varietiesEntitySet =varietiesService.addVarietiesWithProduct(productRequestBean.getVarietiesRequestBeanList(), productEntity).stream().collect(Collectors.toSet());
                productEntity.setVarietiesEntitySet(varietiesEntitySet);
                productEntity = productsRepositoryService.save(productEntity);
            }
            productsEntities.add(productEntity);
        }
        return productAssembler.toCollectionModel(productsEntities);
    }

    @Override
    public ProductResponseBean getProduct(UUID productId) throws ProductIdNotFoundException {
        ProductResponseBean productResponseBean = null;
        ProductEntity productsEntityOptional = productsRepositoryService.findById(productId);
            productResponseBean = productAssembler.toModel(productsEntityOptional);
        return productResponseBean;
    }

    @Override
    public PagedModel<ProductResponseBean> getProductFilter(ProductFilterRequestBean productFilterRequestBean, Pageable pageable) {

        PageRequest pageRequest =
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by(Sort.Direction.DESC, PRODUCT_NAME));
        Page<ProductEntity> productsEntityPage =
                productsRepository.findAll(
                        (root, query, criteriaBuilder) -> {
                            List<Predicate> predicates = new ArrayList<>();
                            productAuthenticationTypeCriteria(productFilterRequestBean.getAuthenticationType(),root, criteriaBuilder, predicates);
                            productNameCriteria(productFilterRequestBean.getProductName(), root, criteriaBuilder, predicates);
                            productCategoryCriteria(productFilterRequestBean.getCategory(), root, criteriaBuilder, predicates);
                            varietiesCriteria(productFilterRequestBean.getVarieties(), root, criteriaBuilder, predicates);
                            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                        },
                        pageRequest);

        return pagedResourcesAssembler.toModel(productsEntityPage,productAssembler);
        }

    private void varietiesCriteria(String varieties, Root<ProductEntity> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (!Objects.toString(varieties, "").equals("")) {
//            Join<ProductEntity, VarietiesEntity> varietyJoin = root.join("varietiesEntitySet", JoinType.LEFT);
//
//            log.error("Variety name :{}", criteriaBuilder.lower(varietyJoin.get("variety_name")));
//            predicates.add(
//                    criteriaBuilder.and(
//                            criteriaBuilder.like(
//                                    criteriaBuilder.lower(varietyJoin.get("variety_name")),
//                                    "%" + CriteriaUtils.escapeForLike(varieties).toLowerCase(Locale.ROOT) + "%")));
//        }
            final CriteriaQuery<ProductEntity> cq =
                    criteriaBuilder.createQuery(ProductEntity.class);
            final Subquery<UUID> subquery = cq.subquery(UUID.class);
            final Root<ProductEntity> productEntityRoot = subquery.from(ProductEntity.class);
            final Join<ProductEntity,VarietiesEntity> productEntityJoin =
                    productEntityRoot.join("varietiesEntitySet",JoinType.INNER);
            subquery
                    .select(productEntityRoot.get("id"))
                    .where(
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(productEntityJoin.get("varietyName")),
                                    LIKE_OPERATOR + CriteriaUtils.formatSearchText(varieties).toLowerCase(Locale.ROOT) + LIKE_OPERATOR));
            predicates.add(criteriaBuilder.in(root.get("id")).value(subquery));
        }

    }

    private void productNameCriteria(
            String searchText,
            Root<ProductEntity> root,
            CriteriaBuilder criteriaBuilder,
            List<Predicate> predicates) {
        if (!Objects.toString(searchText, "").equals("")) {
            predicates.add(
                    criteriaBuilder.and(
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get(PRODUCT_NAME)),
                                    "%" + CriteriaUtils.escapeForLike(searchText).toLowerCase(Locale.ROOT) + "%")));
        }
    }

    private void productAuthenticationTypeCriteria(
            String authenticationType,
            Root<ProductEntity> root,
            CriteriaBuilder criteriaBuilder,
            List<Predicate> predicates) {
        if (!Objects.toString(authenticationType, "").equals("")) {
            CriteriaQuery<ProductEntity> cq = criteriaBuilder.createQuery(ProductEntity.class);

            Predicate predicate = criteriaBuilder.equal(root.get("authenticationType"), authenticationType);
            cq.select(root).where(predicate);
            predicates.add(predicate);
        }
    }
    private void productCategoryCriteria(
            String searchText,
            Root<ProductEntity> root,
            CriteriaBuilder criteriaBuilder,
            List<Predicate> predicates) {
        if (!Objects.toString(searchText, "").equals("")) {
            predicates.add(
                    criteriaBuilder.and(
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get(PRODUCT_CATEGORY)),
                                    "%" + CriteriaUtils.escapeForLike(searchText).toLowerCase(Locale.ROOT) + "%")));
        }
    }

}
