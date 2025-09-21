package com.example.bigbisort_be.persistence.admin.repository;

import com.example.bigbisort_be.persistence.admin.entity.AdminEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminRepositoryService {
    private final AdminRepository adminRepository;

    public boolean existsByUsername(String username) {
        return adminRepository.existsByUserName(username);
    }

    public AdminEntity save(AdminEntity adminEntity) {
        return adminRepository.save(adminEntity);
    }
}
