package com.zup.ecommerce.controllers;

import com.zup.ecommerce.dtos.CustomerRequestDTO;
import com.zup.ecommerce.dtos.CustomerResponseDTO;
import com.zup.ecommerce.models.Customer;
import com.zup.ecommerce.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity <CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO customerRequest){
        return service.createCustomer(customerRequest);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers(){
        return ResponseEntity.ok(service.getAllCustomers());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Map<String, Object>> updateCustomer(@PathVariable(value = "id") Long id, @RequestBody Customer customerUpdate){
        try{
            ResponseEntity<Map<String, Object>> updated = service.updateCustomer(id, customerUpdate);
            return updated;
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}
