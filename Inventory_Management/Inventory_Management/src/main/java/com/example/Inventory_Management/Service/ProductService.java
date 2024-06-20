package com.example.Inventory_Management.Service;

import com.example.Inventory_Management.Repository.ProductRepository;
import com.example.Inventory_Management.allModels.Product.Product;
import com.example.Inventory_Management.allModels.Product.ProductRequest;
import com.example.Inventory_Management.allModels.Product.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .build();

        productRepository.save(product);
        log.info("Product {} and saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        //map product to product response
        return products.stream().map(product-> mapToProductResponse(product)).toList();
    }

    private ProductResponse mapToProductResponse(Product p) {
        return ProductResponse.builder()
                .id(p.getId())
                .description(p.getDescription())
                .name(p.getName())
                .price(p.getPrice())
                .build();
    }

}
