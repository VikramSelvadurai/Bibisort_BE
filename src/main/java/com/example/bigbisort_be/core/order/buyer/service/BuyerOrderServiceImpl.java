package com.example.bigbisort_be.core.order.buyer.service;

import com.example.bigbisort_be.core.order.buyer.assembler.BuyerOrderAssembler;
import com.example.bigbisort_be.core.order.buyer.bean.request.BuyerOrderRequestBean;
import com.example.bigbisort_be.core.order.buyer.bean.response.BuyerOrderResponseBean;
import com.example.bigbisort_be.exception.IdNotFoundException;
import com.example.bigbisort_be.persistence.order.buyer.entity.BuyerOrderEntity;
import com.example.bigbisort_be.persistence.order.buyer.model.BuyerOrderRepositoryService;
import com.example.bigbisort_be.persistence.product.entity.ProductsEntity;
import com.example.bigbisort_be.persistence.product.model.ProductsRepositoryService;
import com.example.bigbisort_be.persistence.signup.buyer_signup.entity.BuyerEntity;
import com.example.bigbisort_be.persistence.signup.buyer_signup.model.BuyerRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuyerOrderServiceImpl implements BuyerOrderService{

    private final BuyerRepositoryService buyerRepositoryService;
    private final ProductsRepositoryService productsRepositoryService;
    private final BuyerOrderRepositoryService buyerOrderRepositoryService;
    private final BuyerOrderAssembler buyerOrderAssembler;

    @Override
    public BuyerOrderResponseBean saveOrder(BuyerOrderRequestBean buyerOrderRequestBean) throws IdNotFoundException {

        BuyerEntity buyerEntity = buyerRepositoryService.findById(UUID.fromString(buyerOrderRequestBean.getBuyerId()));
        Set<ProductsEntity> productsEntitySet = productsRepositoryService.findAllByIdIsIn(buyerOrderRequestBean.getProductIds());
        BuyerOrderEntity buyerOrderEntity = BuyerOrderEntity.builder()
                .orderDate(buyerOrderRequestBean.getOrderDate())
                .billingCompanyName(buyerOrderRequestBean.getBillingCompanyName())
                .quantity(buyerOrderRequestBean.getQuantity())
                .shippingName(buyerOrderRequestBean.getShippingName())
                .paymentMethod(buyerOrderRequestBean.getPaymentMethod())
                .estimationDateOfArrival(buyerOrderRequestBean.getEstimationDateOfArrival())
                .buyerEntity(buyerEntity)
                .build();
        buyerOrderEntity.setProductsEntitySet(productsEntitySet);

        buyerOrderEntity = buyerOrderRepositoryService.save(buyerOrderEntity);

    return buyerOrderAssembler.toModel(buyerOrderEntity);
    }

    @Override
    public BuyerOrderResponseBean getBuyer(UUID buyerOrderId) throws IdNotFoundException {
       return buyerOrderAssembler.toModel(buyerOrderRepositoryService.findById(buyerOrderId));
    }
}
