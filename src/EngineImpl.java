public class EngineImpl implements Engine {

    private int lastMoveColumn;
    private int lastMoveRow;
    private int lastMoveColour;

    public String gameEnded() {
        if(gameEndedWithWin()){
            return "PLAYER"+lastMoveColour+"WIN";
        }
        else if(gameEndedWithDraw()){
            return "DRAW";
        }
        else{
            return "NOTENDED";
        }
    }
    private boolean gameEndedWithWin(){
        return      checkColumn() || checkRow() || checkDiagonal1() || checkDiagonal2();
    }
    private boolean gameEndedWithDraw(){
        Board board = Gameplay.getCurrentBoard();
        for (int i = 0; i< board.columns; i++) {
            if(board.board[i][board.rows-1]== 0){
                return false;
            }
        }
        return true;
    }
    public boolean movePossible(Move move) {
        Board board = Gameplay.getCurrentBoard();
        if (move.chosenColumn < 0 || move.chosenColumn >= board.columns) {
            return false;
        }
        if (board.board[move.chosenColumn][board.rows - 1] == 0) {
            return true;
        }
        return false;
    }
    public void makeMoveOnBoard(Move move, Board CURRENT_BOARD) {
        for (int i = 0; i < CURRENT_BOARD.rows; i++) {
            if (CURRENT_BOARD.board[move.chosenColumn][i] == 0) {
                CURRENT_BOARD.board[move.chosenColumn][i] = move.colour;

                this.lastMoveColour = move.colour;
                this.lastMoveColumn = move.chosenColumn;
                this.lastMoveRow = i;

                break;
            }
        }
    }


    private boolean checkColumn() {
        Board board = Gameplay.getCurrentBoard();
        int counter = 0;
        for (int i = 0; i < board.rows; i++) {
            if (board.board[this.lastMoveColumn][i] == this.lastMoveColour) {
                counter++;
                if (counter == Gameplay.getConnectN()) {
                    return true;
                }
            } else {
                counter = 0;
            }
        }
        return false;
    }

    private boolean checkRow() {
        Board board = Gameplay.getCurrentBoard();
        int counter = 0;
        for (int i = 0; i < board.columns; i++) {
            if (board.board[i][this.lastMoveRow] == this.lastMoveColour) {
                counter++;
                if (counter == Gameplay.getConnectN()) {
                    return true;
                }
            } else {
                counter = 0;
            }
        }
        return false;
    }

    private boolean checkDiagonal1() {
        Board board = Gameplay.getCurrentBoard();
        int counter = 0;
        for (int i = 0; i <= this.lastMoveColumn+ this.lastMoveRow; i++) {
            if (i < board.columns && this.lastMoveColumn + this.lastMoveRow - i < board.rows) {
                if (board.board[i][this.lastMoveColumn + this.lastMoveRow - i] == this.lastMoveColour) {
                    counter++;
                    if (counter == Gameplay.getConnectN()) {
                        return true;
                    }
                } else {
                    counter = 0;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal2() {
        Board board = Gameplay.getCurrentBoard();
        int counter = 0;
        for (int i = 0; i < board.rows; i++) {
            int referenceColumn = this.lastMoveColumn - this.lastMoveRow + i;
            if (referenceColumn < board.columns
                    && referenceColumn >= 0) {
                if (board.board[referenceColumn][i] == this.lastMoveColour) {
                    counter++;
                    if (counter == Gameplay.getConnectN()) {
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
