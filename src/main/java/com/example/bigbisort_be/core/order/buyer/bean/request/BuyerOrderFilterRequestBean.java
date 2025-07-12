package com.example.bigbisort_be.core.order.buyer.bean.request;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BuyerOrderFilterRequestBean {

    private String billingCompanyName;
    private String shippingName;
    private String buyerName;
    private String buyerId;
    private String orderId;
}
