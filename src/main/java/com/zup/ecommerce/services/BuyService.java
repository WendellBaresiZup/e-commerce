package com.zup.ecommerce.services;

import com.zup.ecommerce.repository.BuyRepository;
import org.springframework.stereotype.Service;

@Service
public class BuyService {

    private final BuyRepository repository;

    public BuyService(BuyRepository repository) {
        this.repository = repository;
    }


}
