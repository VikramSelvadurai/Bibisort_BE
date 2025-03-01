package com.example.bigbisort_be.varieties.service;

import com.example.bigbisort_be.persistence.product.entity.ProductsEntity;
import com.example.bigbisort_be.persistence.varieties.entity.VarietiesEntity;
import com.example.bigbisort_be.persistence.varieties.model.VarietiesRepositoryService;
import com.example.bigbisort_be.varieties.assembler.VarietiesAssembler;
import com.example.bigbisort_be.varieties.bean.request.VarietiesRequestBean;
import com.example.bigbisort_be.varieties.bean.response.VarietiesResponseBean;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class VarietiesServiceImpl implements VarietiesService {

    private final VarietiesRepositoryService varietiesRepositoryService;
    private final VarietiesAssembler varietiesAssembler;

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
}
