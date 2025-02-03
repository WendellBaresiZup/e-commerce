package com.zup.ecommerce.dtos;

import java.util.List;

public class BuyResponseDTO {
    private String message;
    private List<ProductResponseDTO> products;

    public BuyResponseDTO(){

    }

    public BuyResponseDTO(String message, List<ProductResponseDTO> products) {
        this.message = message;
        this.products = products;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProductResponseDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponseDTO> products) {
        this.products = products;
    }
}
