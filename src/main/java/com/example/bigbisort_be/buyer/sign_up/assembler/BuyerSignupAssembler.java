package com.example.bigbisort_be.buyer.sign_up.assembler;

import com.example.bigbisort_be.buyer.sign_up.response.BuyerSignupResponseBean;
import com.example.bigbisort_be.persistence.signup.buyer_signup.entity.BuyerEntity;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class BuyerSignupAssembler implements RepresentationModelAssembler<BuyerEntity, BuyerSignupResponseBean> {

    @Override
    public BuyerSignupResponseBean toModel(BuyerEntity buyerEntity) {
        return buildModel(buyerEntity);
    }

    public BuyerSignupResponseBean buildModel(BuyerEntity buyerEntity) {
    return BuyerSignupResponseBean.builder()
        .name(buyerEntity.getName())
        .email(buyerEntity.getEmail())
        .address(buyerEntity.getAddress())
        .state(buyerEntity.getState())
        .zip(buyerEntity.getZip())
        .phone(buyerEntity.getPhone())
        .country(buyerEntity.getCountry())
        .city(buyerEntity.getCity())
        .build();
    }
}
