package com.zup.ecommerce.controllers;

import com.zup.ecommerce.models.Product;
import com.zup.ecommerce.services.ProductService;
import org.springframework.http.ResponseEntity;

public class ProductController {
    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    public ResponseEntity<Object> createProduct(Product product){
        ResponseEntity<Object> productCreated = service.createProduct(product);
        return ResponseEntity.ok(productCreated);
    }
}
