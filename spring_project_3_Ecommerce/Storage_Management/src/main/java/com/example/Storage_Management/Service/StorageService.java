package com.example.Storage_Management.Service;

import com.example.Storage_Management.Repository.StorageRepository;
import com.example.Storage_Management.allModels.StorageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class StorageService {

    private final StorageRepository storageRepository;

    @Autowired
    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @Transactional(readOnly = true)
    public boolean checkStorage(String skuCode) {
        return storageRepository.findByskuCode(skuCode).isPresent();
    }

    @Transactional(readOnly = true)
    public List<StorageResponse> checkStorageList(List<String> skuCodes) {
        System.out.println(skuCodes);
        return storageRepository.findByskuCodeIn(skuCodes)
                .stream()
                .map(storage ->
                    StorageResponse.builder()
                            .skuCode(storage.getSkuCode())
                            .inStock(storage.getQuantity() > 0)
                            .build()
                ).toList();
    }
}
