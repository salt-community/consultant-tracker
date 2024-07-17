package com.example.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({ConsultantNotFoundException.class})
    private ResponseEntity<ApiError> notFoundException (RuntimeException error) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, error.getMessage());
        return new ResponseEntity<>(apiError, apiError.status());
    }
}
