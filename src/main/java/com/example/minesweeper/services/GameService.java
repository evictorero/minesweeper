package com.example.minesweeper.services;

import com.example.minesweeper.exception.BadRequestException;
import com.example.minesweeper.exception.NotFoundException;
import com.example.minesweeper.model.Action;
import com.example.minesweeper.model.Game;
import com.example.minesweeper.model.GameState;
import com.example.minesweeper.model.StartGameDTO;
import com.example.minesweeper.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private GameRepository gameRepository;

    @Autowired
    public GameService (GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(StartGameDTO startGameDTO) {
        return this.gameRepository.save(new Game(startGameDTO.getName(), startGameDTO.getRowSize(), startGameDTO.getColumnSize(), startGameDTO.getMinePercentage()));
    }

    public List<Game> findGames() {
        return this.gameRepository.findAll();
    }

    public Game play(Long gameId, Action action, int row, int column) {
        var game = this.gameRepository.findById(gameId).orElseThrow(() -> new NotFoundException("Game not found"));
        validateMove(game, row, column);

        applyMove(game, action, row, column);
        this.gameRepository.save(game);
        return game;
    }

    private void applyMove(Game game, Action action, int row, int column) {
        var cell = game.getBoard().getRows().get(row).getCells().get(column);
        cell.click(action);
        if (cell.isMined()) {
            game.setState(GameState.FINISHED);
        }
    }

    private void validateMove(Game game, int row, int column) {
        if (column >= game.getBoard().getColumnSize() || row >= game.getBoard().getRowSize()) {
            throw new BadRequestException("Index out of bounds");
        }

        if (game.getState() != GameState.INPROGRESS) {
            throw new BadRequestException("Game is not in progres");
        }

    }

}
