package com.example.bigbisort_be.buyer.sign_up.controller;

import com.example.bigbisort_be.buyer.sign_up.request.BuyerSigninRequestBean;
import com.example.bigbisort_be.buyer.sign_up.request.BuyerSignupRequestBean;
import com.example.bigbisort_be.buyer.sign_up.response.BuyerSignupResponseBean;
import com.example.bigbisort_be.buyer.sign_up.service.BuyerSignupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class BuyerSignupController {

    private final BuyerSignupService buyerSignupService; ;

    @PostMapping("/add")
    public BuyerSignupResponseBean addBuyer(@RequestBody BuyerSignupRequestBean buyerSignupRequestBean) {
        return buyerSignupService.addBuyer(buyerSignupRequestBean);
    }

    @PostMapping("/sign-in")
    public String buyerLogin(@RequestBody @Valid BuyerSigninRequestBean buyerSigninRequestBean) throws JsonProcessingException {
        return buyerSignupService.buyerLogin(buyerSigninRequestBean);
    }

    @GetMapping("/exist-username")
    public boolean existUsername(@RequestParam String username) {
        return buyerSignupService.existUsername(username);
    }
    @GetMapping("/email-phone-exist")
    public boolean existUsername(@RequestParam(name = "email",required = false) String username, @RequestParam(name = "phone",required = false) String phone) {
        return buyerSignupService.existEmailorPhone(username,phone);
    }
}
