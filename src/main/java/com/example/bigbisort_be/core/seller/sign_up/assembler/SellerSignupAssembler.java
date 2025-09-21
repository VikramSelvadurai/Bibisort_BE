package com.example.bigbisort_be.core.seller.sign_up.assembler;

import com.example.bigbisort_be.core.seller.sign_up.response.SellerSignupResponseBean;
import com.example.bigbisort_be.persistence.signup.seller_signup.entity.SellerEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class SellerSignupAssembler implements RepresentationModelAssembler<SellerEntity, SellerSignupResponseBean> {
    @Override
    public SellerSignupResponseBean toModel(SellerEntity entity) {
        return buildModel(entity);
    }

    @Override
    public CollectionModel<SellerSignupResponseBean> toCollectionModel(Iterable<? extends SellerEntity> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }

    public SellerSignupResponseBean buildModel(SellerEntity sellerEntity){
        return SellerSignupResponseBean.builder()
                .sellerId(sellerEntity.getId())
                .name(sellerEntity.getName())
                .email(sellerEntity.getEmail())
                .address(sellerEntity.getAddress())
                .state(sellerEntity.getState())
                .zip(sellerEntity.getZip())
                .phone(sellerEntity.getPhone())
                .country(sellerEntity.getCountry())
                .city(sellerEntity.getCity())
                .build();

    }
}
