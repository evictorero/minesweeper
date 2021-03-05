export class StartGameDTO {
  name: string;
  rowSize: number;
  columnSize: number;
  minePercentage: number;

  constructor(name, rowSize, columnSize, minePercentage) {
    this.name = name;
    this.rowSize = rowSize;
    this.columnSize = columnSize;
    this.minePercentage = minePercentage;
  }
}

export class MatrixRow {
  cells: Cell[];
}

// export class Board {
//   constructor(size: number, mines: number) {
//     for (let y = 0; y < size; y++) {
//       this.cells[y] = [];
//       for (let x = 0; x < size; x++) {
//         this.cells[y][x] = new Cell(y, x);
//       }
//     }
//   }
//   cells: Cell[][] = [];
// }

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
  // constructor(public row: number, public column: number) {}

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
  state: GameState;
}
