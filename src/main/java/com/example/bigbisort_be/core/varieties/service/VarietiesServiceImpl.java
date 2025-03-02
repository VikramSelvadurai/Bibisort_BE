package com.example.bigbisort_be.core.varieties.service;

import com.example.bigbisort_be.core.varieties.assembler.VarietiesAssembler;
import com.example.bigbisort_be.persistence.product.entity.ProductsEntity;
import com.example.bigbisort_be.persistence.varieties.entity.VarietiesEntity;
import com.example.bigbisort_be.persistence.varieties.model.VarietiesRepository;
import com.example.bigbisort_be.persistence.varieties.model.VarietiesRepositoryService;
import com.example.bigbisort_be.core.product.utils.CriteriaUtils;
import com.example.bigbisort_be.core.varieties.bean.request.VarietiesFilterRequestBean;
import com.example.bigbisort_be.core.varieties.bean.request.VarietiesRequestBean;
import com.example.bigbisort_be.core.varieties.bean.response.VarietiesResponseBean;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
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

@Service
@RequiredArgsConstructor
public class VarietiesServiceImpl implements VarietiesService {

    private static final String VARIETIES_NAME = "varietyName";
    private final VarietiesRepositoryService varietiesRepositoryService;
    private final VarietiesRepository varietiesRepository;
    private final VarietiesAssembler varietiesAssembler;
    private final PagedResourcesAssembler<VarietiesEntity> pagedResourcesAssembler;

    @Override
    public CollectionModel<VarietiesResponseBean> addVarieties(List<VarietiesRequestBean> varietiesRequestBeanList) {

        List<VarietiesEntity> varietiesEntityList = new ArrayList<>();
        for (VarietiesRequestBean varietiesRequestBean : varietiesRequestBeanList) {
            varietiesEntityList.add(VarietiesEntity.builder()
                    .varietyName(varietiesRequestBean.getVarietyName())
                    .description(varietiesRequestBean.getDescription())
                    .origin(varietiesRequestBean.getOrigin())
                    .availability(varietiesRequestBean.getAvailability())
                    .quality(varietiesRequestBean.getQuality())
                    .size(varietiesRequestBean.getSize())
                    .harvestSeason(varietiesRequestBean.getHarvestSeason())
                    .certifications(varietiesRequestBean.getCertifications())
                    .grower(varietiesRequestBean.getGrower())
                    .growingMethod(varietiesRequestBean.getGrowingMethod())
                    .build());
        }
        return varietiesAssembler.toCollectionModel(varietiesRepositoryService.saveAll(varietiesEntityList));
    }

    @Override
    @Transactional
    public List<VarietiesEntity> addVarietiesWithProduct(List<VarietiesRequestBean> varietiesRequestBeanList, ProductsEntity productsEntity) {

        List<VarietiesEntity> varietiesEntityList = new ArrayList<>();
        for (VarietiesRequestBean varietiesRequestBean : varietiesRequestBeanList) {
            varietiesEntityList.add(VarietiesEntity.builder()
                    .varietyName(varietiesRequestBean.getVarietyName())
                    .description(varietiesRequestBean.getDescription())
                    .origin(varietiesRequestBean.getOrigin())
                    .availability(varietiesRequestBean.getAvailability())
                    .quality(varietiesRequestBean.getQuality())
                    .size(varietiesRequestBean.getSize())
                    .harvestSeason(varietiesRequestBean.getHarvestSeason())
                    .certifications(varietiesRequestBean.getCertifications())
                    .grower(varietiesRequestBean.getGrower())
                    .growingMethod(varietiesRequestBean.getGrowingMethod())
                    .productsEntity(productsEntity)
                    .build());
        }
       return varietiesRepositoryService.saveAllWithProduct(varietiesEntityList);
    }

    @Override
    public VarietiesResponseBean getVarieties(UUID varietiesId) {
        VarietiesEntity varietiesEntity = null;
        Optional<VarietiesEntity> optionalVarieties = varietiesRepositoryService.findById(varietiesId);
        if(optionalVarieties.isPresent()){
          varietiesEntity =  optionalVarieties.get();
            VarietiesEntity.builder()
                    .varietyName(varietiesEntity.getVarietyName())
                    .description(varietiesEntity.getDescription())
                    .origin(varietiesEntity.getOrigin())
                    .availability(varietiesEntity.getAvailability())
                    .quality(varietiesEntity.getQuality())
                    .size(varietiesEntity.getSize())
                    .harvestSeason(varietiesEntity.getHarvestSeason())
                    .certifications(varietiesEntity.getCertifications())
                    .grower(varietiesEntity.getGrower())
                    .growingMethod(varietiesEntity.getGrowingMethod())
                    .build();
        }
        return Objects.nonNull(varietiesEntity)? varietiesAssembler.toModel(varietiesEntity) : VarietiesResponseBean.builder().build();
    }

    @Override
    public PagedModel<VarietiesResponseBean> getVarietiesFilter(VarietiesFilterRequestBean varietiesFilterRequestBean, Pageable pageable) {

        PageRequest pageRequest =
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by(Sort.Direction.DESC, VARIETIES_NAME));
        Page<VarietiesEntity> productsEntityPage =
                varietiesRepository.findAll(
                        (root, query, criteriaBuilder) -> {
                            List<Predicate> predicates = new ArrayList<>();
                            varietiesNameCriteria(varietiesFilterRequestBean.getVarietyName(), root, criteriaBuilder, predicates);
                            predicates.add(
                                    criteriaBuilder.in(root.get(VarietiesEntity.Fields.productsEntity).get(ProductsEntity.Fields.id))
                                            .value(varietiesFilterRequestBean.getProductId()));
                            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                        },
                        pageRequest);

        return pagedResourcesAssembler.toModel(productsEntityPage,varietiesAssembler);
    }

    private void varietiesNameCriteria(
            String searchText,
            Root<VarietiesEntity> root,
            CriteriaBuilder criteriaBuilder,
            List<Predicate> predicates) {
        if (!Objects.toString(searchText, "").equals("")) {
            predicates.add(
                    criteriaBuilder.and(
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get(VARIETIES_NAME)),
                                    "%" + CriteriaUtils.escapeForLike(searchText).toLowerCase(Locale.ROOT) + "%")));
        }
    }
//    private void productCategoryCriteria(
//            String searchText,
//            Root<VarietiesEntity> root,
//            CriteriaBuilder criteriaBuilder,
//            List<Predicate> predicates) {
//        if (!Objects.toString(searchText, "").equals("")) {
//            predicates.add(
//                    criteriaBuilder.and(
//                            criteriaBuilder.like(
//                                    criteriaBuilder.lower(root.get(PRODUCT_CATEGORY)),
//                                    "%" + CriteriaUtils.escapeForLike(searchText).toLowerCase(Locale.ROOT) + "%")));
//        }
//    }
}
