package com.example.bigbisort_be.security.core.jwt;

import com.example.bigbisort_be.common.exception.APIError;
import com.example.bigbisort_be.security.core.authentication.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthFilter(JwtUtils jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            try {
                String token = authHeader.substring(7);
                String username = jwtUtil.extractUsername(token);
                log.error("Username is {}", username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    boolean isExpired = jwtUtil.validateToken(token,userDetails);
                    log.error("Token is {}", isExpired);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());

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
                return;
            }


        }
        chain.doFilter(request, response);
    }
}
