package com.example.bigbisort_be.singnup.buyer_signup.assembler;

import com.example.bigbisort_be.contact.response.ContactResponseBean;
import com.example.bigbisort_be.persistence.contact.model.entity.ContactEntity;
import com.example.bigbisort_be.persistence.signup.buyer_signup.entity.BuyerSignupEntity;
import com.example.bigbisort_be.singnup.buyer_signup.response.BuyerSignupResponseBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class BuyerSignupAssembler implements RepresentationModelAssembler<BuyerSignupEntity, BuyerSignupResponseBean> {

    @Override
    public BuyerSignupResponseBean toModel(BuyerSignupEntity buyerSignupEntity) {
        return buildModel(buyerSignupEntity);
    }

    public BuyerSignupResponseBean buildModel(BuyerSignupEntity buyerSignupEntity) {
    return BuyerSignupResponseBean.builder()
        .name(buyerSignupEntity.getName())
        .email(buyerSignupEntity.getEmail())
        .address(buyerSignupEntity.getAddress())
        .state(buyerSignupEntity.getState())
        .zip(buyerSignupEntity.getZip())
        .phone(buyerSignupEntity.getPhone())
        .country(buyerSignupEntity.getCountry())
        .city(buyerSignupEntity.getCity())
        .build();
    }
}
