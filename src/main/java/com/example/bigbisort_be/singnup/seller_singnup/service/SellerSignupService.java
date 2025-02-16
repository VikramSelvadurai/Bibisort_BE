package com.example.bigbisort_be.singnup.seller_singnup.service;

import com.example.bigbisort_be.singnup.seller_singnup.request.SellerSignupRequestBean;
import com.example.bigbisort_be.singnup.seller_singnup.response.SellerSignupResponseBean;

public interface SellerSignupService {
    SellerSignupResponseBean addSellerSignup(SellerSignupRequestBean requestBean);
}
