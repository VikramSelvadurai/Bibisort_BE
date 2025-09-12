package com.example.bigbisort_be.security.core.authentication;

import com.example.bigbisort_be.persistence.signup.user.entity.UsersEntity;
import com.example.bigbisort_be.persistence.signup.user.model.UserRepositoryService;
import com.example.bigbisort_be.security.core.utils.EncryptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryService userRepositoryService;
    private final EncryptionUtils encryptionUtils;
    @Autowired
    private  @Lazy PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserRepositoryService userRepositoryService, EncryptionUtils encryptionUtils) {
        this.userRepositoryService = userRepositoryService;
        this.encryptionUtils = encryptionUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity usersEntity = userRepositoryService.findByUsername(username);
        String decryptedPassword = null;
            try {
                decryptedPassword = encryptionUtils.decrypt(usersEntity.getSPhrase());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        log.error("Decrypted password is {}", decryptedPassword);
            return User.builder()
                    .username(Objects.nonNull(usersEntity)?usersEntity.getName():null)
                    .password(passwordEncoder.encode(decryptedPassword)) // must be encoded
                    .roles(usersEntity.getAuthenticationType().getSignTypValue()) // Spring adds ROLE_ automatically
                    .build();
    }
}
