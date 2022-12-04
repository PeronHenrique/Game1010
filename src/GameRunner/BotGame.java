package GameRunner;

import Bots.Bot;
import GameLogic.Move;
import GameUI.UIRenderer;

public class BotGame extends GameRunner{

    public BotGame(int width, int height, String name, UIRenderer UI, int nPieces) {
        super(width, height, name, UI, nPieces);
    }

    @Override
    protected Move getMove() {
        return Bot.getMove(gameBoard, pieces);
    }
}
