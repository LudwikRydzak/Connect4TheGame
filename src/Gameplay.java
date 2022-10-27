import javafx.stage.Stage;

public class Gameplay {

    public static boolean gameplay(Board board, Player player1, Player player2, Stage stage) {
        for (int i = 0; i < (board.columns * board.rows + 1) / 2; i++) {

///////////////////////////////////////////////////////////////////////////////////////////gracz1
            Display.displayBoard(board);
            if (stage != null) {
                stage.show();
            }
            Move currentMove;
            do {
                currentMove = player1.makeMove(board);
                if (currentMove == null) {
                    System.out.println("Gracz1 nie mogl wykonac ruchu");
                    return false;
                }

            } while (!board.ifMovePossible(currentMove.columnIndex));

            if (board.makeMove(currentMove)) {
                if (board.ifEnd(currentMove)) {
                    System.out.println("Wygral gracz 1");
                    Display.displayBoard(board);
                    return true;
                }
            } else {
                System.out.println("Nie mozna bylo wykonac ruchu");
            }
/////////////////////////////////////////////////////////////////////////////////////////gracz2
            Display.displayBoard(board);
            do {
                currentMove = player2.makeMove(board);
                if (currentMove == null) {
                    System.out.println("Gracz2 nie mogl wykonac ruchu");
                    return false;
                }
            } while (!board.ifMovePossible(currentMove.columnIndex));

            if (board.makeMove(currentMove)) {
                if (board.ifEnd(currentMove)) {
                    System.out.println("Wygral gracz 2");
                    Display.displayBoard(board);
                    return true;
                }
            } else {
                System.out.println("Nie mozna bylo wykonac ruchu");
            }

        }

        System.out.println("Remis!!!");
        Display.displayBoard(board);
        return false;
    }
}
