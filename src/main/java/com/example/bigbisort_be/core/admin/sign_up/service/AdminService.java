package com.example.bigbisort_be.core.admin.sign_up.service;

import com.example.bigbisort_be.core.admin.sign_up.request.AdminSignupRequestBean;
import com.example.bigbisort_be.core.admin.sign_up.response.AdminSignupResponseBean;
import com.example.bigbisort_be.exception.UserNameAlreadyExistException;
import org.springframework.stereotype.Component;

@Component
public interface AdminService {
    AdminSignupResponseBean adminSignUp(AdminSignupRequestBean adminSignupRequestBean) throws Exception;

}
