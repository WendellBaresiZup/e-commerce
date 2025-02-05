package com.zup.ecommerce.services;

import com.zup.ecommerce.dtos.CustomerRequestDTO;
import com.zup.ecommerce.dtos.CustomerResponseDTO;
import com.zup.ecommerce.exceptions.CustomerInvalidException;
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

    public ResponseEntity<CustomerResponseDTO> createCustomer(CustomerRequestDTO customerRequest){
        try {
            Customer customer = new Customer(null, customerRequest.getName(), customerRequest.getCpf(), customerRequest.getEmail());
            validateCustomer(customer);
            Customer saveCustomer = repository.save(customer);
            CustomerResponseDTO bodyCustomer = new CustomerResponseDTO ("Customer Create succesfully!",saveCustomer.getId(), saveCustomer.getName(), saveCustomer.getCpf(), saveCustomer.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(bodyCustomer);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public List<CustomerResponseDTO> getAllCustomers(){
        return repository.findAll().stream()
                .map(customer -> new CustomerResponseDTO("All Customer Data!!",customer.getId(), customer.getName(), customer.getCpf(), customer.getEmail()))
                .collect(Collectors.toList());
    }

    public ResponseEntity<CustomerResponseDTO> updateCustomer(Long id, CustomerRequestDTO customerRequest){
        try {
            Customer existingCustomer = repository.findById(id).orElseThrow(()-> new RuntimeException("Customer with ID " + id + " not found."));
            validateCustomerName(customerRequest.getName());
            validateCustomerCpf(customerRequest.getCpf());
            validateCustomerEmail(customerRequest.getEmail());

            existingCustomer.setName(customerRequest.getName());
            existingCustomer.setCpf(customerRequest.getCpf());
            existingCustomer.setEmail(customerRequest.getEmail());

            Customer saveCustomer = repository.save(existingCustomer);
            CustomerResponseDTO bodyCustomer = new CustomerResponseDTO ("Customer Update Sucessfully",saveCustomer.getId(), saveCustomer.getName(), saveCustomer.getCpf(), saveCustomer.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(bodyCustomer);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    private void validateCustomerName(String name){
        if (name == null || name.isEmpty()){
            throw new CustomerInvalidException("The Customer name cannot be empty!");
        }
    }

    private void validateCustomerCpf(String cpf){
        String cpfPattern = "^[0-9]{11}$";
        if (cpf == null || !Pattern.matches(cpfPattern, cpf)){
            throw new CustomerInvalidException("The Customer CPF must be a valid 11-digit number!");
        }
        if (repository.existsByCpf(cpf)){
            throw new CustomerInvalidException("The Customer CPF already exists!");
        }
    }

    private void validateCustomerEmail(String email){
        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-za-z]{2,6}$";
        if (email == null || !Pattern.matches(emailPattern, email)){
            throw new CustomerInvalidException("The Customer email must be valid!");
        }
        if (repository.existsByEmail(email)){
            throw new CustomerInvalidException("The Customer email already exists!");
        }
    }

    private void validateCustomer(Customer customer){
        validateCustomerName(customer.getName());
        validateCustomerCpf(customer.getCpf());
        validateCustomerEmail(customer.getEmail());
    }

}
