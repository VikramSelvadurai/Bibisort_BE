package com.example.bigbisort_be.core.seller.sign_up.service;

import com.example.bigbisort_be.exception.EmailorPhoneAlreadyExistException;
import com.example.bigbisort_be.persistence.signup.seller_signup.entity.SellerSignupEntity;
import com.example.bigbisort_be.persistence.signup.seller_signup.model.SellerSignupRepositoryService;
import com.example.bigbisort_be.core.seller.sign_up.assembler.SellerSignupAssembler;
import com.example.bigbisort_be.core.seller.sign_up.request.SellerSignupRequestBean;
import com.example.bigbisort_be.core.seller.sign_up.response.SellerSignupResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerSignupServiceImpl implements SellerSignupService {

    private final SellerSignupRepositoryService sellerSignupRepositoryService;
    private final SellerSignupAssembler sellerSignupAssembler;


    @Override
    public SellerSignupResponseBean addSellerSignup(SellerSignupRequestBean requestBean) {
        if((requestBean.getEmail()!=null && requestBean.getPhone()!=null) && (!requestBean.getEmail().isEmpty() && !requestBean.getPhone().isEmpty())){
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
