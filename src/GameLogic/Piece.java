package GameLogic;

import java.math.BigInteger;

public class Piece {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        if (newName == null)
            return;
        if (newName.isEmpty())
            return;
        name = newName;
    }

    final public int width;
    final public int height;

    final public BigInteger bitmap;

    private BigInteger rowMask;
    private BigInteger defaultPieceMask;

    public Piece(long bitmap, int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.bitmap = BigInteger.valueOf(bitmap);
        this.name = name;

        rowMask = BigInteger.valueOf(0);
        for (int i = 0; i < width; i++)
            rowMask = rowMask.shiftLeft(1).add(BigInteger.ONE);

        setDefaultMask(this.width, this.height);
    }

    /**
     * Gets a mask of the piece in position (0,0)
     * for a board of the given size
     * 
     * @param boardWidth
     * @param boardHeight
     * @return
     */
    public BigInteger getPieceMask(int boardWidth, int boardHeight) {
        BigInteger piecemask = BigInteger.valueOf(0);
        int offset = boardWidth - this.width;

        for (int i = 0; i < this.height; i++)
            piecemask = piecemask.or(bitmap.and(rowMask.shiftLeft(this.width * i)).shiftLeft(offset * i));

        return piecemask;
    }

    /**
     * Calculates the piece mask for a given board size
     * and store it
     * 
     * @param boardWidth
     * @param boardHeight
     */
    public void setDefaultMask(int boardWidth, int boardHeight) {
        defaultPieceMask = getPieceMask(boardWidth, boardHeight);
    }

    /**
     * Gets the default piece mask
     * 
     * @return
     */
    public BigInteger getDefaultMask() {
        return defaultPieceMask;
    }

}
