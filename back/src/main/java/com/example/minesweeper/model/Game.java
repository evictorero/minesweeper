package com.example.minesweeper.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Duration;
import java.time.Instant;

@Entity
@Table(name="game")
public class Game {
    public static final Logger logger = LoggerFactory.getLogger(Game.class);


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant startTime;

    private long timeElapsed;

    private GameState state;

    private String userName;

    private GameResult result;

    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "game")
    private Board board;

    public Game() {}

    public Game(StartGameDTO startGameDTO) {
        this.startTime = Instant.now();
        this.userName = startGameDTO.getName();
        this.board = new Board(startGameDTO.getRowSize(), startGameDTO.getColumnSize(), startGameDTO.getMinePercentage());
        this.board.setGame(this);
        this.state = GameState.INPROGRESS;
        this.result = GameResult.INPROGRESS;
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

    public GameState getState() {
        return state;
    }

    public void applyMove(Action action, int row, int column) {
        var cell = this.getBoard().getRows().get(row).getCells().get(column);
        cell.applyAction(action);
        if (Action.OPEN.equals(action)) {
            if (cell.isMined()) {
                logger.info("game lost");
                this.lostGame();
            } else if (cell.getSurroundingMines() == 0) {
                logger.info("opening adjacent cells with 0 mines");
                // open recursively
                this.getBoard().openSurroundingCells(row, column);
            }
        }
        analizeGameStatus();
    }

    private void analizeGameStatus() {
        if (this.getBoard().allRemainingCellsHasAMine()) {
            this.winGame();
        }
    }

    private void winGame() {
        this.endGame(GameResult.WIN);
    }

    private void lostGame() {
        this.endGame(GameResult.LOSE);
    }
    public void pause() {
        if (GameState.INPROGRESS.equals(this.state)) {
            logger.info("pausing gameId: {}", this.id);
            this.state = GameState.PAUSED;
            this.timeElapsed = this.timeElapsed + Duration.between(startTime, Instant.now()).toMillis();
        }
    }

    public void resume() {
        if (GameState.PAUSED.equals(this.state)) {
            logger.info("resuming gameId: {}", this.id);
            this.state = GameState.INPROGRESS;
            this.startTime = Instant.now();
        }
    }

    public void endGame(GameResult gameResult) {
        logger.info("finishing gameId: {}", this.id);

        if (GameState.INPROGRESS.equals(this.state)) {
            logger.info("calculating timeElapsed for gameId: {}", this.id);
            this.timeElapsed = this.timeElapsed + Duration.between(startTime, Instant.now()).toMillis();
        }

        this.state = GameState.FINISHED;
        this.result = gameResult;
    }

    public String getTimeElapsedFormatted() {
        var timeElapsedCalculated = this.getTimeElapsed();
        return Duration.ofMillis(timeElapsedCalculated).toHoursPart() + ":" + Duration.ofMillis(timeElapsedCalculated).toMinutesPart() + ":" + Duration.ofMillis(timeElapsedCalculated).toSecondsPart();
    }

    public void initialize() {
        this.board.initialize();
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public long getTimeElapsed() {
        if (this.state == GameState.INPROGRESS) {
            return timeElapsed + Duration.between(startTime, Instant.now()).toMillis();
        }
        return timeElapsed;
    }

    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }
}
