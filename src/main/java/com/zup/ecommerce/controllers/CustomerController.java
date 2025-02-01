package com.zup.ecommerce.controllers;

import com.zup.ecommerce.services.CustomerService;

public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }


}
