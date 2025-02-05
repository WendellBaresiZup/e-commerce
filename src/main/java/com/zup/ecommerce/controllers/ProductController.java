package com.zup.ecommerce.controllers;

import com.zup.ecommerce.dtos.CustomerResponseDTO;
import com.zup.ecommerce.dtos.ProductRequestDTO;
import com.zup.ecommerce.dtos.ProductResponseDTO;
import com.zup.ecommerce.models.Product;
import com.zup.ecommerce.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequest){
        ResponseEntity<ProductResponseDTO> productCreated = service.createProduct(productRequest);
        return productCreated;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getProductById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        return service.deleteProduct(id);
    }
}
