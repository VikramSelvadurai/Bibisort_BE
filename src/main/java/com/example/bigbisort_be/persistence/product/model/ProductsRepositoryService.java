package com.example.bigbisort_be.persistence.product.model;

import com.example.bigbisort_be.persistence.product.entity.ProductsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductsRepositoryService {
    private final ProductsRepository productsRepository;

    public ProductsEntity save(ProductsEntity productsEntity) {
        return productsRepository.saveAndFlush(productsEntity);
    }

    public Optional<ProductsEntity> findById(UUID productId) {
        return productsRepository.findById(productId);
    }

    public boolean existsByProductNameIgnoreCase(String productName) {
        return productsRepository.existsByProductNameIgnoreCase(productName);
    }

    public Set<ProductsEntity> findAllByIdIsIn(List<UUID> productIds) {
        return new HashSet<>(productsRepository.findAllByIdIsIn(productIds));
    }

}
