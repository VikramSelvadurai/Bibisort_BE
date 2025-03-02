package com.example.bigbisort_be.core.buyer.order.bean.request;

import lombok.*;

import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequestBean {

//    Order Table
    private UUID productId;
    private String productName;
    private String variety;
    private String orderDate;
    private String paymentMethod;
    private String billingCompanyName;
    private String quantity;
    private String shippingName;
    private String estimationDateOfArrival;
}
