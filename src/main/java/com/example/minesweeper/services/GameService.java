package com.example.minesweeper.services;

import com.example.minesweeper.model.Game;
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

}
