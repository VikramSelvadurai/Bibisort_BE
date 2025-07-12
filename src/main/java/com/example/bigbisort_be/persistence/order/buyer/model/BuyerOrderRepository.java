package com.example.bigbisort_be.persistence.order.buyer.model;

import com.example.bigbisort_be.core.order.buyer.bean.BuyerOrderStatusCount;
import com.example.bigbisort_be.persistence.order.buyer.entity.BuyerOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BuyerOrderRepository extends JpaRepository<BuyerOrderEntity, UUID>, JpaSpecificationExecutor<BuyerOrderEntity>,
        PagingAndSortingRepository<BuyerOrderEntity, UUID> {

    Optional<BuyerOrderEntity> findById(UUID uuid);

    @Query("SELECT new com.example.bigbisort_be.core.order.buyer.bean.BuyerOrderStatusCount(bo.status, COUNT(bo)) FROM BuyerOrderEntity bo GROUP BY bo.status")
    List<BuyerOrderStatusCount> getStatusCounts();

    @Query("SELECT new com.example.bigbisort_be.core.order.buyer.bean.BuyerOrderStatusCount(bo.status, COUNT(bo)) FROM BuyerOrderEntity bo where bo.buyerEntity.id = ?1   GROUP BY bo.status")
    List<BuyerOrderStatusCount> getStatusCounts(UUID buyerId);

    Page<BuyerOrderEntity> findByBuyerEntity_Id(UUID uuid, Pageable pageable);
}


