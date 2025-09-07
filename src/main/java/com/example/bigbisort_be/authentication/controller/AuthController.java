package com.example.bigbisort_be.authentication.controller;

import com.example.bigbisort_be.authentication.bean.LoginRequestBean;
import com.example.bigbisort_be.authentication.bean.LoginResponseBean;
import com.example.bigbisort_be.core.buyer.sign_up.request.BuyerSignupRequestBean;
import com.example.bigbisort_be.core.buyer.sign_up.response.BuyerSignupResponseBean;
import com.example.bigbisort_be.core.buyer.sign_up.service.BuyerSignupService;
import com.example.bigbisort_be.security.core.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtil;
    private final BuyerSignupService buyerSignupService;

    @PostMapping("/login")
    public LoginResponseBean login(@RequestBody LoginRequestBean loginRequestBean) {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestBean.getUsername(), loginRequestBean.getPassword()));
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

    @PostMapping("/sign-up")
    public BuyerSignupResponseBean buyerSignUp(@RequestBody BuyerSignupRequestBean buyerSignupRequestBean) throws Exception {
        return buyerSignupService.buyerSignUp(buyerSignupRequestBean);
    }
}
