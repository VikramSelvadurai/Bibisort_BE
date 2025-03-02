package com.example.bigbisort_be.core.order.buyer.bean.request;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BuyerOrderRequestBean {

    private Date orderDate;
    private String paymentMethod;
    private String billingCompanyName;
    private String quantity;
    private String shippingName;
    private String estimationDateOfArrival;
    private String buyerName;
    private String buyerId;
    private List<UUID> productIds;

}
