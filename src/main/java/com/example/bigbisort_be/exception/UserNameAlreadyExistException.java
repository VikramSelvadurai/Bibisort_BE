package com.example.bigbisort_be.exception;

public class UserNameAlreadyExistException extends Exception{
    public UserNameAlreadyExistException(final String messsage) {
        super(messsage);
    }
}
