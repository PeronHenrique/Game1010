package GameRunner;

import java.util.HashMap;
import java.util.Map;

import GameLogic.GameBoard;
import GameLogic.Move;
import GameLogic.Piece;
import GameLogic.Pieces;
import GameUI.UIRenderer;

public class GameRunner implements Runnable {
    protected final int width;
    protected final int height;
    protected final UIRenderer UI;

    protected int points;
    protected GameBoard gameBoard;

    protected Map<Integer, Piece> pieces;
    protected boolean getNewPieces;
    protected final int nPieces;

    public GameRunner(int width, int height, String name, UIRenderer UI, int nPieces) {

        this.nPieces = nPieces;

        this.width = width;
        this.height = height;
        this.UI = UI;

        points = 0;

        getNewPieces = true;
        pieces = new HashMap<>();

        gameBoard = new GameBoard(width, height, name);
    }

    @Override
    public void run() {

        while (true) {
            if (getNewPieces) {
                getNewPieces();
                getNewPieces = false;
            }

            if (gameOver())
                break;

            UI.displayGame(gameBoard, points, pieces);

            Move move = getMove();

            if (makeMove(move)) {
                updateGame();

                pieces.remove(move.pieceIndex);
                if (pieces.isEmpty())
                    getNewPieces = true;
            } else {
                UI.displayInvalidMove();
            }
        }

        UI.drawGameOver(gameBoard, points, pieces);
    }

    protected void getNewPieces() {
        for (int i = 0; i < nPieces; i++)
            pieces.put(i, Pieces.getRandonPiece());
    }

    protected Move getMove() {
        return UI.getMove(nPieces, width, height);
    }

    protected boolean makeMove(Move move) {
        Piece piece = pieces.get(move.pieceIndex);
        if (piece == null)
            return false;

        return gameBoard.setPiece(piece, move.X, move.Y);
    }

    protected void updateGame() {
        points += gameBoard.getNewPoints();
        gameBoard.updateBoard();
    }

    protected boolean gameOver() {
        for (Piece piece : pieces.values())
            for (int i = 0; i < width; i++)
                for (int j = 0; j < height; j++)
                    if (gameBoard.isMoveValid(piece, i, j))
                        return false;

        return true;
    }

    public void startGame() {
        for (Piece piece : Pieces.List)
            piece.setDefaultMask(width, height);
        new Thread(this).start();
    }
}
