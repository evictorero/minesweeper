import { Component } from '@angular/core';
import { GameService } from './game.service';
import { Action, Cell, CellState, Game, GameResult, GameState, PlayMoveDTO, StartGameDTO } from './entities';
import { isNotNullOrUndefined } from 'codelyzer/util/isNotNullOrUndefined';

declare function confetti(): any;

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


  constructor(private gameService: GameService) { }

  reset() {
    this.gameInProgress = null;
    this.games = [];
    this.userName = null;
    this.currentUserName = null;
    this.stopConfetti();
  }

  open(cell, row, column) {
    const playMoveDTO = new PlayMoveDTO(Action.OPEN, row, column);
    this.gameService.play(this.gameInProgress.id, playMoveDTO).subscribe(game => {
      this.gameInProgress = game;
      if (game.state == GameState.FINISHED && game.result == GameResult.WIN) {
        this.startConfetti();
      }
      this.updateGameList(game);
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
      this.updateGameList(game);
    });
  }

  loadGame(game: Game) {
    this.gameInProgress = game;
    this.stopConfetti();
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
      this.loadGame(game);
      this.games.push(game);
    });
  }

  pauseGame() {
    this.gameService.pause(this.gameInProgress.id).subscribe(game => {
      this.gameInProgress = game;
      this.updateGameList(game);
    });
  }

  resumeGame() {
    this.gameService.resume(this.gameInProgress.id).subscribe(game => {
      this.gameInProgress = game;
      this.updateGameList(game);
    });
  }

  private updateGameList(game: Game) {
    const index = this.games.findIndex(g => g.id == game.id);
    this.games.splice(index, 1, game);
  }

  startConfetti() {
    // @ts-ignore
    confetti.start();
  }

  stopConfetti() {
    // @ts-ignore
    confetti.stop();
  }
}
