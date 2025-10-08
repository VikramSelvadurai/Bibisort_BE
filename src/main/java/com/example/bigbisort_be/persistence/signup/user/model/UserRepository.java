package com.example.bigbisort_be.persistence.signup.user.model;

import com.example.bigbisort_be.common.enums.AuthenticationType;
import com.example.bigbisort_be.persistence.signup.user.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, UUID> {

    Optional<UsersEntity> findByUserName(String username);

    boolean existsByUserName(String username);
    Optional<UsersEntity> findByPhoneNumber(String phoneNumber);
    boolean existsByUserNameAndAuthenticationType(String username, AuthenticationType authenticationType);
    Optional<UsersEntity> findByUserNameAndAuthenticationType(String username, AuthenticationType authenticationType);
}
