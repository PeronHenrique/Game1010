package Bots.SearchBot;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Bots.Bot;
import GameLogic.GameBoard;
import GameLogic.Move;
import GameLogic.Piece;

public class SearchBot implements Bot {

    List<Move> bestMoves = new ArrayList<>();

    @Override
    public Move getMove(GameBoard gameBoard, Map<Integer, Piece> pieces) {

        if (bestMoves.isEmpty()) {

            List<Play> plays = getAllPlays(gameBoard, pieces);

            for (Play play : plays)
                play.CalculateScore(gameBoard);

            bestMoves = findBestScore(plays).getMoves();
        }

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return bestMoves.remove(0);
    }

    private Play findBestScore(List<Play> plays) {
        int maxIndex = 0;
        long maxScore = plays.get(0).score;

        for (int i = 1; i < plays.size(); i++) {
            if (plays.get(i).score > maxScore) {
                maxScore = plays.get(i).score;
                maxIndex = i;
            }
        }
        return plays.get(maxIndex);
    }

    private List<List<Integer>> getAllMoveCombos(Map<Integer, Piece> pieces) {
        List<List<Integer>> oldMoveCombos = new ArrayList<>();

        for (Integer i : pieces.keySet()) {
            List<Integer> list = new ArrayList<>();
            list.add(i);
            oldMoveCombos.add(list);
        }

        for (int i = 0; i < pieces.keySet().size() - 1; i++) {
            List<List<Integer>> newMoveCombos = new ArrayList<>();
            for (List<Integer> list : oldMoveCombos) {
                for (Integer k : pieces.keySet()) {
                    List<Integer> newList = new ArrayList<>(list);
                    if (newList.contains(k))
                        continue;
                    newList.add(k);
                    newMoveCombos.add(newList);
                }
            }
            oldMoveCombos = newMoveCombos;
        }

        return oldMoveCombos;
    }

    private List<Play> getAllPlays(GameBoard gameBoard, Map<Integer, Piece> pieces) {
        List<Play> plays = new ArrayList<>();

        List<List<Integer>> moveCombos = getAllMoveCombos(pieces);
        Set<BigInteger> boardsAchieved = new HashSet<>();

        for (List<Integer> moveCombo : moveCombos) {
            plays.addAll(getPlays(gameBoard, pieces, boardsAchieved, moveCombo));
        }

        return plays;
    }

    private List<Play> getPlays(GameBoard gameBoard, Map<Integer, Piece> pieces,
            Set<BigInteger> boardsAchieved, List<Integer> moveCombo) {

        List<Play> plays = new ArrayList<>();
        int pieceIndex = moveCombo.get(0);
        BigInteger boardNow = gameBoard.getBoardBigInteger();
        Piece piece = pieces.get(moveCombo.get(0));
        BigInteger piecemask = piece.getDefaultMask();

        for (int x = 0; x < gameBoard.width; x++) {
            for (int y = 0; y < gameBoard.height; y++) {
                if (Play.isValidMove(gameBoard, boardNow, piece, x, y)) {
                    BigInteger board = gameBoard.getBoardBigInteger();
                    board = GameBoard.setPiece(board, piecemask.shiftLeft(x + y * gameBoard.width));

                    if (boardsAchieved.add(board)) {
                        List<Move> moves = new ArrayList<>();
                        moves.add(new Move(pieceIndex, x, y));
                        plays.add(new Play(board, moves));
                    }
                }
            }
        }

        for (int i = 1; i < moveCombo.size(); i++) {

            piece = pieces.get(pieceIndex);
            piecemask = piece.getDefaultMask();
            List<Play> newplays = new ArrayList<>();

            for (Play play : plays) {

                boardNow = play.getboard();

                for (int x = 0; x < gameBoard.width; x++) {
                    for (int y = 0; y < gameBoard.height; y++) {
                        if (Play.isValidMove(gameBoard, boardNow, piece, x, y)) {
                            BigInteger board = play.getboard();
                            board = GameBoard.setPiece(board, piecemask.shiftLeft(x + y * gameBoard.width));

                            if (boardsAchieved.add(board)) {
                                List<Move> newList = play.getMoves();
                                newList.add(new Move(pieceIndex, x, y));
                                newplays.add(new Play(board, newList));
                            }
                        }
                    }
                }
            }
            plays.addAll(newplays);
        }

        return plays;
    }
}