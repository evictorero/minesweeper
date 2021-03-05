package com.example.minesweeper.model;

import java.util.List;

public enum CellState {
    FLAGGED, QUESTION_MARK, OPENED, UNOPENED;

    public boolean isUnopenedState() {
        return List.of(FLAGGED, QUESTION_MARK, UNOPENED).contains(this);
    }
}
