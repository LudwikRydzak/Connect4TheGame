
public class Board {
	int[][] board;
	int columns;
	int rows;
	int connectN;

	//initialization of a board
	public Board(int columns, int rows, int connectN) {
		this.board = new int[columns][rows];
		this.columns = columns;
		this.rows = rows;
		this.connectN = connectN;
	}

	public Board(Board otherBoard) {
		this.columns = otherBoard.columns;
		this.rows = otherBoard.rows;
		this.connectN = otherBoard.connectN;
		this.board = new int[columns][rows];

		for (int i = 0; i < this.columns; i++) {
			for (int j = 0; j < this.rows; j++) {
				this.board[i][j] = otherBoard.board[i][j];
			}
		}
	}

	public boolean makeMove(Move move) {
		for (int i = 0; i < this.rows; i++) {
			if (this.board[move.columnIndex][i] == 0) {
				this.board[move.columnIndex][i] = move.colour;
				move.rowIndex = i;
				return true;
			}
		}
		return false;
	}

	public boolean ifNoMoreMoves() {
		for (int i = 0; i < this.columns; i++) {
			if (this.board[i][this.rows - 1] == 0) {
				return false;
			}
		}
		return true;
	}

	public boolean ifEnd(Move move) {
		return checkColumn(move) || checkRow(move) || checkDiagonal1(move)
				|| checkDiagonal2(move);
	}

	public boolean ifMovePossible(int columnIndex) {
		if (columnIndex < 0 || columnIndex >= columns) {
			return false;
		}
		if (board[columnIndex][rows - 1] == 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkColumn(Move move) {
		int counter = 0;
		for (int i = 0; i < this.rows; i++) {
			if (this.board[move.columnIndex][i] == move.colour) {
				counter++;
				if (counter == connectN) {
					return true;
				}
			} else {
				counter = 0;
			}
		}
		return false;
	}

	private boolean checkRow(Move move) {
		int counter = 0;
		for (int i = 0; i < this.columns; i++) {
			if (this.board[i][move.rowIndex] == move.colour) {
				counter++;
				if (counter == connectN) {
					return true;
				}
			} else {
				counter = 0;
			}
		}
		return false;
	}

	private boolean checkDiagonal1(Move move) {
		int counter = 0;
		for (int i = 0; i <= move.columnIndex + move.rowIndex; i++) {
			if (i < this.columns && move.columnIndex + move.rowIndex - i < this.rows) {
				if (this.board[i][move.columnIndex + move.rowIndex - i] == move.colour) {
					counter++;
					if (counter == connectN) {
						return true;
					}
				} else {
					counter = 0;
				}
			}
		}
		return false;
	}

	private boolean checkDiagonal2(Move move) {
		int counter = 0;
		for (int i = 0; i < this.rows; i++) {
			if (move.columnIndex - move.rowIndex + i < this.columns
					&& move.columnIndex - move.rowIndex + i >= 0) {
				if (this.board[move.columnIndex - move.rowIndex + i][i] == move.colour) {
					counter++;
					if (counter == connectN) {
						return true;
					}
				} else {
					counter = 0;
				}
			}
		}
		return false;
	}
}