package com.example.bigbisort_be.persistence.signup.buyer_signup.model;

import com.example.bigbisort_be.persistence.signup.buyer_signup.entity.BuyerSignupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BuyerSignupRepository extends JpaRepository<BuyerSignupEntity,UUID> {

    boolean existsByUserNameIgnoreCase(String username);

    boolean existsByUserNameIgnoreCaseAndPassword(String username, String password);

    boolean existsByEmailIgnoreCaseOrPhone(String email, String phone);

}
