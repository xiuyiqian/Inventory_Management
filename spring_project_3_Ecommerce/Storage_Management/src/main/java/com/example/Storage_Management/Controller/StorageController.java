package com.example.Storage_Management.Controller;

import com.example.Storage_Management.Service.StorageService;
import com.example.Storage_Management.allModels.StorageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/storage")
public class StorageController {

    private final StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public boolean InStock(@PathVariable String skuCode){
        return storageService.checkStorage(skuCode);
    }
    //"http://localhost:8082/api/v2/storage?skucode=product1&skucode=product2"
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StorageResponse> isListInStock(@RequestParam List<String> skuCode){
        return storageService.checkStorageList(skuCode);
    }

}
