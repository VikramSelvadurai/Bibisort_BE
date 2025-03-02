package com.example.bigbisort_be.core.seller.sign_up.controller;

import com.example.bigbisort_be.core.seller.sign_up.request.SellerSignupRequestBean;
import com.example.bigbisort_be.core.seller.sign_up.response.SellerSignupResponseBean;
import com.example.bigbisort_be.core.seller.sign_up.service.SellerSignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerSignupController {
    private final SellerSignupService sellerSignupService;

    @GetMapping("/add")
    public SellerSignupResponseBean addSellerSignup(@RequestBody SellerSignupRequestBean requestBean) {
        return sellerSignupService.addSellerSignup(requestBean);
    }

    @GetMapping("/sign-in")
    public SellerSignupResponseBean sellerSignup(@RequestBody SellerSignupRequestBean requestBean) {
        return sellerSignupService.addSellerSignup(requestBean);
    }




}
