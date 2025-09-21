package com.example.bigbisort_be.persistence.signup.seller_signup.model;

import com.example.bigbisort_be.persistence.signup.seller_signup.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SellerSignupRepository extends JpaRepository<SellerEntity,UUID> {
    boolean existsByEmailIgnoreCaseOrPhone(String email, String phone);

    boolean existsByPhone(String phone);

}
