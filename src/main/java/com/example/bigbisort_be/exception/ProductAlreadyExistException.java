package com.example.bigbisort_be.exception;

public class ProductAlreadyExistException extends Exception{
    public ProductAlreadyExistException(final String message){
        super(message);
    }
}
