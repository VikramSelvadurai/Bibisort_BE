package com.example.bigbisort_be.buyer.sign_up.service;

import com.example.bigbisort_be.buyer.sign_up.request.BuyerSigninRequestBean;
import com.example.bigbisort_be.buyer.sign_up.request.BuyerSignupRequestBean;
import com.example.bigbisort_be.buyer.sign_up.response.BuyerSignupResponseBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

@Component
public interface BuyerSignupService  {

    BuyerSignupResponseBean addBuyer(BuyerSignupRequestBean buyerSignupRequestBean);

    String buyerLogin(BuyerSigninRequestBean buyerSigninRequestBean) throws JsonProcessingException;

    boolean existUsername(String username);

    boolean existEmailorPhone(String username, String phone);

}
