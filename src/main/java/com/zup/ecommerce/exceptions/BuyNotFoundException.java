package com.zup.ecommerce.exceptions;

public class BuyNotFoundException extends RuntimeException{
    public BuyNotFoundException(String message){
        super(message);
    }
}
