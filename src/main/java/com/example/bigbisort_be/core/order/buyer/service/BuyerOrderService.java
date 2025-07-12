package com.example.bigbisort_be.core.order.buyer.service;

import com.example.bigbisort_be.core.order.buyer.bean.BuyerOrderStatusCount;
import com.example.bigbisort_be.core.order.buyer.bean.BuyerOrdersCount;
import com.example.bigbisort_be.core.order.buyer.bean.request.BuyerOrderFilterRequestBean;
import com.example.bigbisort_be.core.order.buyer.bean.request.BuyerOrderRequestBean;
import com.example.bigbisort_be.core.order.buyer.bean.response.BuyerOrderResponseBean;
import com.example.bigbisort_be.core.order.buyer.enums.BuyerOrderStatusEnum;
import com.example.bigbisort_be.exception.IdNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public interface BuyerOrderService {

    BuyerOrderResponseBean saveOrder(BuyerOrderRequestBean buyerOrderRequestBean) throws IdNotFoundException;

    BuyerOrderResponseBean getBuyer(UUID orderId) throws IdNotFoundException;

    CollectionModel<BuyerOrderResponseBean> getOrderBasedOnBuyer(BuyerOrderFilterRequestBean buyerOrderFilterRequestBean, Pageable pageable) throws IdNotFoundException;

    Map<String,String > updateOrderStatus(UUID orderId, BuyerOrderStatusEnum buyerOrderStatusEnum) throws IdNotFoundException;

    BuyerOrdersCount getOrderStatusCount(UUID buyerId) throws IdNotFoundException;

}
