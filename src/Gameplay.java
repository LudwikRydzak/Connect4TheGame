import javafx.stage.Stage;

public class Gameplay {
    private static Board CURRENT_BOARD;
    private static int CONNECTN;
    Player player1;
    Player player2;
    Engine engine;
    Display display;
    private Player currentPlayer;
    private String gameResult;
    public Gameplay(GameplaySettings settings) {
        CURRENT_BOARD = settings.board;
        CONNECTN = settings.connectN;

        this.player1 = settings.player1;
        this.player2 = settings.player2;
        this.engine = settings.engine;
        this.display = settings.display;

        this.currentPlayer = player1;
    }
    public static Board getCurrentBoard(){
        return CURRENT_BOARD;
    }
    public static int getConnectN(){
        return CONNECTN;
    }
    public boolean gameplay() {
        int gameplayRounds = CURRENT_BOARD.columns * CURRENT_BOARD.rows;
        for (int i = 0; i < gameplayRounds; i++) {

            this.display.displayBoard(CURRENT_BOARD);

            if (gameEnded()) {
                return true;
            }

            Move currentMove = currentPlayerMakeMove();
            engine.makeMoveOnBoard(currentMove, CURRENT_BOARD);

            nextPlayer();
        }
        this.display.displayBoard(CURRENT_BOARD);

        return gameEnded(); //if at this moment gameEnded() returns false, something went wrong

    }
    private Move currentPlayerMakeMove()
    {
        Move currentMove;
            do{
                currentMove = this.currentPlayer.makeMove();
            } while (!this.engine.movePossible(currentMove));

        return currentMove;
    }

    private boolean gameEnded() {
        boolean ended;
        this.gameResult = engine.gameEnded();
        switch (this.gameResult) {
            case "DRAW":
                ended = true;
                break;
            case "PLAYER1WIN":
                ended = true;
                break;
            case "PLAYER2WIN":
                ended = true;
                break;
            case "NOTENDED":
                ended = false;
                break;
            default:
                ended = true;
                System.out.print("Unexpected end of the game value. Game ends now!");
        }
        return ended;
    }


    private Player nextPlayer(){
        currentPlayer = currentPlayer == player1 ? player2 : player1;
        return currentPlayer;
    }
}
