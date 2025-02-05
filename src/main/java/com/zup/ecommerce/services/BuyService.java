package com.zup.ecommerce.services;

import com.zup.ecommerce.dtos.BuyRequestDTO;
import com.zup.ecommerce.dtos.BuyResponseDTO;
import com.zup.ecommerce.dtos.ProductRequestDTO;
import com.zup.ecommerce.dtos.ProductResponseDTO;
import com.zup.ecommerce.exceptions.BuyInvalidException;
import com.zup.ecommerce.exceptions.BuyNotFoundException;
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
import java.util.stream.Collectors;

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

    public ResponseEntity<BuyResponseDTO> buyProduct(BuyRequestDTO buyRequest){
        try {
            String cpf = buyRequest.getCpf();
            List<ProductRequestDTO> products = buyRequest.getProducts();
            if (products == null || products.isEmpty()){
                throw new BuyInvalidException("The Product list cannot be null or empty!");
            }

            Customer customer = customerRepository.findByCpf(cpf).orElseThrow(() -> new BuyNotFoundException("Customer not found with CPF: " + cpf));

            List<ProductResponseDTO> productResponses = products.stream().
                    map(productRequest -> {
                        String productName = productRequest.getName();
                        Product product = productRepository.findByName(productName).orElseThrow(() -> new BuyNotFoundException("Product not found: " + productName));

                        if (product.getQuantity() <= 0){
                            throw new BuyInvalidException("Product out of stock: " + productName);
                        }

                        product.setQuantity(product.getQuantity() - 1);
                        productRepository.save(product);

                        Buy buy = new Buy(null, customer, product);
                        repository.save(buy);

                        return new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getQuantity());
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (BuyInvalidException | BuyNotFoundException e){
            BuyResponseDTO errorBody = new BuyResponseDTO(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
        }
    }
}
