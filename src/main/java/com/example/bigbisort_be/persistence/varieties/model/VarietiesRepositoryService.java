package com.example.bigbisort_be.persistence.varieties.model;

import com.example.bigbisort_be.persistence.varieties.entity.VarietiesEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VarietiesRepositoryService {
    private final VarietiesRepository varietiesRepository;

    public List<VarietiesEntity> saveAll(List<VarietiesEntity> varietiesEntityList) {
        return varietiesRepository.saveAllAndFlush(varietiesEntityList);
    }

    public  List<VarietiesEntity> saveAllWithProduct(List<VarietiesEntity> varietiesEntityList) {
         return varietiesRepository.saveAllAndFlush(varietiesEntityList);
    }

    public Optional<VarietiesEntity> findById(UUID varietyId) {
        return varietiesRepository.findById(varietyId);
    }
}
