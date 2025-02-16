package com.example.bigbisort_be.singnup.buyer_signup.service;

import com.example.bigbisort_be.exception.UserNameAlreadyExistException;
import com.example.bigbisort_be.singnup.buyer_signup.request.BuyerSigninRequestBean;
import com.example.bigbisort_be.singnup.buyer_signup.request.BuyerSignupRequestBean;
import com.example.bigbisort_be.singnup.buyer_signup.response.BuyerSignupResponseBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

@Component
public interface BuyerSignupService  {

    BuyerSignupResponseBean addBuyer(BuyerSignupRequestBean buyerSignupRequestBean);

    String buyerLogin(BuyerSigninRequestBean buyerSigninRequestBean) throws JsonProcessingException;

    boolean existUsername(String username);

    boolean existEmailorPhone(String username, String phone);

}
