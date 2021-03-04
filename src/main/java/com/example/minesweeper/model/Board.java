package com.example.minesweeper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@Table(name="board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rowSize;
    private int columnSize;
    private int minePercentage;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "board", cascade = CascadeType.ALL)
    private List<MatrixRow> rows;

    @OneToOne(fetch= FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnore
    private Game game;


    public Board() {}

    public Board(int rowSize, int columnSize, int minePercentage){
        this.rows = IntStream.range(0, rowSize)
                .boxed()
                .map(v -> new MatrixRow(this, columnSize))
                .collect(Collectors.toList());

        this.rowSize = rowSize;
        this.columnSize = columnSize;
        this.minePercentage = minePercentage;

        initialize();
    }

    private void initialize() {

        addMines();
        calculateAdjacentMines();
    }

    private Optional<Cell> getByMatrixNotation(int row, int column) {
        try {
            return Optional.of(this.rows.get(row).getCells().get(column));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    private void calculateAdjacentMines() {
               for (int i = 0; i < this.rowSize; i++) {
                   for (int j = 0; j < this.columnSize; j++) {
                       AtomicInteger mineQuantity = new AtomicInteger();

                       // row above
                       this.getByMatrixNotation(i - 1, j).ifPresent( it -> { if(it.isMined()) mineQuantity.getAndIncrement(); } );
                       this.getByMatrixNotation(i - 1, j - 1).ifPresent( it -> { if(it.isMined()) mineQuantity.getAndIncrement(); } );
                       this.getByMatrixNotation(i - 1, j + 1).ifPresent( it -> { if(it.isMined()) mineQuantity.getAndIncrement(); } );

                       // center
                       this.getByMatrixNotation(i, j - 1).ifPresent( it -> { if(it.isMined()) mineQuantity.getAndIncrement(); } );
                       this.getByMatrixNotation(i, j + 1).ifPresent( it -> { if(it.isMined()) mineQuantity.getAndIncrement(); } );

                       // row below
                       this.getByMatrixNotation(i + 1, j - 1).ifPresent( it -> { if(it.isMined()) mineQuantity.getAndIncrement(); } );
                       this.getByMatrixNotation(i + 1, j).ifPresent( it -> { if(it.isMined()) mineQuantity.getAndIncrement(); } );
                       this.getByMatrixNotation(i + 1, j + 1).ifPresent( it -> { if(it.isMined()) mineQuantity.getAndIncrement(); } );

                       this.rows.get(i).getCells().get(j).setSurroundingMines(mineQuantity.intValue());

                   }
        }
    }

    private void addMines() {
        var mineQuantity = this.rowSize * this.columnSize * this.minePercentage / 100;

        while (mineQuantity > 0) {
            var row = ThreadLocalRandom.current().nextInt(0, this.rowSize);
            var column = ThreadLocalRandom.current().nextInt(0, this.columnSize);

            var cell = this.rows.get(row).getCells().get(column);

            if (!cell.isMined()) {
                cell.setMined(true);
                mineQuantity--;
            }
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<MatrixRow> getRows() {
        return rows;
    }

    public void setRows(List<MatrixRow> rows) {
        this.rows = rows;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}