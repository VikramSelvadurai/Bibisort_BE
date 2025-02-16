package com.example.bigbisort_be.singnup.seller_singnup.controller;

import com.example.bigbisort_be.singnup.seller_singnup.request.SellerSignupRequestBean;
import com.example.bigbisort_be.singnup.seller_singnup.response.SellerSignupResponseBean;
import com.example.bigbisort_be.singnup.seller_singnup.service.SellerSignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class SellerSignupController {
    private SellerSignupService sellerSignupService;

    @GetMapping("/add")
    public SellerSignupResponseBean addSellerSignup(SellerSignupRequestBean requestBean) {
        return sellerSignupService.addSellerSignup(requestBean);
    }


}
