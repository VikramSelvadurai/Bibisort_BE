package com.example.bigbisort_be.persistence.product.model;

import com.example.bigbisort_be.exception.ProductIdNotFoundException;
import com.example.bigbisort_be.persistence.product.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductsRepositoryService {
    private final ProductsRepository productsRepository;

    public ProductEntity save(ProductEntity productEntity) {
        return productsRepository.saveAndFlush(productEntity);
    }

    public ProductEntity findById(UUID productId) throws ProductIdNotFoundException {
         ProductEntity productEntity = productsRepository.findById(productId).orElseThrow(() -> new ProductIdNotFoundException("Product id not found"));
    return  productEntity;
    }

    public boolean existsByProductNameIgnoreCase(String productName) {
        return productsRepository.existsByProductNameIgnoreCase(productName);
    }

    public Set<ProductEntity> findAllByIdIsIn(List<UUID> productIds) {
        return new HashSet<>(productsRepository.findAllByIdIsIn(productIds));
    }

}
