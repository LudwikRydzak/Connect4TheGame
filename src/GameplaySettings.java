public class GameplaySettings {
    Board board = new Board(7,5);
    Player player1 = new ComputerPlayer(1,1);
    Player player2 = new ComputerPlayer(2,1);
    Engine engine = new EngineImpl();
    Display display = new DisplayConsoleImpl();
    int connectN = 4;
}
