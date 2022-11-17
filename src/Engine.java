public interface Engine {
    public int gameEnded();
    public boolean movePossible(Move move);
    public Board makeMove(Move move);
}
