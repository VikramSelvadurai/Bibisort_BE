package com.example.bigbisort_be.singnup.seller_singnup.service;

import com.example.bigbisort_be.exception.EmailorPhoneAlreadyExistException;
import com.example.bigbisort_be.persistence.signup.buyer_signup.model.BuyerSignupRepositoryService;
import com.example.bigbisort_be.persistence.signup.seller_signup.entity.SellerSignupEntity;
import com.example.bigbisort_be.persistence.signup.seller_signup.model.SellerSignupRepository;
import com.example.bigbisort_be.persistence.signup.seller_signup.model.SellerSignupRepositoryService;
import com.example.bigbisort_be.singnup.seller_singnup.assembler.SellerSignupAssembler;
import com.example.bigbisort_be.singnup.seller_singnup.request.SellerSignupRequestBean;
import com.example.bigbisort_be.singnup.seller_singnup.response.SellerSignupResponseBean;
import org.springframework.stereotype.Service;

@Service
public class SellerSignupServiceImpl implements SellerSignupService {

    private SellerSignupRepositoryService sellerSignupRepositoryService;
    private SellerSignupAssembler sellerSignupAssembler;

    @Override
    public SellerSignupResponseBean addSellerSignup(SellerSignupRequestBean requestBean) {
        if((requestBean.getEmail()!=null && requestBean.getPhone()!=null) && (!requestBean.getEmail().isEmpty() && !requestBean.getPhone().isEmpty())){
            if(sellerSignupRepositoryService.existsByEmailIgnoreCaseOrPhone(requestBean.getEmail(),requestBean.getPhone())){
                throw new EmailorPhoneAlreadyExistException("Email or Phone number already exist, please choose another one");
            }
        }
    SellerSignupEntity sellerSignupEntity =
        SellerSignupEntity.builder()
            .name(requestBean.getName())
            .email(requestBean.getEmail())
            .phone(requestBean.getPhone())
            .zip(requestBean.getZip())
            .state(requestBean.getState())
            .city(requestBean.getCity())
            .country(requestBean.getCountry())
            .address(requestBean.getCity())
            .build();
        return sellerSignupAssembler.toModel(sellerSignupRepositoryService.save(sellerSignupEntity));
    }
}
