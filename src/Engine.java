public interface Engine {
    public String gameEnded();
    public boolean movePossible(Move move);
    public void makeMoveOnBoard(Move move, Board board);
}
