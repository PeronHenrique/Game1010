
import GameRunner.GameRunner;
import GameRunner.BotGame;
import GameRunner.UserGame;
import GameUI.TerminalUIRenderer;

public class App {
    final static private int BOARD_WIDTH = 8;
    final static private int BOARD_HEIGHT = 8;

    public static void main(String[] args) {
        // Tests.runTests();

        // GameRunner gameRunner = new UserGame(BOARD_WIDTH, BOARD_HEIGHT, 
        // "Player 1", new TerminalUIRenderer(), 3);

        GameRunner gameRunner = new BotGame(BOARD_WIDTH, BOARD_HEIGHT, 
        "Bot", new TerminalUIRenderer(), 2);

        gameRunner.startGame();

    }
}
