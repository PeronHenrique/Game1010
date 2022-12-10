package Bots;

import java.util.Map;
import java.util.Random;

import GameLogic.GameBoard;
import GameLogic.Move;
import GameLogic.Piece;

public class RandomBot implements Bot {

    @Override
    public Move getMove(GameBoard gameBoard, Map<Integer, Piece> pieces) {

        Random dice = new Random();
        return new Move(dice.nextInt(3), dice.nextInt(gameBoard.width), dice.nextInt(gameBoard.height));
    }
}