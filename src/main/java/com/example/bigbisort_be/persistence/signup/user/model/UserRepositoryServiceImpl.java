package com.example.bigbisort_be.persistence.signup.user.model;

import com.example.bigbisort_be.common.enums.UserStatus;
import com.example.bigbisort_be.persistence.signup.user.entity.UsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class UserRepositoryServiceImpl  {

    private final UserRepositoryService userRepositoryService;

    public UserRepositoryServiceImpl(@Lazy UserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

    public Optional<UsersEntity> findByUsername(String username) {
        return Optional.ofNullable(userRepositoryService.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username)));
    }

    public UsersEntity save(UsersEntity usersEntity) {
       return userRepositoryService.save(usersEntity);
    }
}
