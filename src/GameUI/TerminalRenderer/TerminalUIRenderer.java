package GameUI.TerminalRenderer;

import java.util.Map;

import GameLogic.GameBoard;
import GameLogic.Move;
import GameLogic.Piece;
import GameUI.UIRenderer;

public class TerminalUIRenderer implements UIRenderer {

    @Override
    public void displayGame(GameBoard gameBoard, int points, Map<Integer, Piece> pieces) {
        TerminalUI.printBoard(gameBoard, points);
        TerminalUI.printPieceHashMap(pieces);
    }

    @Override
    public void drawGameOver(GameBoard gameBoard, int points, Map<Integer, Piece> pieces) {
        TerminalUI.printLine("GAME OVER!");
        displayGame(gameBoard, points, pieces);
    }

    @Override
    public void displayInvalidMove() {
        TerminalUI.printLine("Invalid Move, play again:");
    }

    @Override
    public Move getMove(int nPieces, int width, int height) {
        return TerminalUI.getUserMove(nPieces, width, height);
    }

}
