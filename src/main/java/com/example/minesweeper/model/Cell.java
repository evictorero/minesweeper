package com.example.minesweeper.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cell")
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Enumerated(EnumType.STRING)
    private CellState state;

    private boolean hasAMine;

    private int surroundingMines;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "row_id")
    private MatrixRow matrixRow;

    public Cell() {}

    public Cell(MatrixRow matrixRow) {
        this.hasAMine = false;
        this.state = CellState.UNOPENED;
        this.matrixRow = matrixRow;
        this.surroundingMines = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public boolean hasAMine() {
        return hasAMine;
    }

    public void setHasAMine(boolean hasAMine) {
        this.hasAMine = hasAMine;
    }

    public int getSurroundingMines() {
        return surroundingMines;
    }

    public void setSurroundingMines(int surroundingMines) {
        this.surroundingMines = surroundingMines;
    }

    public MatrixRow getMatrixRow() {
        return matrixRow;
    }

    public void setMatrixRow(MatrixRow matrixRow) {
        this.matrixRow = matrixRow;
    }
}
