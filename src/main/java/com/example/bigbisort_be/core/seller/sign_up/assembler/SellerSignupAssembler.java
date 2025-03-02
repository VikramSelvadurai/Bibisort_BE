package com.example.bigbisort_be.core.seller.sign_up.assembler;

import com.example.bigbisort_be.core.seller.sign_up.response.SellerSignupResponseBean;
import com.example.bigbisort_be.persistence.signup.seller_signup.entity.SellerSignupEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class SellerSignupAssembler implements RepresentationModelAssembler<SellerSignupEntity, SellerSignupResponseBean> {
    @Override
    public SellerSignupResponseBean toModel(SellerSignupEntity entity) {
        return buildModel(entity);
    }

    @Override
    public CollectionModel<SellerSignupResponseBean> toCollectionModel(Iterable<? extends SellerSignupEntity> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }

    public SellerSignupResponseBean buildModel(SellerSignupEntity sellerSignupEntity){
        return SellerSignupResponseBean.builder()
                .name(sellerSignupEntity.getName())
                .email(sellerSignupEntity.getEmail())
                .address(sellerSignupEntity.getAddress())
                .state(sellerSignupEntity.getState())
                .zip(sellerSignupEntity.getZip())
                .phone(sellerSignupEntity.getPhone())
                .country(sellerSignupEntity.getCountry())
                .city(sellerSignupEntity.getCity())
                .build();

    }
}
