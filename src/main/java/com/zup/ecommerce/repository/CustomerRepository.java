package com.zup.ecommerce.repository;

import com.zup.ecommerce.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
