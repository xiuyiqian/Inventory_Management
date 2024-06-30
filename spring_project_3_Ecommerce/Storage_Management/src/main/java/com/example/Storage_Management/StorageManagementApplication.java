package com.example.Storage_Management;

import com.example.Storage_Management.Repository.StorageRepository;
import com.example.Storage_Management.Service.StorageService;
import com.example.Storage_Management.allModels.Storage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StorageManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageManagementApplication.class, args);
	}


	@Bean
	public CommandLineRunner loadData(StorageRepository storageRepository) {
		return args -> {
			Storage storage1 = new Storage();
			storage1.setSkuCode("iphone_8");
			storage1.setQuantity(1);

			Storage storage2 = new Storage();
			storage2.setSkuCode("iphone_9");
			storage2.setQuantity(1);

			storageRepository.save(storage1);
			storageRepository.save(storage2);
		};
	}
}
