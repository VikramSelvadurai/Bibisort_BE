package com.example.bigbisort_be.persistence.signup.user.model;

import com.example.bigbisort_be.persistence.signup.user.entity.UsersEntity;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.UnknownUnits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepositoryService extends JpaRepository<UsersEntity, UUID> {

    Optional<UsersEntity> findByUserName(String username);
}
