package com.example.bigbisort_be.exception;

public class EmailorPhoneAlreadyExistException extends RuntimeException {
    public EmailorPhoneAlreadyExistException(final String message) {
        super(message);
    }
}
