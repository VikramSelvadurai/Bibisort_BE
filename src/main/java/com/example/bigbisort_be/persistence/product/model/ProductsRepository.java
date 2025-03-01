package com.example.bigbisort_be.persistence.product.model;

import com.example.bigbisort_be.persistence.product.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, UUID> {

    Optional<ProductsEntity> findById(UUID productId);

    boolean existsByProductNameIgnoreCase(String prductName);

    List<ProductsEntity> findAllByIdIsIn(List<UUID> productIds);
}
