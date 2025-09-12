package com.example.bigbisort_be.persistence.signup.user.model;

import com.example.bigbisort_be.common.enums.AuthenticationType;
import com.example.bigbisort_be.persistence.signup.user.entity.UsersEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class UserRepositoryService {

    private final UserRepository userRepository;

    public UserRepositoryService(@Lazy UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UsersEntity findByUsername(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public boolean existsByUserName(String username) {
        return userRepository.existsByUserName(username);
    }
    public boolean existsByUserNameAndAuthenticationType(String username, AuthenticationType authenticationType) {
        return userRepository.existsByUserNameAndAuthenticationType(username,authenticationType);
    }

    public UsersEntity save(UsersEntity usersEntity) {
       return userRepository.save(usersEntity);
    }
}
