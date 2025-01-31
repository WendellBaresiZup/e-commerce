package com.zup.ecommerce.services;

import com.zup.ecommerce.models.Product;
import com.zup.ecommerce.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    public Product getProductById(Long id){
        Product search = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return new Product(search.getId(), search.getName(), search.getPrice(), search.getQuantity());
    }

    public ResponseEntity<Map<String, Object>> updateProduct(Long id, Product updateProduct){
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product with id " + id + " not found"));

        existingProduct.setName(updateProduct.getName());
        existingProduct.setPrice(updateProduct.getPrice());
        existingProduct.setQuantity(updateProduct.getQuantity());

        Product saveProduct = productRepository.save(existingProduct);
        Map<String, Object> bodyProduct = Map.of("Message", "Product Update Successfully", "New Product Data",new Product(saveProduct.getId(),saveProduct.getName(), saveProduct.getPrice(), saveProduct.getQuantity()));
        return ResponseEntity.status(HttpStatus.OK).body(bodyProduct);
    }
}
