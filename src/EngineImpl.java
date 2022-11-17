public class EngineImpl implements Engine {

    public int gameEnded() {
//        return checkColumn(move) || checkRow(move) || checkDiagonal1(move)
//                || checkDiagonal2(move);
    }
    public boolean movePossible(Move move) {
//        if (columnIndex < 0 || columnIndex >= columns) {
//            return false;
//        }
//        if (board[columnIndex][rows - 1] == 0) {
//            return true;
//        } else {
//            return false;
//        }
        return false;
    }
    public Board makeMove(Move move) {
//        for (int i = 0; i < this.rows; i++) {
//            if (this.board[move.columnIndex][i] == 0) {
//                this.board[move.columnIndex][i] = move.colour;
//                move.rowIndex = i;
//                return true;
//            }
//        }
//        return false;
        return null;
    }


    private boolean checkColumn(Move move) {
        int counter = 0;
        for (int i = 0; i < this.rows; i++) {
            if (this.board[move.columnIndex][i] == move.colour) {
                counter++;
                if (counter == connectN) {
                    return true;
                }
            } else {
                counter = 0;
            }
        }
        return false;
    }

    private boolean checkRow(Move move) {
        int counter = 0;
        for (int i = 0; i < this.columns; i++) {
            if (this.board[i][move.rowIndex] == move.colour) {
                counter++;
                if (counter == connectN) {
                    return true;
                }
            } else {
                counter = 0;
            }
        }
        return false;
    }

    private boolean checkDiagonal1(Move move) {
        int counter = 0;
        for (int i = 0; i <= move.columnIndex + move.rowIndex; i++) {
            if (i < this.columns && move.columnIndex + move.rowIndex - i < this.rows) {
                if (this.board[i][move.columnIndex + move.rowIndex - i] == move.colour) {
                    counter++;
                    if (counter == connectN) {
                        return true;
                    }
                } else {
                    counter = 0;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal2(Move move) {
        int counter = 0;
        for (int i = 0; i < this.rows; i++) {
            if (move.columnIndex - move.rowIndex + i < this.columns
                    && move.columnIndex - move.rowIndex + i >= 0) {
                if (this.board[move.columnIndex - move.rowIndex + i][i] == move.colour) {
                    counter++;
                    if (counter == connectN) {
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
