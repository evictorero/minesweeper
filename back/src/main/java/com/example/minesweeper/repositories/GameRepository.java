package com.example.minesweeper.repositories;

import com.example.minesweeper.model.Game;
import com.example.minesweeper.model.GameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByUserNameAndStateIsNot (String userName, GameState state);
}
