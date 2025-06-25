//package com.example.bigbisort_be.security.core.jwt;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
////    @Bean
////    public AuthenticationProvider  authenticationProvider(AuthenticationConfiguration authenticationConfiguration) throws Exception {
////        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
////        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
////        daoAuthenticationProvider.setUserDetailsService();
////
////        return daoAuthenticationProvider;
////    }
//
//    @Bean
//    public SecurityFilterChain securityFilterAutoConfiguration(HttpSecurity httpSecurity) throws Exception {
//
//    return httpSecurity.csrf(customizer -> customizer.disable())
//            .authorizeHttpRequests(request -> request
//                    .requestMatchers("sign-in","sign-up").permitAll()
//                    .anyRequest().authenticated())
//            .httpBasic(Customizer.withDefaults())
//            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .build();
//    }
//}
