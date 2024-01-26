package com.emma.firstapp.utils;

import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus status, String error) {
}
