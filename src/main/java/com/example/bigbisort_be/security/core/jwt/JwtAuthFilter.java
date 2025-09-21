package com.example.bigbisort_be.security.core.jwt;

import com.example.bigbisort_be.common.bean.UserAuthenticationDetails;
import com.example.bigbisort_be.common.exception.APIError;
import com.example.bigbisort_be.persistence.signup.user.entity.UsersEntity;
import com.example.bigbisort_be.persistence.signup.user.model.UserRepositoryService;
import com.example.bigbisort_be.security.core.authentication.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final UserRepositoryService userRepositoryService;

    public JwtAuthFilter(JwtUtils jwtUtil, CustomUserDetailsService userDetailsService, UserRepositoryService userRepositoryService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        List<String> authorities = new ArrayList<>();
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            try {
                String token = authHeader.substring(7);
                String username = jwtUtil.extractUsername(token);
//                Claims claims = jwtUtil.extractAllClaims(token);
//                List<String> roles = claims.get("Roles", List.class);
//                userDetailsService.loadUserByUsername(username);

                log.error("Username is {}", username);
                UsersEntity usersEntity = userRepositoryService.findByUsername(username);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserAuthenticationDetails authenticationDetails =
                            UserAuthenticationDetails.builder()
                                    .userId(String.valueOf(usersEntity.getId()))
                                    .userName(usersEntity.getName())
                                    .firstName(usersEntity.getFirstName())
                                    .lastName(usersEntity.getLastName())
                                    .email(usersEntity.getEmail())
                                    .authenticationType(usersEntity.getAuthenticationType())
                                    .build();
                    boolean isExpired = jwtUtil.validateToken(token,username,requestURI);
                    log.error("Token is {}", isExpired);
                    List<SimpleGrantedAuthority> simpleGrantedAuthorities =
                            authorities.stream().map(SimpleGrantedAuthority::new).toList();
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    username, null, simpleGrantedAuthorities);
                    authToken.setDetails(authenticationDetails);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (ExpiredJwtException ex) {
                APIError apiError = APIError.builder()
                        .status(HttpServletResponse.SC_UNAUTHORIZED)
                        .timestamp(LocalDateTime.now().toString())
                        .error("Token expired")
                        .message("Token expired, please login again")
                        .path(request.getRequestURI())
                        .build();
                String jsonString = objectMapper.writeValueAsString(apiError);
                log.error("Json String :{}",jsonString);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                response.setContentType("application/json");
                response.getWriter().write(jsonString);
                return;
            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Invalid token\"}");
//                throw new ServletException(ex);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
