package com.example.bigbisort_be.security.core.jwt;

import com.example.bigbisort_be.common.enums.AuthenticationType;
import com.example.bigbisort_be.security.constants.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private static final String SECRET_KEY_GEN_KEY ="zjYeLRusmijLxsJZBysd7bHQeoR30uP9";
    private static final long EXPIRATION_MS = 1000 * 60 * 30; // 30min

    private final SecretKey key;

    public JwtUtils() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY_GEN_KEY.getBytes());;
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key)   // 🔹 same key used here
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)  // 🔹 and the same key used here
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

//        public static SecretKey generateSecretKey() {
//        return Keys.hmacShaKeyFor(SECRET_KEY_GEN_KEY.getBytes(StandardCharsets.UTF_8));
//    }
//    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY_GEN_KEY.getBytes());

//    public String generateToken(String username) {
//        return Jwts.builder()
//                .subject(username)
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
//                .signWith(key)
//                .compact();
//    }
//
//    public String extractUsername(String token) {
//        return Jwts.parser()
//                .verifyWith(generateSecretKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload()
//                .getSubject();
//    }

//
//
//
//    public String generateToken(String username, AuthenticationType authenticationType) {
//
//        Claims claims = Jwts.claims().setSubject(username).build();
//        claims.put(JwtConstants.ApiResponseFields.USER_NAME, username);
//        claims.put(JwtConstants.ApiResponseFields.AUTH_TYPE, "IN_MEMORY");
//        claims.put("SIGN_IN_TYPE", authenticationType);
//
//
//        return Jwts.builder()
//                .claims(claims)
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 + 24))
//                .signWith(generateSecretKey(), Jwts.SIG.HS256)
//                .compact();
//    }

//    public JwtToken generateAccessJwtTokenForConfig(AuthUserDetails userDetails) throws KeyNotAvailableException {
//        ServerException.throwIfTrue(
//                userDetails == null,
//                FailureMessages.MISSING_METHOD_PARAMETER.apply(ApiResponseFields.USER_DETAILS));
//        /**
//         * ServerException.throwIfTrue(StringUtils.hasText(userDetails.getUsername()),
//         * FailureMessages.JWT_TOKEN_GEN_FAILED_MISSING_USERNAME);
//         */
//
//        final UserModelMapperBean userModel = userDetails.getUserModel();
//        Claims claims = Jwts.claims().setSubject(userModel.getUserName());
//        claims.put(JwtConstants.ApiResponseFields.USER_ID, userModel.getUserName());
//        claims.put(ApiResponseFields.AUTH_TYPE, "SAML");
//        UserDetailsBean userDetailsBean =
//                UserDetailsBean.builder().userId(systemEncryptionUtils.encrypt(userModel.getId())).build();
//        claims.put(JwtConstants.ApiResponseFields.USER_DETAILS, userDetailsBean);
//        LocalDateTime currentTime = LocalDateTime.now();
//        String tokenSigningKey = redisRepository.getActiveKey();
//        return new JwtAccessToken(
//                Jwts.builder()
//                        .setClaims(claims)
//                        .setIssuer(tokenIssuer)
//                        .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
//                        .setExpiration(
//                                Date.from(
//                                        currentTime.plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant()))
//                        .signWith(SignatureAlgorithm.HS512, tokenSigningKey.getBytes())
//                        .compact());
//    }


}
