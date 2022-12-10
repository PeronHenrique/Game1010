package GameUI.TerminalRenderer;

import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import GameLogic.GameBoard;
import GameLogic.Move;
import GameLogic.Piece;

public class TerminalUI {

    private static Scanner scanner;

    public static void printPieceHashMap(Map<Integer, Piece> pieces) {

        int maxHeight = 0;
        for (Entry<Integer, Piece> entry : pieces.entrySet()) {
            System.out.print("Piece " + entry.getKey() + ":      ");
            int pieceHeight = entry.getValue().height;
            if (maxHeight < pieceHeight)
                maxHeight = pieceHeight;
        }
        System.out.println("");

        for (int j = 0; j < maxHeight; j++) {
            for (Entry<Integer, Piece> entry1 : pieces.entrySet()) {
                Piece piece = entry1.getValue();
                if (j >= piece.height) {
                    for (int i = 0; i < 14; i++)
                        System.out.print(" ");
                    continue;
                }

                long mask = 1;
                mask <<= piece.width * j;

                long board = piece.bitmap;
                for (int i = 0; i < piece.width; i++) {
                    if ((board & mask) != 0)
                        System.out.print(" @");
                    else
                        System.out.print(" .");
                    mask <<= 1;
                }

                for (int i = 0; i < 14 - piece.width * 2; i++)
                    System.out.print(" ");
            }
            System.out.println("");
        }

        System.out.println("");
    }

    public static void printLong(long board, int boardWidth, int boardHeight) {
        long mask = 1;
        for (int j = 0; j < boardHeight; j++) {
            for (int i = 0; i < boardWidth; i++) {
                if ((board & mask) != 0)
                    System.out.print(" @");
                else
                    System.out.print(" .");
                mask <<= 1;
            }
            System.out.println("");
        }

        System.out.println("");
    }

    public static void printPiece(Piece piece) {
        System.out.println(piece.getName() + ": ");
        printLong(piece.bitmap, piece.width, piece.height);
    }

    public static void printPiece(Piece piece, String name) {
        System.out.println(name + ": ");
        printLong(piece.bitmap, piece.width, piece.height);
    }

    public static void printBoard(GameBoard gameBoard) {
        System.out.println(gameBoard.name + ": ");
        printLong(gameBoard.getBoardLong(), gameBoard.width, gameBoard.height);
    }

    public static void printBoard(GameBoard gameBoard, int points) {
        System.out.println(gameBoard.name + ": \nPontos: " + points);
        printLong(gameBoard.getBoardLong(), gameBoard.width, gameBoard.height);
    }

    public static void printSeparator() {
        System.out.println("*****************************");
        System.out.println("");
    }

    public static void printLine(String text) {
        System.out.println(text);
    }

    public static int getPieceIndex(int max) {
        return getNumberFromUser(max, "Enter piece index");
    }

    public static int getPositionX(int max) {
        return getNumberFromUser(max, "Enter board position X");
    }

    public static int getPositionY(int max) {
        return getNumberFromUser(max, "Enter board position Y");
    }

    public static int getNumberFromUser(int max, String text) {
        System.out.println("");
        System.out.println(text);

        if (scanner == null)
            scanner = new Scanner(System.in);

        while (true) {
            String numberStr = scanner.nextLine(); // Read user input
            int number;
            try {
                number = Integer.parseInt(numberStr);
                if (number < max && number >= 0)
                    return number;
                else
                    System.out.println("Enter a number in range [0, " + max + "), please");
                continue;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number, please");
                continue;
            }
        }

    }


    public static Move getUserMove(int i, int width, int height) {
        System.out.println("Enter your move: (index, X, Y)");

        if (scanner == null)
            scanner = new Scanner(System.in);

        String line = scanner.nextLine(); // Read user input
        String[] entrys = line.split(",");

        if (entrys.length != 3)
            return new Move(i, width, height);

        try {
            int[] numbers = {
                    Integer.parseInt(entrys[0]),
                    Integer.parseInt(entrys[1]),
                    Integer.parseInt(entrys[2])
            };

            if (!(numbers[0] < i && numbers[0] >= 0))
                return new Move(i, width, height);
            else if (!(numbers[1] < width && numbers[1] >= 0))
                return new Move(i, width, height);
            else if (!(numbers[2] < height && numbers[2] >= 0))
                return new Move(i, width, height);
            else
                return new Move(numbers[0], numbers[1], numbers[2]);
        } catch (NumberFormatException e) {
            return new Move(i, width, height);
        }
    }

}
