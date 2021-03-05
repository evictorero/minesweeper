import { Component } from '@angular/core';
import { GameService } from './game.service';
import { Action, Cell, CellState, Game, PlayMoveDTO, StartGameDTO } from './entities';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'minesweeper';
  counter = 5;
  cell: Cell;
  games: Game[] = [];
  gameInProgress: Game;
  constructor(private gameService: GameService) {
    this.gameService.getAll().subscribe(games => {
      this.games = games;
      if (games.length > 0) {
        this.gameInProgress = this.games[0];
      }
      console.log(this.games);
    })
    this.reset();
  }

  open(cell, row, column) {
    const playMoveDTO = new PlayMoveDTO(Action.OPEN, row, column);
    this.gameService.play(this.gameInProgress.id, playMoveDTO).subscribe(game => {
      this.gameInProgress = game;
      // cell.state = CellState.OPENED;
    });
  }

  rightClick(cell, row, column) {
    let nextAction: Action;
    switch (cell.state) {
      case (CellState.UNOPENED):
        nextAction = Action.FLAG;
        break;
      case (CellState.FLAGGED):
        nextAction = Action.QUESTION_MARK;
        break;
      case (CellState.QUESTION_MARK):
        nextAction = Action.CLEAR;
        break;
    }
    const playMoveDTO = new PlayMoveDTO(nextAction, row, column);
    this.gameService.play(this.gameInProgress.id, playMoveDTO).subscribe(game => {
      this.gameInProgress = game;
    });
  }

  reset() {
    // this.board = new Board(this.counter, this.counter);
    this.counter++;
  }

  createGame() {
    const newGame = new StartGameDTO('eze', 3, 3, 10);
    this.gameService.create(newGame).subscribe(game => {
      this.gameInProgress = game;
      this.games.push(game);
    });
  }
}
