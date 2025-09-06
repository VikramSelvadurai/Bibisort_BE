package com.example.bigbisort_be.core.buyer.sign_up.service;

import com.example.bigbisort_be.core.buyer.sign_up.request.BuyerSigninRequestBean;
import com.example.bigbisort_be.core.buyer.sign_up.request.BuyerSignupRequestBean;
import com.example.bigbisort_be.core.buyer.sign_up.response.BuyerInfoBean;
import com.example.bigbisort_be.core.buyer.sign_up.response.BuyerSignupResponseBean;
import com.example.bigbisort_be.exception.IdNotFoundException;
import com.example.bigbisort_be.exception.ResourceNotAvailableException;
import com.example.bigbisort_be.exception.UserNameAlreadyExistException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface BuyerSignupService  {

    BuyerSignupResponseBean buyerSignUp(BuyerSignupRequestBean buyerSignupRequestBean) throws Exception;

    BuyerInfoBean buyerLogin(BuyerSigninRequestBean buyerSigninRequestBean) throws JsonProcessingException, ResourceNotAvailableException;

    boolean existUsername(String username);

    boolean existEmailorPhone(String username, String phone);

}
