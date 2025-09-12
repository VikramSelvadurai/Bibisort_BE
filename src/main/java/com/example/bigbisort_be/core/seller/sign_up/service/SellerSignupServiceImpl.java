package com.example.bigbisort_be.core.seller.sign_up.service;

import com.example.bigbisort_be.common.Internationalization.Translator;
import com.example.bigbisort_be.common.MapBuilder.MapBuilder;
import com.example.bigbisort_be.common.constants.CommonConstants;
import com.example.bigbisort_be.common.enums.AuthenticationType;
import com.example.bigbisort_be.core.seller.sign_up.request.SellerSignInRequestBean;
import com.example.bigbisort_be.exception.EmailorPhoneAlreadyExistException;
import com.example.bigbisort_be.exception.InvalidCredentialsException;
import com.example.bigbisort_be.exception.UserNameAlreadyExistException;
import com.example.bigbisort_be.persistence.signup.seller_signup.entity.SellerSignupEntity;
import com.example.bigbisort_be.persistence.signup.seller_signup.model.SellerSignupRepositoryService;
import com.example.bigbisort_be.core.seller.sign_up.assembler.SellerSignupAssembler;
import com.example.bigbisort_be.core.seller.sign_up.request.SellerSignupRequestBean;
import com.example.bigbisort_be.core.seller.sign_up.response.SellerSignupResponseBean;
import com.example.bigbisort_be.persistence.signup.user.entity.UsersEntity;
import com.example.bigbisort_be.persistence.signup.user.model.UserRepositoryService;
import com.example.bigbisort_be.security.core.utils.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.example.bigbisort_be.core.contact.service.ContactServiceImpl.MESSAGE;


@Service
@RequiredArgsConstructor
public class SellerSignupServiceImpl implements SellerSignupService {

    private static final String USER_NAME_ALREADY_EXIST = "exception.auth.userName.already.exist";
    private static final String EMAIL_ALREADY_EXIST="exception.auth.email.already.exist";

    private final SellerSignupRepositoryService sellerSignupRepositoryService;
    private final UserRepositoryService userRepositoryService;
    private final SellerSignupAssembler sellerSignupAssembler;
    private final Translator translator;
    private final EncryptionUtils encryptionUtils;


    @Override
    public SellerSignupResponseBean sellerSignUp(SellerSignupRequestBean sellerSignupRequestBean) throws Exception {

        if(StringUtils.isNotEmpty(sellerSignupRequestBean.getEmail()) && StringUtils.isNotEmpty(sellerSignupRequestBean.getPhone())) {
            if(sellerSignupRepositoryService.existsByEmailIgnoreCaseOrPhone(sellerSignupRequestBean.getEmail(),sellerSignupRequestBean.getPhone())){
                throw new EmailorPhoneAlreadyExistException(translator.toLocale(EMAIL_ALREADY_EXIST));
            }
        }

        if(StringUtils.isNotEmpty(sellerSignupRequestBean.getUsername())) {
            if(userRepositoryService.existsByUserNameAndAuthenticationType(sellerSignupRequestBean.getUsername(), AuthenticationType.SELLER)){
                throw new UserNameAlreadyExistException(translator.toLocale(USER_NAME_ALREADY_EXIST,new String[]{sellerSignupRequestBean.getUsername()}));
            }
        }
        UsersEntity usersEntity = UsersEntity.builder()
                .authenticationType(AuthenticationType.SELLER)
                .email(sellerSignupRequestBean.getEmail())
                .name(sellerSignupRequestBean.getName())
                .userName(sellerSignupRequestBean.getUsername())
                .sPhrase(encryptionUtils.encrypt(sellerSignupRequestBean.getPassword()))
                .build();

        UsersEntity usersEntitySaved = userRepositoryService.save(usersEntity);

    SellerSignupEntity sellerSignupEntity =
        SellerSignupEntity.builder()
            .name(sellerSignupRequestBean.getName())
            .email(sellerSignupRequestBean.getEmail())
            .phone(sellerSignupRequestBean.getPhone())
            .zip(sellerSignupRequestBean.getZip())
            .state(sellerSignupRequestBean.getState())
            .city(sellerSignupRequestBean.getCity())
            .country(sellerSignupRequestBean.getCountry())
            .address(sellerSignupRequestBean.getCity())
            .usersEntity(usersEntitySaved)
            .build();
        return sellerSignupAssembler.toModel(sellerSignupRepositoryService.save(sellerSignupEntity));
    }

    @Override
    public Map<String,String> sellerSignIn(SellerSignInRequestBean sellerSignInRequestBean) {

        if (StringUtils.isNotEmpty(sellerSignInRequestBean.getMobileNumber())  && StringUtils.isNotEmpty(sellerSignInRequestBean.getOtp() )) {
            if (!sellerSignupRepositoryService.existsByPhone(sellerSignInRequestBean.getMobileNumber())) {
                throw new InvalidCredentialsException("Invalid Credentials");
            }
        }
        return MapBuilder.of(MESSAGE, CommonConstants.LOGIN_SUCCESSFULLY);
    }

}
