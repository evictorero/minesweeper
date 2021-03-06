import { Component } from '@angular/core';
import { GameService } from './game.service';
import { Action, Cell, CellState, Game, PlayMoveDTO, StartGameDTO } from './entities';
import { isNotNullOrUndefined } from 'codelyzer/util/isNotNullOrUndefined';
import { isNullOrUndefined } from 'util';

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
  userName: string;
  currentUserName: string;

  startGameDTO: StartGameDTO = new StartGameDTO();


  constructor(private gameService: GameService) {
    // this.gameService.getAllByUserName().subscribe(games => {
    //   this.games = games;
    //   if (games.length > 0) {
    //     this.gameInProgress = this.games[0];
    //   }
    //   console.log(this.games);
    // })
    // this.reset();
  }

  reset() {
    this.gameInProgress = null;
    this.games = [];
    this.userName = null;
    this.currentUserName = null;
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

  loadGame(game: Game) {
    this.gameInProgress = game;
  }

  startGame() {
    this.currentUserName = this.userName;
    if (isNotNullOrUndefined(this.currentUserName)) {
      this.gameService.getAllByUserName(this.userName).subscribe(games => {
        this.games = games;
      })
    }
  }

  createGame() {
    this.startGameDTO.name = this.currentUserName;

    this.gameService.create(this.startGameDTO).subscribe(game => {
      this.gameInProgress = game;
      this.games.push(game);
    });
  }

  pauseGame() {
    this.gameService.pause(this.gameInProgress.id).subscribe(game => {
      this.gameInProgress = game;
    });
  }

  resumeGame() {
    this.gameService.resume(this.gameInProgress.id).subscribe(game => {
      this.gameInProgress = game;
    });
  }
}
