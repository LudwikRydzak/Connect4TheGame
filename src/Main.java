import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
//		Plansza board = new Plansza(7, 6, 4);
//		Wyswietlacz.wyswietlPlansze(board);
		initialization(1);
	}

	public static boolean initialization(int consoleInput) {
		Board board = null;
		Player player1 = null;
		Player player2 = null;

		if (consoleInput == 1) {
			Scanner scan = new Scanner(System.in);
			System.out.println("Podaj liczbe kolumn");
			int columns = scan.nextInt();
			System.out.println("Podaj liczbe wierszy");
			int rows = scan.nextInt();
			System.out.println("connect N, podaj N");
			int connectN = scan.nextInt();
			board = new Board(columns, rows, connectN);
			int option1;
			do {
				System.out.println("Player1: 1 Hooman, 2 Computer Player");
				option1 = scan.nextInt();
			} while (option1 != 1 && option1 != 2);
			if (option1 == 1) {

				player1 = new HumanPlayer(1);
			} else {
				System.out.println("Podaj strength AI");
				int level = scan.nextInt();
				player1 = new ComputerPlayer(1, level);
			}

			int option2;
			do {
				System.out.println("Player2: 1 Hooman, 2 Computer Player");
				option2 = scan.nextInt();
			} while (option2 != 1 && option2 != 2);
			if (option2 == 1) {
				player2 = new HumanPlayer(2);
			} else {
				System.out.println("Set AI strength");
				int level = scan.nextInt();
				player2 = new ComputerPlayer(2, level);
			}
			Gameplay.gameplay(board, player1, player2, null);

		} else {
			launch();
		}

		return true;

	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		Gui gui = new Gui();
		gui.start(primaryStage);

	}

}
