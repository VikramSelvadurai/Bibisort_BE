package com.example.bigbisort_be.core.seller.sign_up.service;

import com.example.bigbisort_be.core.seller.sign_up.request.SellerSignInRequestBean;
import com.example.bigbisort_be.core.seller.sign_up.request.SellerSignupRequestBean;
import com.example.bigbisort_be.core.seller.sign_up.response.SellerSignupResponseBean;

import java.util.Map;

public interface SellerSignupService {
    SellerSignupResponseBean sellerSignUp(SellerSignupRequestBean requestBean) throws Exception;

    Map<String,String> sellerSignIn(SellerSignInRequestBean sellerSignInRequestBean);
}
