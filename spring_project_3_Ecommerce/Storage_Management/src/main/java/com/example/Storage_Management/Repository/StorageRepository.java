package com.example.Storage_Management.Repository;

import com.example.Storage_Management.allModels.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {

    @Query("SELECT s FROM Storage s where s.skuCode =?1")
    Optional<Storage> findByskuCode(String skuCode);

    List<Storage> findByskuCodeIn(List<String> skuCodes);
}
