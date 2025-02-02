package com.zup.ecommerce.services;

import com.zup.ecommerce.models.Customer;
import com.zup.ecommerce.models.Product;
import com.zup.ecommerce.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Map<String,Object>> createCustomer(Customer customer){
        try {
            validateCustomer(customer);
            Customer created = new Customer(null, customer.getName(), customer.getCpf(), customer.getEmail());
            Customer saveCustomer = repository.save(created);
            Map<String, Object> bodyCustomer = Map.of("Message", "Customer Created Successfully", "Customer Data", new Customer(saveCustomer.getId(), saveCustomer.getName(), saveCustomer.getCpf(), saveCustomer.getEmail()));
            return ResponseEntity.status(HttpStatus.CREATED).body(bodyCustomer);
        }catch (IllegalArgumentException e){
            Map<String, Object> errorBody = Map.of("Error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
        }
    }

    public List<Customer> getAllCustomers(){
        return repository.findAll().stream().map(customer -> new Customer(customer.getId(), customer.getName(), customer.getCpf(), customer.getEmail())).collect(Collectors.toList());
    }

    public ResponseEntity<Map<String, Object>> updateCustomer(Long id, Customer customerUpdate){
        try {
            Customer existingCustomer = repository.findById(id).orElseThrow(()-> new RuntimeException("Customer with ID " + id + " not found."));
            validateCustomerName(customerUpdate.getName());
            validateCustomerCpf(customerUpdate.getCpf());
            validateCustomerEmail(customerUpdate.getEmail());

            existingCustomer.setName(customerUpdate.getName());
            existingCustomer.setCpf(customerUpdate.getCpf());
            existingCustomer.setEmail(customerUpdate.getEmail());

            Customer saveCustomer = repository.save(existingCustomer);
            Map<String, Object> bodyCustomer = Map.of("Message", "Customer Update Sucessfully", "New Customer Data", new Customer(saveCustomer.getId(), saveCustomer.getName(), saveCustomer.getCpf(), saveCustomer.getEmail()));
            return ResponseEntity.status(HttpStatus.OK).body(bodyCustomer);
        } catch (IllegalArgumentException e){
            Map<String, Object> errorBody = Map.of("Error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
        }

    }

    private void validateCustomerName(String name){
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("The Customer name cannot be empty!");
        }
    }

    private void validateCustomerCpf(String cpf){
        String cpfPattern = "^[0-9]{11}$";
        if (cpf == null || !Pattern.matches(cpfPattern, cpf)){
            throw new IllegalArgumentException("The Customer CPF must be a valid 11-digit number!");
        }
        if (repository.existsByCpf(cpf)){
            throw new IllegalArgumentException("The Customer CPF already exists!");
        }
    }

    private void validateCustomerEmail(String email){
        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-za-z]{2,6}$";
        if (email == null || !Pattern.matches(emailPattern, email)){
            throw new IllegalArgumentException("The Customer email must be valid!");
        }
        if (repository.existsByEmail(email)){
            throw new IllegalArgumentException("The Customer email already exists!");
        }
    }

    private void validateCustomer(Customer customer){
        validateCustomerName(customer.getName());
        validateCustomerCpf(customer.getCpf());
        validateCustomerEmail(customer.getEmail());
    }

}
