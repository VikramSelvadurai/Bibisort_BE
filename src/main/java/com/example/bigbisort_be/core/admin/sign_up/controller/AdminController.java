package com.example.bigbisort_be.core.admin.sign_up.controller;

import com.example.bigbisort_be.core.admin.sign_up.request.AdminLoginBean;
import com.example.bigbisort_be.core.admin.sign_up.service.AdminService;
import com.example.bigbisort_be.core.buyer.sign_up.request.BuyerSigninRequestBean;
import com.example.bigbisort_be.core.buyer.sign_up.response.BuyerInfoBean;
import com.example.bigbisort_be.exception.ResourceNotAvailableException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

//    @PostMapping("/sign-in")
//    public AdminLoginBean adminLogin(@RequestBody @Valid AdminLoginBean buyerSigninRequestBean) throws JsonProcessingException, ResourceNotAvailableException {
//        return adminService.buyerLogin(buyerSigninRequestBean);
//    }
}
