package com.example.bigbisort_be.core.seller.sign_up.service;

import com.example.bigbisort_be.common.MapBuilder.MapBuilder;
import com.example.bigbisort_be.common.constants.CommonConstants;
import com.example.bigbisort_be.core.seller.sign_up.request.SellerSignInRequestBean;
import com.example.bigbisort_be.exception.EmailorPhoneAlreadyExistException;
import com.example.bigbisort_be.exception.InvalidCredentialsException;
import com.example.bigbisort_be.persistence.signup.seller_signup.entity.SellerSignupEntity;
import com.example.bigbisort_be.persistence.signup.seller_signup.model.SellerSignupRepositoryService;
import com.example.bigbisort_be.core.seller.sign_up.assembler.SellerSignupAssembler;
import com.example.bigbisort_be.core.seller.sign_up.request.SellerSignupRequestBean;
import com.example.bigbisort_be.core.seller.sign_up.response.SellerSignupResponseBean;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.example.bigbisort_be.core.contact.service.ContactServiceImpl.MESSAGE;


@Service
@RequiredArgsConstructor
public class SellerSignupServiceImpl implements SellerSignupService {

    private final SellerSignupRepositoryService sellerSignupRepositoryService;
    private final SellerSignupAssembler sellerSignupAssembler;


    @Override
    public SellerSignupResponseBean sellerSignUp(SellerSignupRequestBean requestBean) {
        if(StringUtils.isNotEmpty(requestBean.getEmail()) && StringUtils.isNotEmpty(requestBean.getPhone())) {
            if(sellerSignupRepositoryService.existsByEmailIgnoreCaseOrPhone(requestBean.getEmail(),requestBean.getPhone())){
                throw new EmailorPhoneAlreadyExistException("Email or Phone number already exist, please choose another one");
            }
        }
    SellerSignupEntity sellerSignupEntity =
        SellerSignupEntity.builder()
            .name(requestBean.getName())
            .email(requestBean.getEmail())
            .phone(requestBean.getPhone())
            .zip(requestBean.getZip())
            .state(requestBean.getState())
            .city(requestBean.getCity())
            .country(requestBean.getCountry())
            .address(requestBean.getCity())
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

//    public SellerSigninResponsetBean sendOtp(SellerSigninRequestBean request) {
//        if (request.getMobileNumber() == null || request.getCountryCode() == null) {
//            return SellerSigninResponsetBean.builder()
//                    .status("FAILED")
//                    .message("Invalid Mobile Number or Country Code")
//                    .build();
//        }
//
//        String phoneNumber = request.getCountryCode() + request.getMobileNumber();
//        try {
////            Phone.getInstance().verifyPhoneNumber(phoneNumber, 60, java.util.concurrent.TimeUnit.SECONDS, null, null);
//            return SellerSigninResponsetBean.builder()
//                    .status("SUCCESS")
//                    .message("OTP Sent Successfully")
//                    .build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return SellerSigninResponsetBean.builder()
//                    .status("FAILED")
//                    .message("Failed to Send OTP")
//                    .build();
//        }
//    }

//    public SellerSigninResponsetBean verifyOtp(String otp) {
//        try {
//            // Verification logic using Firebase (to be customized with tokens)
//            return SellerSigninResponsetBean.builder()
//                    .status("SUCCESS")
//                    .message("OTP Verified Successfully")
//                    .build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return SellerSigninResponsetBean.builder()
//                    .status("FAILED")
//                    .message("Invalid OTP")
//                    .build();
//        }
//    }
}
