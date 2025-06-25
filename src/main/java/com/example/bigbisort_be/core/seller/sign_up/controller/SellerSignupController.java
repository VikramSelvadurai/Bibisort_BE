package com.example.bigbisort_be.core.seller.sign_up.controller;

import com.example.bigbisort_be.core.seller.sign_up.request.SellerSignInRequestBean;
import com.example.bigbisort_be.core.seller.sign_up.request.SellerSignupRequestBean;
import com.example.bigbisort_be.core.seller.sign_up.response.SellerSignupResponseBean;
import com.example.bigbisort_be.core.seller.sign_up.service.SellerSignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerSignupController {
    private final SellerSignupService sellerSignupService;

    @PostMapping("/sign-up")
    public SellerSignupResponseBean sellerSignUp(@RequestBody SellerSignupRequestBean requestBean) {
        return sellerSignupService.sellerSignUp(requestBean);
    }

    @PostMapping("/sign-in")
    public Map<String,String> sellerSignIn(@RequestBody SellerSignInRequestBean sellerSignInRequestBean) {
        return sellerSignupService.sellerSignIn(sellerSignInRequestBean);
    }




}
