package com.example.bigbisort_be.persistence.order.buyer.model;

import com.example.bigbisort_be.core.order.buyer.bean.BuyerOrderStatusCount;
import com.example.bigbisort_be.exception.IdNotFoundException;
import com.example.bigbisort_be.persistence.order.buyer.entity.BuyerOrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuyerOrderRepositoryService {
    private final BuyerOrderRepository buyerOrderRepository;

    public BuyerOrderEntity save(BuyerOrderEntity buyerOrderEntity) {
        return buyerOrderRepository.save(buyerOrderEntity);
    }

    public BuyerOrderEntity findById(UUID orderId) throws IdNotFoundException {
        return buyerOrderRepository.findById(orderId).orElseThrow(()-> new IdNotFoundException("BuyerOrder '"+orderId+"' not found"));
    }

    public List<BuyerOrderStatusCount> getBuyerOrderStatusCounts(UUID buyerId) throws IdNotFoundException {
        return buyerOrderRepository.getStatusCounts(buyerId);
    }

    public List<BuyerOrderStatusCount> getOrderStatusCounts() {
        return buyerOrderRepository.getStatusCounts();
    }

    public Page<BuyerOrderEntity> findByBuyerEntity_Id(UUID buyerId, Pageable pageable) throws IdNotFoundException {
        return buyerOrderRepository.findByBuyerEntity_Id(buyerId,pageable);
    }

    public Page<BuyerOrderEntity> findAll(Specification<BuyerOrderEntity> predicates, PageRequest pageRequest) {

        return buyerOrderRepository.findAll(predicates,pageRequest);
    }
}
