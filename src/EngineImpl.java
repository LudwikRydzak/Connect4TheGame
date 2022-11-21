public class EngineImpl implements Engine {

    int lastMoveColumn;
    int lastMoveRow;
    int lastMoveColour;
    public String gameEnded() {
        if( checkColumn() || checkRow() || checkDiagonal1()
                || checkDiagonal2()){
            return
        }
        else if(gameEndedWithDraw(Gameplay.getCurrentBoard())){
            return "DRAW";
        }
        else{
            return "NOTENDED";
        }
    }
    private boolean gameEndedWithWin(){
        return      checkColumn()
                 || checkRow()
                 || checkDiagonal1()
                 || checkDiagonal2();
    }
    private boolean gameEndedWithDraw(Board board){
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
                move.row = i;
                break;
            }
        }
    }


    private boolean checkColumnForColor(int column, int colour) {
        Board board = Gameplay.getCurrentBoard();
        int counter = 0;
        for (int i = 0; i < board.rows; i++) {
            if (board.board[column][i] == colour) {
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

    private boolean checkRowForColour(int row, int colour) {
        Board board = Gameplay.getCurrentBoard();
        int counter = 0;
        for (int i = 0; i < board.columns; i++) {
            if (board.board[i][row] == colour) {
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

    private int checkDiagonal1ForColour() {
        Board board = Gameplay.getCurrentBoard();
        int counter = 0;
        for (int i = 0; i <= move.chosenColumn + move.row; i++) {
            if (i < board.columns && move.chosenColumn + move.row - i < board.rows) {
                if (board.board[i][move.chosenColumn + move.row - i] == move.colour) {
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

    private int checkDiagonal2ForColour() {
        Board board = Gameplay.getCurrentBoard();
        int counter = 0;
        for (int i = 0; i < board.rows; i++) {
            int referenceColumn = move.chosenColumn - move.row + i;
            if (referenceColumn < board.columns
                    && referenceColumn >= 0) {
                if (board.board[referenceColumn][i] == move.colour) {
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
