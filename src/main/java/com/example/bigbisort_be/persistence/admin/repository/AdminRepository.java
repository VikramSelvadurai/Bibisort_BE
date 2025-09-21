package com.example.bigbisort_be.persistence.admin.repository;

import com.example.bigbisort_be.persistence.admin.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, UUID> {

    boolean existsByUserName(String username);
}
