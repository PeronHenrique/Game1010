package Bots.SearchBot;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import GameLogic.GameBoard;
import GameLogic.Move;
import GameLogic.Piece;
import GameLogic.Pieces;

public class Play {
    BigInteger board;
    List<Move> moves;
    long score;

    public Play(BigInteger board, List<Move> moves) {
        this.board = board;
        this.moves = moves;
    }

    public BigInteger getboard() {
        return new BigInteger(board.toString());
    }

    public List<Move> getMoves() {
        return new ArrayList<>(moves);
    }

    // public void CalculateScore(GameBoard gameBoard) {
    // for (Piece piece : Pieces.List) {
    // for (int x = 0; x < gameBoard.width; x++) {
    // for (int y = 0; y < gameBoard.height; y++) {
    // if (isValidMove(gameBoard, board, piece, x, y))
    // score += piece.height*piece.width;
    // }
    // }
    // }
    // }

    public void CalculateScore(GameBoard gameBoard) {
        score = gameBoard.height * gameBoard.height * gameBoard.width * 3; // maximum score possible

        int[] columns = new int[gameBoard.width];
        int[] topCell = new int[gameBoard.width];
        for (int i = 0; i < gameBoard.width; i++) 
            topCell[i] = gameBoard.height;

        for (int i = 0; i < gameBoard.width; i++) {
            BigInteger column = board.and(gameBoard.getColumnMask().shiftLeft(i));
            for (int j = 0; j < gameBoard.height; j++) {
                if (!column.and(BigInteger.ONE.shiftLeft(j * gameBoard.width + i)).equals(BigInteger.ZERO)){
                    if(topCell[i] == gameBoard.height)
                        topCell[i] = j;
                    columns[i]++;
                }
                columns[i] <<= 1;
            }
            
            columns[i] >>= 1;
        }

        for (int i = 0; i < gameBoard.width; i++) {
            score -= ruleLeft(columns, topCell, gameBoard, i);
            score -= ruleTop(columns, topCell, gameBoard, i);
            score -= ruleRight(columns, topCell, gameBoard, i);
        }

    }


    private long ruleTop(int[] columns, int[] topCell, GameBoard gameBoard, int i) {

        int points = 0;

        for (int j = 0; j < gameBoard.height - topCell[i]; j++){
            if((columns[i] & (0x01 << j)) == 0)
                points += j + 1;
        }

        return points;
    }

    private long ruleLeft(int[] columns, int[] topCell, GameBoard gameBoard, int i) {
        if (i == 0)
            return getSidePoints(columns[i], 0);

        return getSidePoints(columns[i], gameBoard.height - topCell[i-1]);
    }    
    
    private long ruleRight(int[] columns, int[] topCell, GameBoard gameBoard, int i) {
        if (i == gameBoard.width - 1)
            return getSidePoints(columns[i], 0);

        return getSidePoints(columns[i], gameBoard.height - topCell[i+1]);
    }


    private long getSidePoints(int column, int topCell) {
        int points = 0;

        for (int j = 0; j < topCell; j++){
            if((column & (0x01 << j)) == 0)
                points++;
        }

        return points;
    }

    public static boolean isValidMove(GameBoard gameBoard, BigInteger board, Piece piece, int x, int y) {
        if (gameBoard.isPieceOutofBounds(piece, x, y))
            return false;

        return GameBoard.isMoveValid(board, piece.getDefaultMask().shiftLeft(x + y * gameBoard.width));
    }
}
