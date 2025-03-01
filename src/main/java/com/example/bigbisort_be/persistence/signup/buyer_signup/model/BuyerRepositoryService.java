package com.example.bigbisort_be.persistence.signup.buyer_signup.model;

import com.example.bigbisort_be.exception.IdNotFoundException;
import com.example.bigbisort_be.persistence.signup.buyer_signup.entity.BuyerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BuyerRepositoryService {
    private final BuyerRepository repository;

    public boolean existsByUsername(String username) {
        return repository.existsByUserNameIgnoreCase(username);
    }
    public boolean existsByUserNameIgnoreCaseAndPassword(String username,String password) {
        return repository.existsByUserNameIgnoreCaseAndPassword(username,password);
    }

    public boolean existsByEmailIgnoreCaseOrPhone(String email, String phone) {
        return repository.existsByEmailIgnoreCaseOrPhone(email,phone);
    }

    public BuyerEntity findById(UUID buyerId) throws IdNotFoundException {
        return repository.findById(buyerId).orElseThrow(() -> new IdNotFoundException("Buyer '"+buyerId + "' Not found"));
    }
}
