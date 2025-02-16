package com.example.bigbisort_be.singnup.buyer_signup.controller;

import com.example.bigbisort_be.exception.UserNameAlreadyExistException;
import com.example.bigbisort_be.singnup.buyer_signup.request.BuyerSigninRequestBean;
import com.example.bigbisort_be.singnup.buyer_signup.request.BuyerSignupRequestBean;
import com.example.bigbisort_be.singnup.buyer_signup.response.BuyerSignupResponseBean;
import com.example.bigbisort_be.singnup.buyer_signup.service.BuyerSignupService;
import com.example.bigbisort_be.singnup.buyer_signup.service.BuyerSignupServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.Getter;
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
