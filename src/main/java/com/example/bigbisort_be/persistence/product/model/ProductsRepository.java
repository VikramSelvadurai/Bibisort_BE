package com.example.bigbisort_be.persistence.product.model;

import com.example.bigbisort_be.persistence.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<ProductEntity, UUID>, JpaSpecificationExecutor<ProductEntity>,
        PagingAndSortingRepository<ProductEntity, UUID> {

//    Optional<ProductEntity> findById(UUID productId);

    boolean existsByProductNameIgnoreCase(String productName);

    List<ProductEntity> findAllByIdIsIn(List<UUID> productIds);
}
