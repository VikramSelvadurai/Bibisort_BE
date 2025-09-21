package com.example.bigbisort_be.persistence.signup.user.model;

import com.example.bigbisort_be.common.Internationalization.Translator;
import com.example.bigbisort_be.common.enums.AuthenticationType;
import com.example.bigbisort_be.persistence.signup.user.entity.UsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import static com.example.bigbisort_be.common.constants.CommonConstants.USERNAME_NOT_FOUND;


@Component
public class UserRepositoryService {

    private final UserRepository userRepository;
    private final Translator translator;

    public UserRepositoryService(@Lazy UserRepository userRepository, Translator translator) {
        this.userRepository = userRepository;
        this.translator = translator;
    }

    public UsersEntity findByUsername(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException(translator.toLocale(USERNAME_NOT_FOUND)));
    }

    public UsersEntity findByUserNameAndAuthenticationType(String username, AuthenticationType authenticationType) {
        return userRepository.findByUserNameAndAuthenticationType(username,authenticationType).orElseThrow(() -> new UsernameNotFoundException(translator.toLocale(USERNAME_NOT_FOUND)));
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
