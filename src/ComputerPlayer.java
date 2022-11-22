import java.util.ArrayList;
import java.util.Collections;

public class ComputerPlayer extends Player {
	int strength;

	public ComputerPlayer(int colour, int strength) {
		super(colour);
		this.strength = strength;
		ifHumanPlayer = false;
	}

	@Override
	public Move makeMove() {
		Board board = Gameplay.getCurrentBoard();
		ArrayList<Move> bestMoves = new ArrayList<Move>();
		int bestMoveValue = Integer.MIN_VALUE;
		if (colour == 2) {
			bestMoveValue = Integer.MAX_VALUE;
		}
		int moveValue;
		for (int i = 0; i < board.columns; i++) {
			if (Engine.movePossible(i)) {
				Move move = new Move(i, this.colour);
				Board newBoard = new Board(board);
				newBoard.makeMove(move);
				moveValue = minmax(newBoard, move, strength, (colour ^ 3) == 1, colour ^ 3);
				if (colour == 1) {
					if (moveValue > bestMoveValue) {
						bestMoveValue = moveValue;
						bestMoves.clear();

					}

					if (moveValue == bestMoveValue) {
						bestMoves.add(move);
					}
				} else {
					if (moveValue < bestMoveValue) {
						bestMoveValue = moveValue;
						bestMoves.clear();

					}

					if (moveValue == bestMoveValue) {
						bestMoves.add(move);
					}
				}

			}
		}
		if (!bestMoves.isEmpty()) {
			Collections.shuffle(bestMoves);
			System.out.println("Obliczona wartosc: " + bestMoveValue);
			return bestMoves.get(0);
		}
		return null;
	}

	public int minmax(Board board, Move move, int depth, boolean maximizingPlayer, int colour) {
		int fitnessValue = fitness(board);

		if (depth <= 0 || board.ifEnd(move) || board.ifNoMoreMoves()) {
			System.out.println(
					"fitness value: " + fitnessValue + " depth: " + depth + " move" + move.columnIndex);
			return fitnessValue;
		}
		if (maximizingPlayer) {
			fitnessValue = -100000000;// 100 millions

			for (int i = 0; i < board.columns; i++) {
				if (board.ifMovePossible(i)) {
					Board newBoard = new Board(board);
					Move newMove = new Move(i, colour);
					newBoard.makeMove(newMove);
					fitnessValue = Math.max(fitnessValue, minmax(newBoard, newMove, depth - 1, false, colour ^ 3));
				}
			}
			System.out.println("Obliczona ostatecznie funckja oceny: " + fitnessValue + " depth: " + depth);
			return fitnessValue;
		} else {
			fitnessValue = 100000000;// sto milionow

			for (int i = 0; i < board.columns; i++) {
				if (board.ifMovePossible(i)) {
					Board nowaBoard = new Board(board);
					Move nowyMove = new Move(i, colour);
					nowaBoard.makeMove(nowyMove);
					fitnessValue = Math.min(fitnessValue, minmax(nowaBoard, nowyMove, depth - 1, true, colour ^ 3));
				}
			}
			System.out.println("Obliczona ostatecznie funckja oceny: " + fitnessValue + " depth: " + depth);
			return fitnessValue;
		}
	}

	public int fitness(Board board) {
		int sumaOceny = 0;

		sumaOceny += checkColumns(board, 1);
		sumaOceny -= checkColumns(board, 2);
		sumaOceny += checkRows(board, 1);
		sumaOceny -= checkRows(board, 2);
		sumaOceny += checkDiagonal1(board, 1);
		sumaOceny -= checkDiagonal1(board, 2);
		sumaOceny += checkDiagonal2(board, 1);
		sumaOceny -= checkDiagonal2(board, 2);

		return sumaOceny;

	}

	private int checkColumns(Board board, int kolor) {
		int possibilitiesCounter = 0;
		int actualCounter = 0;
		int suma = 0;
		for (int i = 0; i < board.columns; i++) {
			for (int j = 0; j < board.rows; j++) {
				if (board.board[i][j] == kolor) {
					possibilitiesCounter++;
					actualCounter++;
				} else if (board.board[i][j] == 0) {
					possibilitiesCounter++;
				} else {
					if (possibilitiesCounter >= board.connectN) {
						suma++;
					}
					if (actualCounter >= board.connectN) {
						suma += 1000000;
					}
					possibilitiesCounter = 0;
					actualCounter = 0;
				}
			}
			if (possibilitiesCounter >= board.connectN) {
				suma++;
			}
			if (actualCounter >= board.connectN) {
				suma += 1000000;
			}
			possibilitiesCounter = 0;
			actualCounter = 0;
		}
		return suma;
	}

	private int checkRows(Board board, int kolor) {
		int possibilitiesCounter = 0;
		int actualCounter = 0;
		int sum = 0;
		for (int i = 0; i < board.rows; i++) {
			for (int j = 0; j < board.columns; j++) {
				if (board.board[j][i] == kolor) {
					possibilitiesCounter++;
					actualCounter++;
				} else if (board.board[j][i] == 0) {
					possibilitiesCounter++;
				} else {
					if (possibilitiesCounter >= board.connectN) {
						sum++;
					}
					if (actualCounter >= board.connectN) {
						sum += 1000000;
					}
					possibilitiesCounter = 0;
					actualCounter = 0;
				}
			}
			if (possibilitiesCounter >= board.connectN) {
				sum++;
			}
			if (actualCounter >= board.connectN) {
				sum += 1000000;
			}
			possibilitiesCounter = 0;
			actualCounter = 0;
		}
		return sum;
	}

	private int checkDiagonal1(Board board, int kolor) {
		int licznikMozliwosci = 0;
		int licznikFaktycznych = 0;
		int sum = 0;
		for (int i = 0; i < board.columns + board.rows - 1; i++) {
			for (int j = 0; j < board.rows; j++) {
				if (i - j >= 0 && i - j < board.columns) {
					if (board.board[i - j][j] == kolor) {
						licznikMozliwosci++;
						licznikFaktycznych++;
					} else if (board.board[i - j][j] == 0) {

					} else {
						if (licznikMozliwosci >= board.connectN) {
							sum++;
						}
						if (licznikFaktycznych >= board.connectN) {
							sum += 1000000;
						}
						licznikMozliwosci = 0;
						licznikFaktycznych = 0;
					}
				} else {
					if (licznikMozliwosci >= board.connectN) {
						sum++;
					}
					if (licznikFaktycznych >= board.connectN) {
						sum += 1000000;
					}
					licznikMozliwosci = 0;
					licznikFaktycznych = 0;
				}
			}

		}
		return sum;
	}

	private int checkDiagonal2(Board board, int kolor) {
		int licznikMozliwosci = 0;
		int licznikFaktycznych = 0;
		int suma = 0;
		for (int i = 0; i < board.columns + board.rows - 1; i++) {
			for (int j = 0; j < board.rows; j++) {
				if (i - board.rows + 1 + j >= 0 && i - board.rows + 1 + j < board.columns) {
					if (board.board[i - board.rows + 1 + j][j] == kolor) {
						licznikMozliwosci++;
						licznikFaktycznych++;
					} else if (board.board[i - board.rows + 1 + j][j] == 0) {

					} else {
						if (licznikMozliwosci >= board.connectN) {
							suma++;
						}
						if (licznikFaktycznych >= board.connectN) {
							suma += 1000000;
						}
						licznikMozliwosci = 0;
						licznikFaktycznych = 0;
					}
				} else {
					if (licznikMozliwosci >= board.connectN) {
						suma++;
					}
					if (licznikFaktycznych >= board.connectN) {
						suma += 1000000;
					}
					licznikMozliwosci = 0;
					licznikFaktycznych = 0;
				}
			}

		}
		return suma;
	}

}
