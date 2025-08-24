package com.example.bigbisort_be.security.core.jwt;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


//    @Bean
//    public AuthenticationProvider  authenticationProvider(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
//        daoAuthenticationProvider.setUserDetailsService();
//
//        return daoAuthenticationProvider;
//    }

    @Bean
    public SecurityFilterChain securityFilterAutoConfiguration(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
//                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
//    public SecurityFilterChain securityFilterAutoConfiguration(HttpSecurity httpSecurity) throws Exception {
//
//    return httpSecurity
//            .csrf(customizer -> customizer.disable())
//            .authorizeHttpRequests(request -> request
////                    .requestMatchers("/bigbisort-imp-exp/buyer/sign-in").authenticated()
//                    .requestMatchers("bigbisort-imp-exp/product/**").authenticated()
//            )
//            .httpBasic(Customizer.withDefaults())
//            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .build();
//    }


//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http.csrf(AbstractHttpConfigurer::disable)
//            .authorizeHttpRequests(auth -> auth
//                    .requestMatchers("/bigbisort-imp-exp/product/product_info")
//                    .permitAll()
//                    .anyRequest())
////            .exceptionHandling(ex -> ex
////                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
////            )
//            .sessionManagement(session -> session
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            )
//            // CRITICAL: Add your JWT filter to the filter chain
////            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
//            .httpBasic(Customizer.withDefaults())
//            .headers(headers -> headers
//                    .contentSecurityPolicy(csp -> csp
//                            .policyDirectives("default-src 'self'; script-src 'self'; style-src 'self'; img-src 'self'; object-src 'self';"))
//                    .cacheControl(Customizer.withDefaults())
//                    .contentTypeOptions(Customizer.withDefaults())
//                    .frameOptions(Customizer.withDefaults())
//                    .httpStrictTransportSecurity(Customizer.withDefaults())
//                    .xssProtection(Customizer.withDefaults()));
//
//    return http.build();
//}
}
