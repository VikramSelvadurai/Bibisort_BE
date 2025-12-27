package com.example.bigbisort_be.authentication.service;

import com.example.bigbisort_be.authentication.bean.LoginRequestBean;
import com.example.bigbisort_be.authentication.bean.LoginResponseBean;
import org.springframework.stereotype.Component;

@Component
public interface AuthService {

    public LoginResponseBean userLogin(LoginRequestBean loginRequestBean) throws Exception;
}
