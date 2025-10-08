//package com.example.bigbisort_be.security.core.mobile.service;
//
//import com.example.bigbisort_be.exception.PhoneNumberNotFoundException;
//import com.example.bigbisort_be.persistence.mobile.entity.OtpEntity;
//import com.example.bigbisort_be.persistence.mobile.model.OtpRepositoryService;
//import com.example.bigbisort_be.persistence.signup.user.entity.UsersEntity;
//import com.example.bigbisort_be.persistence.signup.user.model.UserRepository;
//import com.example.bigbisort_be.persistence.signup.user.model.UserRepositoryService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.security.SecureRandom;
//import java.time.Duration;
//import java.time.Instant;
//import java.util.Date;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class OtpService {
//
//        private final OtpRepositoryService otpRepositoryService;
//        private final UserRepositoryService userRepositoryService;
//        private final SmsSenderService smsSender; // use SMS now
//
//        private final Duration OTP_VALIDITY = Duration.ofMinutes(5);
//        private final SecureRandom random = new SecureRandom();
//
////        @Autowired
////        public OtpService(OtpRepository otpRepository, UserRepository userRepository, SmsSender smsSender) {
////            this.otpRepository = otpRepository;
////            this.userRepository = userRepository;
////            this.smsSender = smsSender;
////        }
//
//        public void requestOtp(String phoneNumber) {
//            UsersEntity usersEntity = userRepositoryService.findByPhoneNumber(phoneNumber);
//
//
//            String code = generateNumericOtp(6);
//            OtpEntity otp =OtpEntity.builder()
//                    .userName(usersEntity.getUserName())
//                    .code(code)
//                    .expiresAt(Date.from(Instant.now().plus(OTP_VALIDITY)))
//                    .build();
//
//            otpRepositoryService.save(otp);
//
//            smsSender.sendOtp(phoneNumber, code);
//        }
//
//        public boolean verifyOtp(String phoneNumber, String code) throws PhoneNumberNotFoundException {
//            OtpEntity otpEntity = otpRepositoryService.findByUsernameAndCodeAndUsedFalse(phoneNumber, code);
//            if (otpEntity.getExpiresAt().before(new Date()))
//                return false;
//            otpEntity.setUsed(true);
//            otpRepositoryService.save(otpEntity);
//            return true;
//        }
//
//        private String generateNumericOtp(int length) {
//            StringBuilder sb = new StringBuilder(length);
//            for (int i = 0; i < length; i++) {
//                sb.append(random.nextInt(10));
//            }
//            return sb.toString();
//        }
//    }
//
