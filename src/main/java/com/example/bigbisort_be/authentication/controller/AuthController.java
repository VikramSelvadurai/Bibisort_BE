package com.example.bigbisort_be.authentication.controller;

import com.example.bigbisort_be.authentication.bean.LoginRequestBean;
import com.example.bigbisort_be.authentication.bean.LoginResponseBean;
import com.example.bigbisort_be.core.admin.sign_up.request.AdminSignupRequestBean;
import com.example.bigbisort_be.core.admin.sign_up.response.AdminSignupResponseBean;
import com.example.bigbisort_be.core.admin.sign_up.service.AdminService;
import com.example.bigbisort_be.core.buyer.sign_up.request.BuyerSignupRequestBean;
import com.example.bigbisort_be.core.buyer.sign_up.response.BuyerSignupResponseBean;
import com.example.bigbisort_be.core.buyer.sign_up.service.BuyerSignupService;
import com.example.bigbisort_be.core.seller.sign_up.request.SellerSignupRequestBean;
import com.example.bigbisort_be.core.seller.sign_up.response.SellerSignupResponseBean;
import com.example.bigbisort_be.core.seller.sign_up.service.SellerSignupService;
import com.example.bigbisort_be.security.core.jwt.JwtUtils;
import com.example.bigbisort_be.security.core.twilio.bean.request.TwilioRequestBean;
import com.example.bigbisort_be.security.core.twilio.bean.request.TwilioVerifyOtpRequestBean;
import com.example.bigbisort_be.security.core.twilio.service.TwilioVerifyService;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.RequiredArgsConstructor;
import com.twilio.rest.verify.v2.service.Verification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtil;
    private final BuyerSignupService buyerSignupService;
    private final SellerSignupService sellerSignupService;
    private final AdminService adminService;
    private final TwilioVerifyService twilioVerifyService;


    @PostMapping("/login")
    public LoginResponseBean login(@RequestBody LoginRequestBean loginRequestBean) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestBean.getUsername()+":"+loginRequestBean.getAuthenticationType(), loginRequestBean.getPassword()));
            List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            return LoginResponseBean.builder()
                    .roles(roles)
                    .accessToken(jwtUtil.generateToken(loginRequestBean.getUsername(),roles))
                    .refreshToken(jwtUtil.generateRefreshToken(loginRequestBean.getUsername(),roles))
                    .build();
    }

    @PostMapping("/refresh-token")
    public LoginResponseBean generateRefresh(@RequestParam(name = "userName") String userName, @RequestParam(name = "roles") List<String> roles ) {
            String refreshToken = jwtUtil.generateRefreshToken(userName,roles);
            return LoginResponseBean.builder().refreshToken(refreshToken).roles(roles).build();
    }

    @PostMapping("/buyer/sign-up")
    public BuyerSignupResponseBean buyerSignUp(@RequestBody BuyerSignupRequestBean buyerSignupRequestBean) throws Exception {
        return buyerSignupService.buyerSignUp(buyerSignupRequestBean);
    }

    @PostMapping("/seller/sign-up")
    public SellerSignupResponseBean sellerSignUp(@RequestBody SellerSignupRequestBean sellerSignupRequestBean) throws Exception {
        return sellerSignupService.sellerSignUp(sellerSignupRequestBean);
    }

    @PostMapping("/admin/sign-up")
    public AdminSignupResponseBean sellerSignUp(@RequestBody AdminSignupRequestBean adminSignupRequestBean) throws Exception {
        return adminService.adminSignUp(adminSignupRequestBean);
    }

    @PostMapping("/send-otp")
    public Map<String,String> sendOtp( @RequestBody TwilioRequestBean req) {
        try {
            Verification v = twilioVerifyService.sendVerification(req.getPhoneNumber(), req.getChannel());
            return Map.of("message","Otp sent successfully");
        } catch (Exception ex) {
            return Map.of("error",ex.getMessage());
        }
    }

    @PostMapping("/validate-otp")
    public Map<String,String> checkOtp(@RequestBody TwilioVerifyOtpRequestBean req) {
        try {
            VerificationCheck check = twilioVerifyService.checkVerification(req.getPhoneNumber(), req.getCode());
            boolean approved = "approved".equalsIgnoreCase(check.getStatus());
            List<Map<String, Object>> failed= check.getSnaAttemptsErrorCodes();
            if (approved) {
                return Map.of("message","Otp verified successfully");
            }
            else{
                return Map.of("error","invalid otp or expired");
            }
        } catch (Exception ex) {
            return Map.of("error",ex.getMessage());
        }
    }

}
