package com.zup.ecommerce.controllers;

import com.zup.ecommerce.models.Product;
import com.zup.ecommerce.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        ResponseEntity<Object> productCreated = service.createProduct(product);
        return ResponseEntity.ok(productCreated);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getProductById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable(value = "id") Long id, @RequestBody Product updateProduct){
        try {
            ResponseEntity <Map<String, Object>> updated = service.updateProduct(id, updateProduct);
            return updated;
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}
