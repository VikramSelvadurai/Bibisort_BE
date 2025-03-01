package com.example.bigbisort_be.order.buyer.controller;

import com.example.bigbisort_be.exception.IdNotFoundException;
import com.example.bigbisort_be.order.buyer.bean.request.BuyerOrderRequestBean;
import com.example.bigbisort_be.order.buyer.bean.response.BuyerOrderResponseBean;
import com.example.bigbisort_be.order.buyer.service.BuyerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{buyerOrderId}")
    public BuyerOrderResponseBean getBuyer(@RequestParam UUID buyerOrderId) throws IdNotFoundException {
        return buyerOrderService.getBuyer(buyerOrderId);
    }
}
