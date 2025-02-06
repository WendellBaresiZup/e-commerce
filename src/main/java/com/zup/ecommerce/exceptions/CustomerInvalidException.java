package com.zup.ecommerce.exceptions;

public class CustomerInvalidException extends RuntimeException {
    public CustomerInvalidException(String message) {
        super(message);
    }
}
