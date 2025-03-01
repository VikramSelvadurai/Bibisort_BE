package com.example.bigbisort_be.persistence.signup.buyer_signup.model;

import com.example.bigbisort_be.persistence.signup.buyer_signup.entity.BuyerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.crypto.spec.OAEPParameterSpec;
import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BuyerRepository extends JpaRepository<BuyerEntity,UUID> {

    boolean existsByUserNameIgnoreCase(String username);

    boolean existsByUserNameIgnoreCaseAndPassword(String username, String password);

    boolean existsByEmailIgnoreCaseOrPhone(String email, String phone);

    Optional<BuyerEntity> findById(UUID buyerId);

}
