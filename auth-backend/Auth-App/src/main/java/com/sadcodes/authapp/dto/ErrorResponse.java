package com.sadcodes.authapp.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, HttpStatus status,int statusCode) {
}
