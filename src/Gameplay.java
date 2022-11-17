import javafx.stage.Stage;

public class Gameplay {
    public static Board CURRENT_BOARD;

    Player player1;
    Player player2;
    Engine engine;
    int connectN;

    public Gameplay(Board board, Player player1, Player player2, Engine engine, int connectN){
        CURRENT_BOARD = board;
        this.player1 = player1;
        this.player2 = player2;
        this.engine = engine;
        this.connectN = connectN;
    }
    private Player currentPlayer;


    public boolean gameplay() {
        int gameplayRounds = CURRENT_BOARD.columns * CURRENT_BOARD.rows;
        for (int i = 0; i < gameplayRounds; i++) {

            DisplayImpl.displayBoard(CURRENT_BOARD);

            Move currentMove;
            do{
                currentMove = this.currentPlayer.makeMove();

            } while (!this.engine.movePossible(currentMove));

            engine.makeMove(currentMove);

            if (gameEnded()) {
                DisplayImpl.displayBoard(CURRENT_BOARD);
                return true;
                }
        }


        DisplayImpl.displayBoard(CURRENT_BOARD);
        return false;
    }

    private boolean gameEnded() {
        boolean ended = false;
        switch (engine.gameEnded()) {
            case 0:
                ended = true;
                break;
            case 1:
                ended = true;
                break;
            case 2:
                ended = true;
                break;
            case -1:
                ended = false;
                break;
            default:
                ended = true;
                System.out.print("Unexpected gameEnd value. Game ends now!");
        }
        return ended;
    }


    private Player nextPlayer(){
        currentPlayer = currentPlayer == player1 ? player2 : player1;
        return currentPlayer;
    }
}
