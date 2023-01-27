package Bots;

import java.util.Map;

import GameLogic.GameBoard;
import GameLogic.Move;
import GameLogic.Piece;

public interface Bot {
    
    public Move getMove(GameBoard gameBoard, Map<Integer, Piece> pieces);
}
