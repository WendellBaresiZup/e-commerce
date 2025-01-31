package com.zup.ecommerce.services;

import com.zup.ecommerce.models.Customer;
import com.zup.ecommerce.models.Product;
import com.zup.ecommerce.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Map<String,Object>> createCustomer(Customer customer){
        Customer created = new Customer(null, customer.getName(), customer.getCpf(), customer.getEmail());
        Customer saveCustomer = repository.save(created);
        Map<String, Object> bodyCustomer = Map.of("Message", "Customer Created Successfully", "Customer Data", new Customer(saveCustomer.getId(), saveCustomer.getName(), saveCustomer.getCpf(), saveCustomer.getEmail()));
        return ResponseEntity.status(HttpStatus.CREATED).body(bodyCustomer);
    }
}
