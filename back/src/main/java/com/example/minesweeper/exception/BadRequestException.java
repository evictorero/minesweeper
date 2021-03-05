package com.example.minesweeper.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {
    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST.value(),
                "Bad Request Exception");
    }
    public BadRequestException (String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
