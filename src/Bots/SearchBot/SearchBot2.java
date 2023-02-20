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

public class SearchBot2 implements Bot {

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

    private class Play {

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

        public void CalculateScore(GameBoard gameBoard) {
            score = gameBoard.height * gameBoard.height * gameBoard.width * 3; // maximum score possible

            int[] columns = new int[gameBoard.width];
            int[] topCell = new int[gameBoard.width];
            for (int i = 0; i < gameBoard.width; i++)
                topCell[i] = gameBoard.height;

            for (int i = 0; i < gameBoard.width; i++) {
                BigInteger column = board.and(gameBoard.getColumnMask().shiftLeft(i));
                for (int j = 0; j < gameBoard.height; j++) {
                    if (!column.and(BigInteger.ONE.shiftLeft(j * gameBoard.width + i)).equals(BigInteger.ZERO)) {
                        if (topCell[i] == gameBoard.height)
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

            for (int j = 0; j < gameBoard.height - topCell[i]; j++) {
                if ((columns[i] & (0x01 << j)) == 0)
                    points += j + 1;
            }

            return points;
        }

        private long ruleLeft(int[] columns, int[] topCell, GameBoard gameBoard, int i) {
            if (i == 0)
                return getSidePoints(columns[i], 0);

            return getSidePoints(columns[i], gameBoard.height - topCell[i - 1]);
        }

        private long ruleRight(int[] columns, int[] topCell, GameBoard gameBoard, int i) {
            if (i == gameBoard.width - 1)
                return getSidePoints(columns[i], 0);

            return getSidePoints(columns[i], gameBoard.height - topCell[i + 1]);
        }

        private long getSidePoints(int column, int topCell) {
            int points = 0;

            for (int j = 0; j < topCell; j++) {
                if ((column & (0x01 << j)) == 0)
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

}