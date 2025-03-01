package com.example.bigbisort_be.persistence.order.buyer.model;

import com.example.bigbisort_be.exception.IdNotFoundException;
import com.example.bigbisort_be.persistence.order.buyer.entity.BuyerOrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuyerOrderRepositoryService {
    private final BuyerOrderRepository buyerOrderRepository;

    public BuyerOrderEntity save(BuyerOrderEntity buyerOrderEntity) {
        return buyerOrderRepository.save(buyerOrderEntity);
    }

    public BuyerOrderEntity findById(UUID buyerOrderId) throws IdNotFoundException {
        return buyerOrderRepository.findById(buyerOrderId).orElseThrow(()-> new IdNotFoundException("BuyerOrder '"+buyerOrderId+"' not found"));
    }

}
