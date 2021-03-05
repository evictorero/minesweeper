package com.example.minesweeper.model;


import javax.validation.constraints.NotNull;

public class PlayMoveDTO {

    @NotNull
    private Action action;

    @NotNull
    private Integer row;

    @NotNull
    private Integer column;

    public PlayMoveDTO(@NotNull Action action, @NotNull Integer row, @NotNull Integer column) {
        this.action = action;
        this.row = row;
        this.column = column;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
}
