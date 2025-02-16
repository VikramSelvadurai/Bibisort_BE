package com.example.bigbisort_be.persistence.signup.seller_signup.model;

import com.example.bigbisort_be.persistence.signup.seller_signup.entity.SellerSignupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SellerSignupRepository extends JpaRepository<SellerSignupEntity,UUID> {
    boolean existsByEmailIgnoreCaseOrPhone(String email, String phone);
}
