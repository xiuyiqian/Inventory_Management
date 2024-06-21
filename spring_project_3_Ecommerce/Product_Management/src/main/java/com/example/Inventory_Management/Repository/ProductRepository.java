package com.example.Inventory_Management.Repository;

import com.example.Inventory_Management.allModels.Product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}
