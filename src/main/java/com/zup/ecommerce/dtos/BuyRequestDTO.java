package com.zup.ecommerce.dtos;

import java.util.List;

public class BuyRequestDTO {

    private String cpf;
    private List<ProductRequestDTO> products;

    public BuyRequestDTO(){

    }

    public BuyRequestDTO(String cpf, List<ProductRequestDTO> products) {
        this.cpf = cpf;
        this.products = products;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<ProductRequestDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRequestDTO> products) {
        this.products = products;
    }
}
