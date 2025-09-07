package com.example.bigbisort_be.security.core.authentication;

import com.example.bigbisort_be.persistence.signup.user.entity.UsersEntity;
import com.example.bigbisort_be.persistence.signup.user.model.UserRepositoryService;
import com.example.bigbisort_be.persistence.signup.user.model.UserRepositoryServiceImpl;
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
import java.util.Optional;
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryServiceImpl userRepositoryService;
    private final EncryptionUtils encryptionUtils;
    @Autowired
    private  @Lazy PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserRepositoryServiceImpl userRepositoryService, EncryptionUtils encryptionUtils) {
        this.userRepositoryService = userRepositoryService;
        this.encryptionUtils = encryptionUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsersEntity> usersEntity = userRepositoryService.findByUsername(username);
        UsersEntity usersEntity1 = null;
        String decryptedPassword = null;
        if(usersEntity.isPresent()) {
            usersEntity1 = usersEntity.get();
            try {
                decryptedPassword = encryptionUtils.decrypt(usersEntity1.getSPhrase());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        log.error("Decrypted password is {}", decryptedPassword);
            return User.builder()
                    .username(Objects.nonNull(usersEntity1)?usersEntity1.getName():null)
                    .password(passwordEncoder.encode(decryptedPassword)) // must be encoded
                    .roles(usersEntity1.getAuthenticationType().getSignTypValue()) // Spring adds ROLE_ automatically
                    .build();
    }
}
