package com.example.minesweeper.exception;

import org.springframework.http.HttpStatus;

public class GameIsNotInProgressException extends ApiException {
    public GameIsNotInProgressException() {
        super(HttpStatus.BAD_REQUEST.value(), "Game is not in progress");
    }
}
