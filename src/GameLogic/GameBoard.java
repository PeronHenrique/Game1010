package GameLogic;
/* 
 * byte     	1 byte
 * short    	2 bytes
 * int          4 bytes
 * long 	    8 bytes
 */

public class GameBoard {
    final public String name;
    final public int width;
    final public int height;

    private long board;

    public long getBoardLong() {
        return board;
    }

    private long rowMask = 0;
    private long columnMask = 0;

    public GameBoard(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;

        board = 0;

        for (int i = 0; i < width; i++)
            rowMask = (rowMask << 1) | 1;

        for (int i = 0; i < height; i++) {
            columnMask = (columnMask << width) | 1;
        }
    }

    public boolean setPiece(Piece piece, int x, int y) {
        if (isPieceOutofBounds(piece, x, y))
            return false;

        long pieceMask = piece.getPieceMask(width, height);
        return setPiece(pieceMask, x, y);
    }

    public boolean setPiece(long pieceMask, int x, int y) {
        pieceMask <<= (x + y * width);
        return setPiece(pieceMask);
    }

    public boolean setPiece(long pieceMask) {
        long tempBoard = board;
        board = setPiece(board, pieceMask);
        return tempBoard != board;
    }

    public static long setPiece(long board, long pieceMask) {
        if (!isMoveValid(board, pieceMask))
            return board;

        board |= pieceMask;
        return board;
    }

    public boolean isMoveValid(Piece piece, int x, int y) {
        if(isPieceOutofBounds(piece, x, y)) return false;
        return isMoveValid(board, piece.getPieceMask(width, height) << (x + y * width));
    }

    private static boolean isMoveValid(long board, long pieceMask) {
        if (pieceMask == 0)
            return false;

        if ((pieceMask & board) != 0)
            return false;
        return true;
    }

    private boolean isPieceOutofBounds(Piece piece, int x, int y) {
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

    public static int getNewPoints(long board, long rowMask, long columnMask, int width, int height) {
        int p = 0;
        for (int i = 0; i < width; i++)
            if ((board & (columnMask << i)) == (columnMask << i))
                p++;

        for (int i = 0; i < height; i++)
            if ((board & (rowMask << i * width)) == (rowMask << i * width))
                p++;

        return (p * (p + 1) * (width + height)) / 4;
    }

    public static long getNewBoard(long board, long rowMask, long columnMask, int width, int height) {
        long boardCopy = board;
        for (int i = 0; i < width; i++)
            if ((boardCopy & (columnMask << i)) == (columnMask << i)) 
                board &= ~(columnMask << i);

        for (int i = 0; i < height; i++)
            if ((boardCopy & (rowMask << i * width)) == (rowMask << i * width)) 
                board &= ~(rowMask << i * width);

        return board;
    }

 



}
