package com.example.bigbisort_be.authentication.service;

import com.example.bigbisort_be.authentication.bean.LoginRequestBean;
import com.example.bigbisort_be.authentication.bean.LoginResponseBean;
import com.example.bigbisort_be.common.enums.AuthenticationType;

import com.example.bigbisort_be.persistence.signup.user.entity.UsersEntity;
import com.example.bigbisort_be.persistence.signup.user.model.UserRepositoryService;
import com.example.bigbisort_be.security.core.utils.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepositoryService userRepositoryService;
    private final EncryptionUtils encryptionUtils;


    @Override
    public LoginResponseBean userLogin(LoginRequestBean loginRequestBean) throws Exception {
        AuthenticationType authenticationType = loginRequestBean.getAuthenticationType();
        boolean loginSuccess = false;
        if (StringUtils.isNotEmpty(loginRequestBean.getUsername() ) && StringUtils.isNotEmpty(loginRequestBean.getPassword() )) {
            UsersEntity usersEntity = userRepositoryService.findByUserNameAndAuthenticationType(loginRequestBean.getUsername(), authenticationType);
            if(loginRequestBean.getPassword().equals(encryptionUtils.decrypt(usersEntity.getSPhrase()))){
                loginSuccess = true;
            }

        }


//        switch (authenticationType){
//            case ADMIN ->
//            {
////                if (StringUtils.isNotEmpty(loginRequestBean.getUsername() ) && StringUtils.isNotEmpty(loginRequestBean.getPassword() )) {
////                    String password = Base64.getEncoder()
////                            .encodeToString(objectWriter.writeValueAsBytes(loginRequestBean.getPassword()));
////                    loginSuccess = adminRepositoryService.existsByUserNameAndPassword(loginRequestBean.getUsername(), password);
////                }
//            }
//            case BUYER -> {
////                if (StringUtils.isNotEmpty(loginRequestBean.getUsername() ) && StringUtils.isNotEmpty(loginRequestBean.getPassword() )) {
////                    String password = Base64.getEncoder()
////                            .encodeToString(objectWriter.writeValueAsBytes(loginRequestBean.getPassword()));
////                    loginSuccess = buyerRepositoryService.existsByUserNameIgnoreCaseAndPassword(loginRequestBean.getUsername(), password);
////                }
//            }
//            case SELLER -> {
////                if (StringUtils.isNotEmpty(loginRequestBean.getUsername()) && StringUtils.isNotEmpty(loginRequestBean.getPassword() )) {
////                 loginSuccess = sellerSignupRepository.existsByUsernameAndPhone(loginRequestBean.getUsername(), loginRequestBean.getPassword());
////                }
//            }
//
//        }
        if(loginSuccess){
            return LoginResponseBean.builder()
                    .message("Login success")
                    .roles(Arrays.asList(authenticationType.toString()))
                    .build();
        }
    return LoginResponseBean.builder()
            .message("Invalid username or password")
            .build();
    }
}

