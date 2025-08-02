package com.example.bigbisort_be.core.buyer.sign_up.controller;

import com.example.bigbisort_be.core.buyer.sign_up.request.BuyerSigninRequestBean;
import com.example.bigbisort_be.core.buyer.sign_up.request.BuyerSignupRequestBean;
import com.example.bigbisort_be.core.buyer.sign_up.response.BuyerInfoBean;
import com.example.bigbisort_be.core.buyer.sign_up.response.BuyerSignupResponseBean;
import com.example.bigbisort_be.core.buyer.sign_up.service.BuyerSignupService;
import com.example.bigbisort_be.exception.IdNotFoundException;
import com.example.bigbisort_be.exception.ResourceNotAvailableException;
import com.example.bigbisort_be.exception.UserNameAlreadyExistException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class BuyerSignupController {

    private final BuyerSignupService buyerSignupService; ;

    @PostMapping("/sign-up")
    public BuyerSignupResponseBean buyerSignUp(@RequestBody BuyerSignupRequestBean buyerSignupRequestBean) throws UserNameAlreadyExistException {
        return buyerSignupService.buyerSignUp(buyerSignupRequestBean);
    }

    @PostMapping("/sign-in")
    public BuyerInfoBean buyerLogin(@RequestBody @Valid BuyerSigninRequestBean buyerSigninRequestBean) throws JsonProcessingException, ResourceNotAvailableException {
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
