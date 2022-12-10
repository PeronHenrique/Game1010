package GameLogic;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Pieces {
    public static final ArrayList<Piece> List = new ArrayList<>() {
        {
            add(new Piece(1, 1, 1, "square 1"));
            add(new Piece(15, 2, 2, "square 2"));
            add(new Piece(511, 3, 3, "square 3"));

            add(new Piece(3, 1, 2, "line 2V"));
            add(new Piece(3, 2, 1, "line 2H"));

            add(new Piece(7, 1, 3, "line 3V"));
            add(new Piece(7, 3, 1, "line 3H"));

            add(new Piece(15, 1, 4, "line 4V"));
            add(new Piece(15, 4, 1, "line 4H"));

            add(new Piece(31, 1, 5, "line 5V"));
            add(new Piece(31, 5, 1, "line 5H"));

            add(new Piece(15 - 1, 2, 2, "L shape 2.1"));
            add(new Piece(15 - 2, 2, 2, "L shape 2.2"));
            add(new Piece(15 - 4, 2, 2, "L shape 2.3"));
            add(new Piece(15 - 8, 2, 2, "L shape 2.4"));

            add(new Piece(511 - 1 - 2 - 8 - 16, 3, 3, "L shape 3.1"));
            add(new Piece(511 - 2 - 4 - 16 - 32, 3, 3, "L shape 3.2"));
            add(new Piece(511 - 8 - 16 - 64 - 128, 3, 3, "L shape 3.3"));
            add(new Piece(511 - 16 - 32 - 128 - 256, 3, 3, "L shape 3.4"));

            add(new Piece(511 - 1 - 4 - 64 - 256, 3, 3, "cross"));

            add(new Piece(511 - 8 - 32 - 64 - 256, 3, 3, "T shape 1"));
            add(new Piece(511 - 2 - 4 - 128 - 256, 3, 3, "T shape 2"));
            add(new Piece(511 - 1 - 4 - 8 - 32, 3, 3, "T shape 3"));
            add(new Piece(511 - 1 - 2 - 64 - 128, 3, 3, "T shape 4"));

            add(new Piece(511 - 16, 3, 3, "ring"));
        }
    };

    public static Piece getRandonPiece() {
        int n = new Random().nextInt(List.size());
        return List.get(n);
    }
    
    public static Piece getRandonPiece(String newName) {
        Piece piece = getRandonPiece();
        piece.setName(newName);
        return piece;
    }
    
    public static Piece getPieceByIndex(int index) {
        return List.get(index);
    }

    public static Optional<Piece> getPieceByname(String name) {
        for (Piece piece : List)
            if (piece.getName().equals(name))
                return Optional.of(piece);

        return Optional.empty();
    }

}
