package com.example.bigbisort_be.persistence.mobile.model;

import com.example.bigbisort_be.persistence.mobile.entity.OtpEntity;
import com.example.bigbisort_be.persistence.order.buyer.entity.BuyerOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity, UUID>, JpaSpecificationExecutor<OtpEntity>,
        PagingAndSortingRepository<OtpEntity, UUID> {

   Optional<OtpEntity> findByUserNameAndCodeAndUsedFalse(String phoneNumber, String code);

           //findByUsernameAndCodeAndUsedFalse
}
