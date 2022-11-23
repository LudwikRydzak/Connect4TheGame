public interface Engine {
    public String gameEnded();
    public boolean movePossible(int chosenColumn);
    public void makeMoveOnBoard(Move move, Board board);
}
