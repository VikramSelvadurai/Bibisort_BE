package com.example.bigbisort_be.core.product.service;

import com.example.bigbisort_be.core.product.assembler.ProductAssembler;
import com.example.bigbisort_be.core.product.bean.request.ProductRequestBean;
import com.example.bigbisort_be.core.product.utils.CriteriaUtils;
import com.example.bigbisort_be.exception.IdNotFoundException;
import com.example.bigbisort_be.exception.ProductAlreadyExistException;
import com.example.bigbisort_be.persistence.product.entity.ProductsEntity;
import com.example.bigbisort_be.persistence.product.model.ProductsRepository;
import com.example.bigbisort_be.persistence.product.model.ProductsRepositoryService;
import com.example.bigbisort_be.persistence.varieties.entity.VarietiesEntity;
import com.example.bigbisort_be.core.product.bean.request.ProductFilterRequestBean;
import com.example.bigbisort_be.core.product.bean.response.ProductResponseBean;
import com.example.bigbisort_be.core.varieties.assembler.VarietiesAssembler;
import com.example.bigbisort_be.core.varieties.service.VarietiesService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_NAME = "productName";
    public static final String PRODUCT_CATEGORY = "category";
    private final VarietiesAssembler varietiesAssembler;

    private final static String PRODUCT_ALREADY_EXIST ="Product already Exist ";

    private final VarietiesService varietiesService;
    private final ProductsRepositoryService productsRepositoryService;
    private final ProductsRepository productsRepository;
    private final ProductAssembler productAssembler;
    private final PagedResourcesAssembler<ProductsEntity> pagedResourcesAssembler;


    @Override
    public CollectionModel<ProductResponseBean> addProduct(List<ProductRequestBean> productRequestBeanList) throws ProductAlreadyExistException {
        List<ProductsEntity> productsEntities = new ArrayList<>();
        for (ProductRequestBean productRequestBean : productRequestBeanList) {
            if(productsRepositoryService.existsByProductNameIgnoreCase(productRequestBean.getProductName())){
                throw new ProductAlreadyExistException("Product '"+productRequestBean.getProductName()+"' already Exist");
            }
            ProductsEntity productsEntity = ProductsEntity.builder()
                    .productName(productRequestBean.getProductName())
                    .description(productRequestBean.getDescription())
                    .category(productRequestBean.getCategory())
                    .subcategory(productRequestBean.getSubcategory())
                    .image_url(productRequestBean.getImage_url())
                    .build();
            productsEntity = productsRepositoryService.save(productsEntity);
            if(!productRequestBean.getVarietiesRequestBeanList().isEmpty()){
                Set<VarietiesEntity> varietiesEntitySet =varietiesService.addVarietiesWithProduct(productRequestBean.getVarietiesRequestBeanList(),productsEntity).stream().collect(Collectors.toSet());
                productsEntity.setVarietiesEntitySet(varietiesEntitySet);
                productsEntity = productsRepositoryService.save(productsEntity);
            }
            productsEntities.add(productsEntity);
        }
        return productAssembler.toCollectionModel(productsEntities);
    }

    @Override
    public ProductResponseBean getProduct(UUID productId) throws IdNotFoundException {
        ProductResponseBean productResponseBean = null;
        Optional<ProductsEntity> productsEntityOptional = productsRepositoryService.findById(productId);
        if(productsEntityOptional.isPresent()){
            productResponseBean = productAssembler.toModel(productsEntityOptional.get());
        }else {
            throw new IdNotFoundException("Product '"+productId+"' not fount");
        }
        return productResponseBean;
    }

    @Override
    public PagedModel<ProductResponseBean> getProductFilter(ProductFilterRequestBean productFilterRequestBean, Pageable pageable) {

        PageRequest pageRequest =
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by(Sort.Direction.DESC, PRODUCT_NAME));
        Page<ProductsEntity> productsEntityPage =
                productsRepository.findAll(
                        (root, query, criteriaBuilder) -> {
                            List<Predicate> predicates = new ArrayList<>();
                            productNameCriteria(productFilterRequestBean.getProductName(), root, criteriaBuilder, predicates);
                            productCategoryCriteria(productFilterRequestBean.getCategory(), root, criteriaBuilder, predicates);
                            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                        },
                        pageRequest);

        return pagedResourcesAssembler.toModel(productsEntityPage,productAssembler);
        }

    private void productNameCriteria(
            String searchText,
            Root<ProductsEntity> root,
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
    private void productCategoryCriteria(
            String searchText,
            Root<ProductsEntity> root,
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
