package com.example.bigbisort_be.seller.sign_up.service;

import com.example.bigbisort_be.seller.sign_up.request.SellerSignupRequestBean;
import com.example.bigbisort_be.seller.sign_up.response.SellerSignupResponseBean;

public interface SellerSignupService {
    SellerSignupResponseBean addSellerSignup(SellerSignupRequestBean requestBean);
}
