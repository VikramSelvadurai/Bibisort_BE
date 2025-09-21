package com.example.bigbisort_be.core.admin.sign_up.assembler;

import com.example.bigbisort_be.core.admin.sign_up.response.AdminSignupResponseBean;
import com.example.bigbisort_be.persistence.admin.entity.AdminEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AdminSignupAssembler implements RepresentationModelAssembler<AdminEntity, AdminSignupResponseBean> {
    @Override
    public AdminSignupResponseBean toModel(AdminEntity entity) {
        return buildModel(entity);
    }

    @Override
    public CollectionModel<AdminSignupResponseBean> toCollectionModel(Iterable<? extends AdminEntity> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }

    public AdminSignupResponseBean buildModel(AdminEntity adminEntity) {
        return AdminSignupResponseBean.builder()
                .name(adminEntity.getName())
                .email(adminEntity.getEmail())
                .username(adminEntity.getUserName())
//                .address(buyerEntity.getAddress())
//                .state(buyerEntity.getState())
//                .zip(buyerEntity.getZip())
//                .phone(buyerEntity.getPhone())
//                .country(buyerEntity.getCountry())
//                .city(buyerEntity.getCity())
                .build();
    }
}
