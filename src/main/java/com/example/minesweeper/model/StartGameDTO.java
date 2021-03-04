package com.example.minesweeper.model;

public class StartGameDTO {
    private String name;
    private int rowSize;
    private int columnSize;
    private int minePercentage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRowSize() {
        return rowSize;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getMinePercentage() {
        return minePercentage;
    }

    public void setMinePercentage(int minePercentage) {
        this.minePercentage = minePercentage;
    }
}
