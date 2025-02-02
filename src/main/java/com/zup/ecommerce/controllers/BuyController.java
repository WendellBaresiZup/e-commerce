package com.zup.ecommerce.controllers;

import com.zup.ecommerce.services.BuyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/api/buy")
public class BuyController {

    private final BuyService buyservice;

    public BuyController(BuyService buyservice) {
        this.buyservice = buyservice;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> buyProduct(@RequestBody Map<String, Object> buyRequest){
        return buyservice.buyProduct(buyRequest);
    }
}
