package com.example.bigbisort_be.persistence.order.buyer.model;

import com.example.bigbisort_be.persistence.order.buyer.entity.BuyerOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BuyerOrderRepository extends JpaRepository<BuyerOrderEntity, UUID> {

    Optional<BuyerOrderEntity> findById(UUID uuid);
}
