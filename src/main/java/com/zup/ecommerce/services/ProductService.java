package com.zup.ecommerce.services;

import com.zup.ecommerce.models.Product;
import com.zup.ecommerce.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<Object> createProduct(Product product){
        Product createProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("The Product was created with successfully!!");
    }
}
