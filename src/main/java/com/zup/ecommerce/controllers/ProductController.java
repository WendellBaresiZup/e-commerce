package com.zup.ecommerce.controllers;

import com.zup.ecommerce.models.Product;
import com.zup.ecommerce.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<Object> createProduct(Product product){
        ResponseEntity<Object> productCreated = service.createProduct(product);
        return ResponseEntity.ok(productCreated);
    }
}
