package com.example.backend.exceptions;

import org.springframework.http.HttpStatus;

public record ApiError(HttpStatus status, String message) {
}