package com.example.minesweeper.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;

    private String userName;

    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "game")
    private Board board;

    public Game() {}

    public Game(String userName, int rowSize, int columnSize, int minePercentage) {
        this.userName = userName;
        this.board = new Board(rowSize, columnSize, minePercentage);
        this.board.setGame(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
