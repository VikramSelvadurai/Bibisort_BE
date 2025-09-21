package com.example.bigbisort_be.core.admin.sign_up.service;

import com.example.bigbisort_be.common.Internationalization.Translator;
import com.example.bigbisort_be.common.enums.AuthenticationType;
import com.example.bigbisort_be.core.admin.sign_up.assembler.AdminSignupAssembler;
import com.example.bigbisort_be.core.admin.sign_up.request.AdminSignupRequestBean;
import com.example.bigbisort_be.core.admin.sign_up.response.AdminSignupResponseBean;
import com.example.bigbisort_be.exception.UserNameAlreadyExistException;
import com.example.bigbisort_be.persistence.admin.entity.AdminEntity;
import com.example.bigbisort_be.persistence.admin.repository.AdminRepositoryService;
import com.example.bigbisort_be.persistence.signup.user.entity.UsersEntity;
import com.example.bigbisort_be.persistence.signup.user.model.UserRepositoryService;
import com.example.bigbisort_be.security.core.utils.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import static com.example.bigbisort_be.common.constants.CommonConstants.USERNAME_ALREADY_EXIST;
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {


    private final AdminRepositoryService adminRepositoryService;
    private final UserRepositoryService userRepositoryService;
    private final EncryptionUtils encryptionUtils;
    private final AdminSignupAssembler adminSignupAssembler;
    private final Translator translator;

    @Override
    public AdminSignupResponseBean adminSignUp(AdminSignupRequestBean adminSignupRequestBean) throws Exception {

        if(StringUtils.isNotEmpty(adminSignupRequestBean.getUserName()) && StringUtils.isNotEmpty(adminSignupRequestBean.getPassword())){
            if(userRepositoryService.existsByUserNameAndAuthenticationType(adminSignupRequestBean.getUserName(), AuthenticationType.ADMIN)){
                throw new UserNameAlreadyExistException(translator.toLocale(USERNAME_ALREADY_EXIST, new String[] {adminSignupRequestBean.getUserName()}));
            }
        }

        UsersEntity usersEntity = UsersEntity.builder()
                .authenticationType(AuthenticationType.ADMIN)
                .email(adminSignupRequestBean.getEmail())
                .name(adminSignupRequestBean.getName())
                .userName(adminSignupRequestBean.getUserName())
                .sPhrase(encryptionUtils.encrypt(adminSignupRequestBean.getPassword()))
                .build();
        UsersEntity usersEntitySaved = userRepositoryService.save(usersEntity);


        AdminEntity adminEntity = adminRepositoryService.save(
                AdminEntity.builder()
                .email(adminSignupRequestBean.getEmail())
                .name(adminSignupRequestBean.getName())
                        .userName(adminSignupRequestBean.getUserName())
                .email(adminSignupRequestBean.getEmail())
                        .usersEntity(usersEntitySaved)
                .build());
       return adminSignupAssembler.toModel(adminEntity);
    }
}
