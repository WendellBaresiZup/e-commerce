package com.zup.ecommerce.services;

import com.zup.ecommerce.models.Product;
import com.zup.ecommerce.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<Object> createProduct(Product product){
        Product createProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("The Product was created with successfully!!");
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll().stream().map(product -> new Product(product.getId(), product.getName(), product.getPrice(), product.getQuantity() )).collect(Collectors.toList());
    }


}
