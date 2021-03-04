package com.example.minesweeper.controller;

import com.example.minesweeper.model.Action;
import com.example.minesweeper.model.Game;
import com.example.minesweeper.model.StartGameDTO;
import com.example.minesweeper.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public Game createGame(@RequestBody StartGameDTO startGameDTO) {
        return this.gameService.createGame(startGameDTO);
    }

    @GetMapping
    public List<Game> findGames() {
        return this.gameService.findGames();
    }

    @PostMapping("{id}")
    public Game play(@PathVariable(value = "id") Long gameId,
                     @RequestParam Action action,
                     @RequestParam int row,
                     @RequestParam int column) {
        return this.gameService.play(gameId, action, row, column);
    }
}
