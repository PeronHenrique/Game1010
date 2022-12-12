import GameRunner.GameRunner;
import GameUI.ProcessingRenderer.ProcessingRenderer;
// import GameUI.TerminalRenderer.TerminalUIRenderer;
// import Bots.RandomBot;
// import GameRunner.BotGame;

public class App {
    final static private int BOARD_WIDTH = 10;
    final static private int BOARD_HEIGHT = 10;
    final static private int N_PIECES = 3;

    public static void main(String[] args) {
        // Tests.runTests();

        // GameRunner gameRunner = new GameRunner(BOARD_WIDTH, BOARD_HEIGHT,
        // "Player 1", new TerminalUIRenderer(), 3);

        // GameRunner gameRunner = new BotGame(BOARD_WIDTH, BOARD_HEIGHT,
        // "Bot", new TerminalUIRenderer(), 3, new RandomBot());

        GameRunner gameRunner = new GameRunner(BOARD_WIDTH, BOARD_HEIGHT,
                "Player 1", new ProcessingRenderer(BOARD_WIDTH, BOARD_HEIGHT, N_PIECES), N_PIECES);

        gameRunner.startGame();
    }
}
