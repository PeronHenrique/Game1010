package GameUI;

import java.util.Map;

import GameLogic.GameBoard;
import GameLogic.Piece;

public class TerminalUIRenderer implements UIRenderer {

    public void displayGame(GameBoard gameBoard, int points, Map<Integer, Piece> pieces) {
        TerminalUI.printBoard(gameBoard, points);
        TerminalUI.printPieceHashMap(pieces);
    }


    public void drawGameOver(GameBoard gameBoard, int points, Map<Integer, Piece> pieces) {
        cleanScreen();
        TerminalUI.printLine("GAME OVER!");
        displayGame(gameBoard, points, pieces);
    }


    public void cleanScreen() {
        TerminalUI.clean();
    }


    public void displayInvalidMove() {
        TerminalUI.printLine("Invalid Move, play again:");
    }


    @Override
    public Move getMove(int nPieces, int width, int height) {
        return TerminalUI.getUserMove(nPieces, width, height);
    }
    
}
