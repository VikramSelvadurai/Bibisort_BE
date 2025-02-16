package com.example.bigbisort_be.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(final String message) {
        super(message);
    }
}
