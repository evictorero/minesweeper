package com.example.minesweeper.services;

import com.example.minesweeper.exception.BadRequestException;
import com.example.minesweeper.exception.NotFoundException;
import com.example.minesweeper.model.Board;
import com.example.minesweeper.model.Game;
import com.example.minesweeper.model.GameResult;
import com.example.minesweeper.model.GameState;
import com.example.minesweeper.model.PlayMoveDTO;
import com.example.minesweeper.model.StartGameDTO;
import com.example.minesweeper.repositories.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    public static final Logger logger = LoggerFactory.getLogger(GameService.class);

    private GameRepository gameRepository;

    @Autowired
    public GameService (GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(StartGameDTO startGameDTO) {
        var game = new Game(startGameDTO);
        game.initialize();
        return this.gameRepository.save(game);
    }

    public List<Game> findGames(String userName) {
        if (userName != null) {
            return this.gameRepository.findByUserNameAndStateIsNot(userName, GameState.FINISHED);
        }
        return this.gameRepository.findAll();
    }

    public Game play(Long gameId, PlayMoveDTO playMoveDTO) {
        logger.info("playing move {} on cell [{},{}] for game {}", playMoveDTO.getAction(), playMoveDTO.getRow(), playMoveDTO.getColumn(), gameId);
        var game = this.findById(gameId);
        validateMove(game, playMoveDTO);

        game.applyMove(playMoveDTO.getAction(), playMoveDTO.getRow(), playMoveDTO.getColumn());

        return this.gameRepository.save(game);
    }


    private void validateMove(Game game, PlayMoveDTO playMoveDTO) {
        if (playMoveDTO.getColumn() >= game.getBoard().getColumnSize() || playMoveDTO.getRow() >= game.getBoard().getRowSize()) {
            throw new BadRequestException("Index out of bounds");
        }

        if (game.getState() != GameState.INPROGRESS) {
            throw new BadRequestException("Game is not in progress");
        }
    }

    public Game pause(long gameId) {
        var game = this.findById(gameId);
        game.pause();
        return this.gameRepository.save(game);
    }

    public Game resume(long gameId) {
        var game = this.findById(gameId);        game.resume();
        return this.gameRepository.save(game);
    }

    public Game end(long gameId) {
        var game = this.findById(gameId);
        game.endGame(GameResult.LOSE);
        return this.gameRepository.save(game);
    }

    public Game findById(long gameId) {
        return this.gameRepository.findById(gameId).orElseThrow(() -> new NotFoundException("Game not found"));
    }
}
