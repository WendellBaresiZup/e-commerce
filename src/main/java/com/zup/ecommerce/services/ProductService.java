package com.zup.ecommerce.services;

import com.zup.ecommerce.dtos.ProductRequestDTO;
import com.zup.ecommerce.exceptions.ProductInvalidException;
import com.zup.ecommerce.models.Product;
import com.zup.ecommerce.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<Object> createProduct(Product product){
        if (!validateName().test(product)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The Product name already Existis!");
        }
        if (!validatePrice().test(product)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The Product price must be greater than 0!");
        }
        if (!validateQuantity().test(product)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The Quantity in stock must be greater than or equal to 0!");
        }
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

    public ResponseEntity<Map<String, String>> deleteProduct(Long id){
        productRepository.deleteById(id);
        Map<String, String> body = Map.of("Message", "Product Deleted Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public void validateName(ProductRequestDTO productRequest){
            Optional<Product> existingProduct = productRepository.findByName(productRequest.getName());
            if (existingProduct.isPresent()){
                throw new ProductInvalidException("The Product name already exists!");
        }
    }

    public void validatePrice(ProductRequestDTO productRequest){
        if (productRequest.getPrice() <= 0) {
            throw new ProductInvalidException("The Product price must be greater than 0!");
        }
    }

    public void validateQuantity(ProductRequestDTO productRequest){
        if (productRequest.getQuantity() < 0){
            throw new ProductInvalidException("The Quantity in stock must be greater than 0!!");
        }
    }

}
