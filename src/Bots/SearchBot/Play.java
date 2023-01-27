package Bots.SearchBot;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import GameLogic.Move;

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

    public void CalculateScore() {
        // TODO: Calculate score
    }
}
