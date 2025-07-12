package com.example.bigbisort_be.core.order.buyer.controller;

import com.example.bigbisort_be.core.order.buyer.bean.BuyerOrdersCount;
import com.example.bigbisort_be.core.order.buyer.bean.request.BuyerOrderFilterRequestBean;
import com.example.bigbisort_be.core.order.buyer.bean.request.BuyerOrderRequestBean;
import com.example.bigbisort_be.core.order.buyer.bean.response.BuyerOrderResponseBean;
import com.example.bigbisort_be.core.order.buyer.enums.BuyerOrderStatusEnum;
import com.example.bigbisort_be.exception.IdNotFoundException;
import com.example.bigbisort_be.core.order.buyer.service.BuyerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/buyer-order")
@RequiredArgsConstructor
public class BuyerOrderController {

    private final BuyerOrderService buyerOrderService;

    @PostMapping("/save")
    public BuyerOrderResponseBean saveOrder(@RequestBody BuyerOrderRequestBean buyerOrderRequestBean) throws IdNotFoundException {
        return buyerOrderService.saveOrder(buyerOrderRequestBean);
    }

    @GetMapping("/{orderId}")
        public BuyerOrderResponseBean getBuyer(@PathVariable UUID orderId) throws IdNotFoundException {
        return buyerOrderService.getBuyer(orderId);
    }

    @PostMapping("/filter")
    public CollectionModel<BuyerOrderResponseBean> getOrderBasedOnBuyer(@RequestBody BuyerOrderFilterRequestBean buyerOrderFilterRequestBean, Pageable pageable) throws IdNotFoundException {
        return buyerOrderService.getOrderBasedOnBuyer(buyerOrderFilterRequestBean,pageable);
    }

    @PutMapping("/update/status/{orderId}")
    public Map<String ,String > updateOrderStatus(@PathVariable UUID orderId, @RequestParam BuyerOrderStatusEnum status) throws IdNotFoundException {
        return buyerOrderService.updateOrderStatus(orderId, status);
    }

    @GetMapping("/status/count")
    public BuyerOrdersCount getOrderStatusCount(@RequestParam(required = false) UUID buyerId) throws IdNotFoundException {
        return buyerOrderService.getOrderStatusCount(buyerId);
    }
}
