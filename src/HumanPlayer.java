import java.util.Scanner;

public class HumanPlayer extends Player {

	public HumanPlayer(int colour) {
		super(colour);
		ifHumanPlayer = true;
	}

	public Move makeMove(Board board) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Make a move: Choose column e.g. 0, 1, 2,...");
		int column = scan.nextInt();
		currentMove = new Move(column, colour);
		return currentMove;
	}

}
