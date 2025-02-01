package com.zup.ecommerce.controllers;

import com.zup.ecommerce.models.Customer;
import com.zup.ecommerce.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity <Map<String,Object>> createCustomer(@RequestBody Customer customer){
        ResponseEntity<Map<String, Object>> customerCreated = service.createCustomer(customer);
        return ResponseEntity.status(customerCreated.getStatusCode()).body(customerCreated.getBody());
    }
}
