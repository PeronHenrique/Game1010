package GameRunner;

import GameLogic.Move;
import GameUI.UIRenderer;

public class UserGame extends GameRunner {


    public UserGame(int width, int height, String name, UIRenderer UI, int nPieces) {
        super(width, height, name, UI, nPieces);
    }

    @Override
    protected Move getMove() {
        return UI.getMove(nPieces, width, height);
    }
}
