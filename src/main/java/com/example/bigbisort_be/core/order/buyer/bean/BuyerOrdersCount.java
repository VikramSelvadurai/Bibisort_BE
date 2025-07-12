package com.example.bigbisort_be.core.order.buyer.bean;

import com.example.bigbisort_be.core.order.buyer.enums.BuyerOrderStatusEnum;
import lombok.*;

import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BuyerOrdersCount {
    private Map<BuyerOrderStatusEnum,Long> buyerOrderStatusCounts;
    private Long TotalOrders;

}
