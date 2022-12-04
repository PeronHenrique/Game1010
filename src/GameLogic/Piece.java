package GameLogic;


public class Piece {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        if(newName == null) return;
        if(newName.isEmpty()) return;
        name = newName;
    }

    final public int width;
    final public int height;

    final public long bitmap;

    private long rowMask;
    private long defaultPieceMask;

    public Piece(long bitmap, int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.bitmap = bitmap;
        this.name = name;

        rowMask = 0;
        for (int i = 0; i < width; i++)
            rowMask = (rowMask << 1) | 1;

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
    public long getPieceMask(int boardWidth, int boardHeight) {
        long piecemask = 0;
        int offset = boardWidth - this.width;

        for (int i = 0; i < this.height; i++)
            piecemask |= ((bitmap & (rowMask << this.width * i)) << offset * i);

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
    public long getDefaultMask() {
        return defaultPieceMask;
    }



}
