package com.example.bigbisort_be.order.buyer.bean.response;

import com.example.bigbisort_be.buyer.sign_up.response.BuyerInfoBean;
import com.example.bigbisort_be.product.bean.response.ProductResponseBean;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BuyerOrderResponseBean extends RepresentationModel<BuyerOrderResponseBean> {

    private UUID orderId;
    private Date orderDate;
    private String paymentMethod;
    private String billingCompanyName;
    private String quantity;
    private String shippingName;
    private String estimationDateOfArrival;
    private BuyerInfoBean buyerInfoBean;
    private Set<String> productNames;
    private Set<ProductResponseBean> productResponseBeanSet;
}
