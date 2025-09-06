package com.example.bigbisort_be.security.core.authentication;

import com.example.bigbisort_be.persistence.signup.user.entity.UsersEntity;
import com.example.bigbisort_be.persistence.signup.user.model.UserRepositoryService;
import com.example.bigbisort_be.persistence.signup.user.model.UserRepositoryServiceImpl;
import com.example.bigbisort_be.security.core.utils.EncryptionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryServiceImpl userRepositoryService;
    private EncryptionUtils encryptionUtils;

    public CustomUserDetailsService(UserRepositoryServiceImpl userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsersEntity> usersEntity = userRepositoryService.findByUsername(username);
        UsersEntity usersEntity1 = null;
//        String encryptedPassword = null;
        if(usersEntity.isPresent()) {
            usersEntity1 = usersEntity.get();
//            try {
//                encryptedPassword = this.encryptionUtils.decrypt(usersEntity1.getSPhrase());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
        }
            return User.builder()
                    .username(Objects.nonNull(usersEntity1)?usersEntity1.getName():null)
                    .password(Objects.nonNull(usersEntity1)?usersEntity1.getSPhrase():null) // must be encoded
                    .roles("") // Spring adds ROLE_ automatically
                    .build();
    }
}
