
public class Board {
	int[][] board;
	int columns;
	int rows;

	//initialization of a board
	public Board(int columns, int rows) {
		this.board = new int[columns][rows];
		this.columns = columns;
		this.rows = rows;
	}

	public Board(Board otherBoard) {
		this.columns = otherBoard.columns;
		this.rows = otherBoard.rows;
		this.board = new int[columns][rows];

		for (int i = 0; i < this.columns; i++) {
			for (int j = 0; j < this.rows; j++) {
				this.board[i][j] = otherBoard.board[i][j];
			}
		}
	}


}