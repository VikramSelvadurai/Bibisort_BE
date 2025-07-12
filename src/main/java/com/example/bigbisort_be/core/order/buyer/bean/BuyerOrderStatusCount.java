package com.example.bigbisort_be.core.order.buyer.bean;

import com.example.bigbisort_be.core.order.buyer.enums.BuyerOrderStatusEnum;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@ToString
public class BuyerOrderStatusCount {

    private BuyerOrderStatusEnum status;
    private Long count;

    public BuyerOrderStatusCount(BuyerOrderStatusEnum status, Long count) {
        this.status = status;
        this.count = count;
    }

}

