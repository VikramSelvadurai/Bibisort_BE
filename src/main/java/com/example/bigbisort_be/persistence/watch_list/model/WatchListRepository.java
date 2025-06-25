package com.example.bigbisort_be.persistence.watch_list.model;

import com.example.bigbisort_be.persistence.watch_list.entity.WatchListEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WatchListRepository extends JpaRepository<WatchListEntity, UUID> {


    Page<WatchListEntity> findAllByBuyerIdAndProductEntity_ProductNameContainsIgnoreCase(UUID buyerId, String productName, Pageable pageable);

    Page<WatchListEntity> findAllByBuyerId(UUID buyerId, Pageable pageable);

}
