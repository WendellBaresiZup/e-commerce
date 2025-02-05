package com.zup.ecommerce.services;

import com.zup.ecommerce.dtos.CustomerRequestDTO;
import com.zup.ecommerce.dtos.CustomerResponseDTO;
import com.zup.ecommerce.dtos.ProductRequestDTO;
import com.zup.ecommerce.dtos.ProductResponseDTO;
import com.zup.ecommerce.exceptions.CustomerInvalidException;
import com.zup.ecommerce.exceptions.CustomerNotFoundException;
import com.zup.ecommerce.exceptions.ProductInvalidException;
import com.zup.ecommerce.exceptions.ProductNotFoundException;
import com.zup.ecommerce.models.Customer;
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

    public ResponseEntity<ProductResponseDTO> createProduct(ProductRequestDTO productRequest){
        try {
            validateProductName(productRequest);
            validateProductPrice(productRequest);
            validateProductQuantity(productRequest);
            Product product = new Product(null, productRequest.getName(), productRequest.getPrice(), productRequest.getQuantity());
            Product createProduct = productRepository.save(product);

            ProductResponseDTO productResponse = new ProductResponseDTO(createProduct.getId(), createProduct.getName(), createProduct.getPrice(), createProduct.getQuantity());
            return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
        } catch (ProductInvalidException | ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public List<ProductResponseDTO> getAllProducts(){
        return productRepository.findAll().stream()
                .map(product -> new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getQuantity() )).collect(Collectors.toList());
    }

    public ProductResponseDTO getProductById(Long id){
        Product search = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductResponseDTO(search.getId(), search.getName(), search.getPrice(), search.getQuantity());
    }

    public ResponseEntity<Map<String, String>> deleteProduct(Long id){
        productRepository.deleteById(id);
        Map<String, String> body = Map.of("Message", "Product Deleted Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public void validateProductName(ProductRequestDTO productRequest){
            Optional<Product> existingProduct = productRepository.findByName(productRequest.getName());
            if (existingProduct.isPresent()){
                throw new ProductInvalidException("The Product name already exists!");
        }
    }

    public void validateProductPrice(ProductRequestDTO productRequest){
        if (productRequest.getPrice() <= 0) {
            throw new ProductInvalidException("The Product price must be greater than 0!");
        }
    }

    public void validateProductQuantity(ProductRequestDTO productRequest){
        if (productRequest.getQuantity() < 0){
            throw new ProductInvalidException("The Quantity in stock must be greater than 0!!");
        }
    }

}
