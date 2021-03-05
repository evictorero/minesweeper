package com.example.minesweeper.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {
    public NotFoundException() {
        super(HttpStatus.NOT_FOUND.value(),
                "Not Found Exception");
    }
    public NotFoundException (String message) {
        super(HttpStatus.NOT_FOUND.value(), message);
    }
}
