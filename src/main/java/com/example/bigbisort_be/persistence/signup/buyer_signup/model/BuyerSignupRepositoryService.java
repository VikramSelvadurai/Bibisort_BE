package com.example.bigbisort_be.persistence.signup.buyer_signup.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuyerSignupRepositoryService{
    private final BuyerSignupRepository repository;

    public boolean existsByUsername(String username) {
        return repository.existsByUserNameIgnoreCase(username);
    }
    public boolean existsByUserNameIgnoreCaseAndPassword(String username,String password) {
        return repository.existsByUserNameIgnoreCaseAndPassword(username,password);
    }

    public boolean existsByEmailIgnoreCaseOrPhone(String email, String phone) {
        return repository.existsByEmailIgnoreCaseOrPhone(email,phone);
    }
}
