package com.zup.ecommerce.dtos;

public class CustomerResponseDTO {
    private String message;
    private Long id;
    private String name;
    private String cpf;
    private String email;

    public CustomerResponseDTO() {
    }

    public CustomerResponseDTO(String message, Long id, String name, String cpf, String email) {
        this.message = message;
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
