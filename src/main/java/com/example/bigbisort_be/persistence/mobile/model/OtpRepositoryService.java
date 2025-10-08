package com.example.bigbisort_be.persistence.mobile.model;

import com.example.bigbisort_be.common.Internationalization.Translator;
import com.example.bigbisort_be.exception.PhoneNumberNotFoundException;
import com.example.bigbisort_be.persistence.mobile.entity.OtpEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.bigbisort_be.common.constants.CommonConstants.PHONE_NUMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OtpRepositoryService {

    private final OtpRepository otpRepository;
    private final Translator translator;


    public OtpEntity findByUsernameAndCodeAndUsedFalse(String phoneNumber, String code) throws PhoneNumberNotFoundException {
        return otpRepository.findByUserNameAndCodeAndUsedFalse(phoneNumber, code).orElseThrow(() -> new PhoneNumberNotFoundException(translator.toLocale(PHONE_NUMBER_NOT_FOUND,new String[]{phoneNumber})));
    }

    public void save(OtpEntity otp) {
        otpRepository.save(otp);
    }
}
