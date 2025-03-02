package com.example.bigbisort_be.persistence.varieties.model;

import com.example.bigbisort_be.persistence.product.entity.ProductsEntity;
import com.example.bigbisort_be.persistence.varieties.entity.VarietiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VarietiesRepository extends JpaRepository<VarietiesEntity, UUID>, JpaSpecificationExecutor<VarietiesEntity>,
        PagingAndSortingRepository<VarietiesEntity, UUID> {

    Optional<VarietiesEntity> findById(UUID varietyId);

}
