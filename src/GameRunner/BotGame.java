package GameRunner;

import Bots.Bot;
import GameLogic.Move;
import GameUI.UIRenderer;

public class BotGame extends GameRunner{
    private Bot bot;

    public BotGame(int width, int height, String name, UIRenderer UI, int nPieces, Bot bot) {
        super(width, height, name, UI, nPieces);
        this.bot = bot;
    }

    @Override
    protected Move getMove() {
        return bot.getMove(gameBoard, pieces);
    }
}
