package com.zup.ecommerce.exceptions;

public class ProductInvalidException extends RuntimeException {
    public ProductInvalidException(String message) {
        super(message);
    }
}
