package com.zup.ecommerce.exceptions;

public class BuyInvalidException extends RuntimeException{
    public BuyInvalidException(String message){
        super(message);
    }
}
