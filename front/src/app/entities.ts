export class StartGameDTO {
  name: string;
  rowSize: number;
  columnSize: number;
  minePercentage: number;
}

export class MatrixRow {
  cells: Cell[];
}


export class Board {
  rowSize: number;
  columnSize: number;
  minePercentage: number;
  rows: MatrixRow[];
}

export class Cell {
  state: CellState;
  mined: boolean;
  surroundingMines: number;
}

export enum CellState {
  UNOPENED = 'UNOPENED',
  OPENED = 'OPENED',
  QUESTION_MARK = 'QUESTION_MARK',
  FLAGGED = 'FLAGGED',
}

export enum GameState {
  FINISHED = 'FINISHED',
  INPROGRESS = 'INPROGRESS',
  PAUSED = 'PAUSED',
}

export enum Action {
  OPEN = 'OPEN',
  FLAG = 'FLAG',
  QUESTION_MARK = 'QUESTION_MARK',
  CLEAR = 'CLEAR'
}

export class PlayMoveDTO {
  action: Action;
  row: number;
  column: number;

  constructor(action: Action, row: number, column: number) {
    this.action = action;
    this.row = row;
    this.column = column;
  }
}

export class Game {
  id: number;
  board: Board;
  userName: string;
  timeElapsed: number;
  state: GameState;
}
