package com.example.bigbisort_be.persistence.signup.seller_signup.model;

import com.example.bigbisort_be.persistence.signup.seller_signup.entity.SellerSignupEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SellerSignupRepositoryService {
    private final SellerSignupRepository sellerSignupRepository;

    public boolean existsByEmailIgnoreCaseOrPhone(String email, String phone) {
        return sellerSignupRepository.existsByEmailIgnoreCaseOrPhone(email,phone);
    }

    public SellerSignupEntity save(SellerSignupEntity sellerSignupEntity) {
        return sellerSignupRepository.save(sellerSignupEntity);
    }
}
