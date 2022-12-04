package GameLogic;

public class Move {
    public final int pieceIndex;
    public final int X;
    public final int Y;

    public Move(int pieceIndex, int X, int Y) {
        this.pieceIndex = pieceIndex;
        this.X = X;
        this.Y = Y;
    }
}