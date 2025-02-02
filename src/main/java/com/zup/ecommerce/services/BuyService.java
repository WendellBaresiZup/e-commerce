package com.zup.ecommerce.services;

import com.zup.ecommerce.models.Buy;
import com.zup.ecommerce.models.Customer;
import com.zup.ecommerce.models.Product;
import com.zup.ecommerce.repository.BuyRepository;
import com.zup.ecommerce.repository.CustomerRepository;
import com.zup.ecommerce.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BuyService {

    private final BuyRepository repository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;


    public BuyService(BuyRepository repository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<Map<String, Object>> buyProduct(Map<String, Object> buyRequest){
        try {
            String cpf = (String) buyRequest.get("cpf");
            List<Map<String, String>> produtos = (List<Map<String, String>>) buyRequest.get("Products");
            if (produtos == null || produtos.isEmpty()){
                throw new IllegalArgumentException("The Product list cannot be null or empty!");
            }

            Customer customer = customerRepository.findByCpf(cpf).orElseThrow(() -> new IllegalArgumentException("Customer not found with CPF: " + cpf));

            for (Map<String,String> produto : produtos) {
                String productName = produto.get("Name");
                Product product = productRepository.findByName(productName).orElseThrow(() -> new IllegalArgumentException("Product not found: " + productName));

                if (product.getQuantity() <= 0){
                    throw new IllegalArgumentException("Product out of stock: " + productName);
                }

                product.setQuantity(product.getQuantity() - 1);
                productRepository.save(product);

                Buy buy = new Buy(null, customer, product);
                repository.save(buy);
            }

            Map<String, Object> body = Map.of("Message", "Buy Completed Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (IllegalArgumentException e){
            Map<String, Object> errorBody = Map.of("Error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
        }
    }


}
