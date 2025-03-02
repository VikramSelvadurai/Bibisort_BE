package com.example.bigbisort_be.core.varieties.assembler;

import com.example.bigbisort_be.persistence.varieties.entity.VarietiesEntity;
import com.example.bigbisort_be.core.varieties.bean.response.VarietiesResponseBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VarietiesAssembler implements RepresentationModelAssembler<VarietiesEntity, VarietiesResponseBean> {
    @Override
    public VarietiesResponseBean toModel(VarietiesEntity entity) {
        return buildModel(entity);
    }

    @Override
    public CollectionModel<VarietiesResponseBean> toCollectionModel(Iterable<? extends VarietiesEntity> entities) {
        List<VarietiesResponseBean>  varietiesResponseBeanList = new ArrayList<>();
        for (VarietiesEntity entity : entities) {
            varietiesResponseBeanList.add(toModel(entity));
        }
        return CollectionModel.of(varietiesResponseBeanList);
    }

    public List<VarietiesResponseBean> toLists(Iterable<? extends VarietiesEntity> entities) {
        List<VarietiesResponseBean>  varietiesResponseBeanList = new ArrayList<>();
        for (VarietiesEntity entity : entities) {
            varietiesResponseBeanList.add(toModel(entity));
        }
        return varietiesResponseBeanList;
    }

    public VarietiesResponseBean buildModel(VarietiesEntity varietiesEntity){

        return VarietiesResponseBean.builder()
                .varietyName(varietiesEntity.getVarietyName())
                .description(varietiesEntity.getDescription())
                .availability(varietiesEntity.getAvailability())
                .varietyId(varietiesEntity.getId())
                .quality(varietiesEntity.getQuality())
                .harvestDate(varietiesEntity.getHarvestDate())
                .size(varietiesEntity.getSize())
                .certifications(varietiesEntity.getCertifications())
                .origin(varietiesEntity.getOrigin())
                .growingMethod(varietiesEntity.getGrowingMethod())
                .grower(varietiesEntity.getGrower())
                .build();
    }
}
