package com.example.bigbisort_be.common.exception;

import com.example.bigbisort_be.exception.EmailorPhoneAlreadyExistException;
import com.example.bigbisort_be.exception.UserNameAlreadyExistException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ExceptionUtils exceptionUtils;

    public GlobalExceptionHandler(ExceptionUtils exceptionUtils) {
        this.exceptionUtils = exceptionUtils;
    }

    @ExceptionHandler(EmailorPhoneAlreadyExistException.class)
    public ResponseEntity<Object> handleEmailOrPhoneAlreadyExist(WebRequest request,EmailorPhoneAlreadyExistException exception) {
        return exceptionUtils.getException(HttpStatus.BAD_REQUEST, request, exception, exception.getMessage());
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(WebRequest request,ExpiredJwtException exception) {
        return exceptionUtils.getException(HttpStatus.FORBIDDEN,request,exception,exception.getMessage()  );

    }

    @ExceptionHandler(UserNameAlreadyExistException.class)
    public ResponseEntity<Object> handleUserNameAlreadyExist(WebRequest request,UserNameAlreadyExistException exception) {
        return exceptionUtils.getExceptionWithoutTanslation(HttpStatus.BAD_REQUEST,request,exception,exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleGeneric(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal server error");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
