package com.example.bigbisort_be.order.buyer.service;

import com.example.bigbisort_be.exception.IdNotFoundException;
import com.example.bigbisort_be.order.buyer.bean.request.BuyerOrderRequestBean;
import com.example.bigbisort_be.order.buyer.bean.response.BuyerOrderResponseBean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface BuyerOrderService {

    BuyerOrderResponseBean saveOrder(BuyerOrderRequestBean buyerOrderRequestBean) throws IdNotFoundException;

    BuyerOrderResponseBean getBuyer(UUID buyerOrderId) throws IdNotFoundException;
}
