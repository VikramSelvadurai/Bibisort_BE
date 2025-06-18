package com.example.bigbisort_be.core.seller.sign_up.service;

import com.example.bigbisort_be.core.seller.sign_up.request.SellerSignInRequestBean;
import com.example.bigbisort_be.core.seller.sign_up.request.SellerSignupRequestBean;
import com.example.bigbisort_be.core.seller.sign_up.response.SellerSignupResponseBean;

public interface SellerSignupService {
    SellerSignupResponseBean sellerSignUp(SellerSignupRequestBean requestBean);

    String sellerSignIn(SellerSignInRequestBean sellerSignInRequestBean);
}
