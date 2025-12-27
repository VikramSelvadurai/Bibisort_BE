package com.example.bigbisort_be.persistence.signup.seller_signup.model;

import com.example.bigbisort_be.authentication.bean.LoginRequestBean;
import com.example.bigbisort_be.persistence.signup.seller_signup.entity.SellerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SellerSignupRepositoryService {
    private final SellerSignupRepository sellerSignupRepository;

    public boolean existsByEmailIgnoreCaseOrPhone(String email, String phone) {
        return sellerSignupRepository.existsByEmailIgnoreCaseOrPhone(email,phone);
    }
    public boolean existsByPhone(String phone) {
        return sellerSignupRepository.existsByPhone(phone);
    }

    public SellerEntity save(SellerEntity sellerEntity) {
        return sellerSignupRepository.save(sellerEntity);
    }


    public boolean existsByUsernameAndPhone(String userName, String phone) {
        return sellerSignupRepository.existsByNameAndPhone(userName,phone);
    }
}
