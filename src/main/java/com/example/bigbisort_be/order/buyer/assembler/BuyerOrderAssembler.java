package com.example.bigbisort_be.order.buyer.assembler;

import com.example.bigbisort_be.buyer.sign_up.response.BuyerInfoBean;
import com.example.bigbisort_be.buyer.sign_up.response.BuyerSignupResponseBean;
import com.example.bigbisort_be.order.buyer.bean.response.BuyerOrderResponseBean;
import com.example.bigbisort_be.persistence.order.buyer.entity.BuyerOrderEntity;
import com.example.bigbisort_be.persistence.product.entity.ProductsEntity;
import com.example.bigbisort_be.persistence.signup.buyer_signup.entity.BuyerEntity;
import com.example.bigbisort_be.product.assembler.ProductAssembler;
import com.example.bigbisort_be.product.bean.response.ProductResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class BuyerOrderAssembler implements RepresentationModelAssembler<BuyerOrderEntity, BuyerOrderResponseBean> {

    private final ProductAssembler productAssembler;
    @Override
    public BuyerOrderResponseBean toModel(BuyerOrderEntity entity) {
        return buildModel(entity);
    }

    @Override
    public CollectionModel<BuyerOrderResponseBean> toCollectionModel(Iterable<? extends BuyerOrderEntity> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }

    public BuyerOrderResponseBean buildModel(BuyerOrderEntity buyerOrderEntity) {
        BuyerEntity buyerEntity  = buyerOrderEntity.getBuyerEntity();
        Set<ProductResponseBean> productResponseBeanSet = new HashSet<>();
        for (ProductsEntity productsEntity : buyerOrderEntity.getProductsEntitySet()) {
            productResponseBeanSet.add(productAssembler.buildModel(productsEntity));
        }
        return BuyerOrderResponseBean.builder()
                .orderId(buyerOrderEntity.getId())
                .billingCompanyName(buyerOrderEntity.getBillingCompanyName())
                .shippingName(buyerOrderEntity.getShippingName())
                .orderDate(buyerOrderEntity.getOrderDate())
                .paymentMethod(buyerOrderEntity.getPaymentMethod())
                .estimationDateOfArrival(buyerOrderEntity.getEstimationDateOfArrival())
                .quantity(buyerOrderEntity.getQuantity())
                .buyerInfoBean(BuyerInfoBean.builder().name(buyerEntity.getName()).email(buyerEntity.getEmail()).phone(buyerEntity.getPhone()).build())
                .productResponseBeanSet(productResponseBeanSet)
                .build();
    }
}
