
public abstract class Player {
	int colour;
	Move currentMove;
	Boolean ifHumanPlayer;

	public Player(int colour) {
		this.colour = colour;
	}

	public abstract Move makeMove(Board board);
}
