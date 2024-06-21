package com.example.Storage_Management.Service;

import com.example.Storage_Management.Repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
