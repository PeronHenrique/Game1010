package GameLogic;

import java.math.BigInteger;

public class GameBoard {
    final public String name;
    final public int width;
    final public int height;

    private BigInteger board;

    public BigInteger getBoardBigInteger() {
        return new BigInteger(board.toString());
    }

    private BigInteger rowMask;
    private BigInteger columnMask;

    public GameBoard(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;

        board = BigInteger.valueOf(0);
        rowMask = BigInteger.valueOf(0);
        columnMask = BigInteger.valueOf(0);

        for (int i = 0; i < width; i++)
            rowMask = rowMask.shiftLeft(1).add(BigInteger.ONE);

        for (int i = 0; i < height; i++)
            columnMask = columnMask.shiftLeft(width).add(BigInteger.ONE);
    }

    public boolean setPiece(Piece piece, int x, int y) {
        if (isPieceOutofBounds(piece, x, y))
            return false;

        BigInteger pieceMask = piece.getPieceMask(width, height);
        return setPiece(pieceMask, x, y);
    }

    public boolean setPiece(BigInteger pieceMask, int x, int y) {
        pieceMask = pieceMask.shiftLeft(x + y * width);
        return setPiece(pieceMask);
    }

    public boolean setPiece(BigInteger pieceMask) {
        BigInteger tempBoard = new BigInteger(board.toString());
        board = setPiece(board, pieceMask);
        return !tempBoard.equals(board);
    }

    public static BigInteger setPiece(BigInteger board, BigInteger pieceMask) {
        if (!isMoveValid(board, pieceMask))
            return board;

        board = board.or(pieceMask);
        return board;
    }

    public boolean isMoveValid(Piece piece, int x, int y) {
        if (isPieceOutofBounds(piece, x, y))
            return false;
        return isMoveValid(board, piece.getPieceMask(width, height).shiftLeft(x + y * width));
    }

    public static boolean isMoveValid(BigInteger board, BigInteger pieceMask) {
        if (pieceMask.equals(BigInteger.ZERO))
            return false;

        return pieceMask.and(board).equals(BigInteger.ZERO);
    }

    public boolean isPieceOutofBounds(Piece piece, int x, int y) {
        if (x + piece.width > width)
            return true;
        if (x < 0)
            return true;
        if (y + piece.height > height)
            return true;
        if (y < 0)
            return true;
        return false;
    }

    public void updateBoard() {
        board = getNewBoard(board, rowMask, columnMask, width, height);
    }

    public int getNewPoints() {
        return getNewPoints(board, rowMask, columnMask, width, height);
    }

    public static int getNewPoints(BigInteger board, BigInteger rowMask, BigInteger columnMask, int width, int height) {
        int p = 0;
        for (int i = 0; i < width; i++)
            if (board.and(columnMask.shiftLeft(i)).equals(columnMask.shiftLeft(i)))
                p++;

        for (int i = 0; i < height; i++)
            if (board.and(rowMask.shiftLeft(i * width)).equals(rowMask.shiftLeft(i * width)))
                p++;

        return (p * (p + 1) * (width + height)) / 4;
    }

    public static BigInteger getNewBoard(BigInteger board, BigInteger rowMask, BigInteger columnMask, int width,
            int height) {
        BigInteger boardCopy = new BigInteger(board.toString());
        for (int i = 0; i < width; i++)
            if (boardCopy.and(columnMask.shiftLeft(i)).equals(columnMask.shiftLeft(i)))
                board = board.andNot(columnMask.shiftLeft(i));

        for (int i = 0; i < height; i++)
            if (boardCopy.and(rowMask.shiftLeft(i * width)).equals(rowMask.shiftLeft(i * width)))
                board = board.andNot(rowMask.shiftLeft(i * width));

        return board;
    }

}
