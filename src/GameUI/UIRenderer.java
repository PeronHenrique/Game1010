package GameUI;

import java.util.Map;

import GameLogic.GameBoard;
import GameLogic.Piece;

public interface UIRenderer {
    public void displayGame(GameBoard gameBoard, int points, Map<Integer, Piece> pieces);
    public void drawGameOver(GameBoard gameBoard, int points, Map<Integer, Piece> pieces);
    public void cleanScreen();
    public void displayInvalidMove();
    public Move getMove(int nPieces, int width, int height);
}
