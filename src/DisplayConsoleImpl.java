
public class DisplayConsoleImpl implements Runnable,Display {

	public void displayBoard(Board board) throws InterruptedException {
		synchronized (this) {
			for (int i = board.rows - 1; i >= 0; i--) {
				for (int n = 0; n < board.columns; n++) {
					System.out.print("----");
				}
				System.out.print("--");
				System.out.println();
				for (int j = 0; j < board.columns; j++) {
					System.out.print(" | ");
					System.out.print(board.board[j][i]);

				}
				System.out.println(" |");
			}
			for (int n = 0; n < board.columns; n++) {
				System.out.print("----");
			}
			System.out.println("--");
			wait();
		}
	}

	@Override
	public void run() {

	}
}
