package Bots;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import GameLogic.GameBoard;
import GameLogic.Move;
import GameLogic.Piece;

public class RandomBot implements Bot {

    @Override
    public Move getMove(GameBoard gameBoard, Map<Integer, Piece> pieces) {
        ArrayList<Move> moves = new ArrayList<>();

        for (Entry<Integer, Piece> entry : pieces.entrySet())
            for (int i = 0; i < gameBoard.width; i++)
                for (int j = 0; j < gameBoard.height; j++)
                    if (gameBoard.isMoveValid(entry.getValue(), i, j))
                        moves.add(new Move(entry.getKey(), i, j));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return moves.get(new Random().nextInt(moves.size()));
    }
}