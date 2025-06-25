package com.example.bigbisort_be.persistence.watch_list.model;

import com.example.bigbisort_be.persistence.watch_list.entity.WatchListEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WatchListRepositoryService {
    private final WatchListRepository watchListRepository;

    public void save(WatchListEntity watchListEntity) {
        watchListRepository.save(watchListEntity);
    }

    public Page<WatchListEntity> findAllByBuyerIdAndProductEntity_ProductNameContainsIgnoreCase(UUID buyerId, String productName, Pageable pageable) {
        return watchListRepository.findAllByBuyerIdAndProductEntity_ProductNameContainsIgnoreCase(buyerId, productName,pageable);
    }

    public Page<WatchListEntity> findAllByBuyerId(UUID buyerId, Pageable pageable) {
        return watchListRepository.findAllByBuyerId(buyerId,pageable);
    }
}
