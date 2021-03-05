package com.example.minesweeper.model;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StartGameDTO {
    @NotBlank
    private String name;

    @NotNull
    private Integer rowSize;

    @NotNull
    private Integer columnSize;

    @NotNull
    @Range(min=0, max=100)
    private Integer minePercentage;

    public StartGameDTO(@NotBlank String name, @NotNull Integer rowSize, @NotNull Integer columnSize, @NotNull @Range(min = 0, max = 100) Integer minePercentage) {
        this.name = name;
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        this.minePercentage = minePercentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRowSize() {
        return rowSize;
    }

    public void setRowSize(Integer rowSize) {
        this.rowSize = rowSize;
    }

    public Integer getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(Integer columnSize) {
        this.columnSize = columnSize;
    }

    public Integer getMinePercentage() {
        return minePercentage;
    }

    public void setMinePercentage(Integer minePercentage) {
        this.minePercentage = minePercentage;
    }
}
