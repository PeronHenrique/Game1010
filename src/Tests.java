// import GameLogic.GameBoard;
// import GameLogic.Piece;
// import GameLogic.Pieces;
// import GameUI.TerminalRenderer.TerminalUI;

// public class Tests {

//     static public void runTests() {
//         BasicFunctionalityTest();
//         PiecesTest();
//         PiecesGetTest();
//         BoardUpdateTest();
//         BoardUpdateStaticTest();
//         TerminalGetTest();
//     }


//     static private void BasicFunctionalityTest() {
//         GameBoard gameBoard = new GameBoard(8, 8, "Game 1");

//         Piece piece1 = new Piece(31, 4, 2, "Piece 1");
//         piece1.setDefaultMask(8, 8);
//         TerminalUI.printPiece(piece1);
//         Piece piece2 = new Piece(11, 2, 2, "Piece 2");
//         piece2.setDefaultMask(8, 8);
//         TerminalUI.printPiece(piece2);

//         gameBoard.setPiece(piece1, 0, 2);
//         TerminalUI.printBoard(gameBoard);
//         gameBoard.setPiece(piece1, 1, 0);
//         TerminalUI.printBoard(gameBoard);
//         gameBoard.setPiece(piece1, 1, 3);
//         TerminalUI.printBoard(gameBoard);
//         gameBoard.setPiece(piece2, 3, 1);
//         TerminalUI.printBoard(gameBoard);
//         gameBoard.setPiece(piece2.getDefaultMask() << (6 + 6 * 8));
//         TerminalUI.printBoard(gameBoard);
//     }

//     static private void PiecesTest() {
//         for (Piece p : Pieces.List)
//             TerminalUI.printPiece(p);
//     }

//     static private void PiecesGetTest() {
//         for (int i = 0; i < 20; i++)
//             TerminalUI.printPiece(Pieces.getRandonPiece());
//     }

//     private static void BoardUpdateTest() {
//         GameBoard gameBoard = new GameBoard(8, 8, "Game 1");
//         Piece piece = Pieces.getPieceByname("line 4V").orElse(new Piece(15, 4, 0, "line 4H"));
//         Piece piece2 = new Piece(255, 2, 4, "teste");

//         gameBoard.setPiece(piece, 0, 0);
//         TerminalUI.printBoard(gameBoard);
//         gameBoard.updateBoard();
//         TerminalUI.printBoard(gameBoard);
//         TerminalUI.printSeparator();

//         gameBoard.setPiece(piece, 1, 0);
//         TerminalUI.printBoard(gameBoard);
//         gameBoard.updateBoard();
//         TerminalUI.printBoard(gameBoard);
//         TerminalUI.printSeparator();

//         gameBoard.setPiece(piece, 2, 0);
//         TerminalUI.printBoard(gameBoard);
//         gameBoard.updateBoard();
//         TerminalUI.printBoard(gameBoard);
//         TerminalUI.printSeparator();

//         gameBoard.setPiece(piece, 0, 4);
//         TerminalUI.printBoard(gameBoard);
//         gameBoard.updateBoard();
//         TerminalUI.printBoard(gameBoard);
//         TerminalUI.printSeparator();

//         gameBoard.setPiece(piece, 0, 0);
//         TerminalUI.printBoard(gameBoard);
//         gameBoard.updateBoard();
//         TerminalUI.printBoard(gameBoard);
//         TerminalUI.printSeparator();

//         gameBoard.setPiece(piece2, 0, 4);
//         TerminalUI.printBoard(gameBoard);
//         gameBoard.updateBoard();
//         TerminalUI.printBoard(gameBoard);
//         TerminalUI.printSeparator();

//     }

//     private static void BoardUpdateStaticTest() {
//         long gameBoard = 0;
//         Piece piece = Pieces.getPieceByname("line 4H").orElse(new Piece(15, 4, 0, "line 4H"));
//         piece.setDefaultMask(8, 8);
//         long piecemask = piece.getDefaultMask();

//         gameBoard = GameBoard.setPiece(gameBoard, piecemask);
//         TerminalUI.printLong(gameBoard, 8, 8);
//         gameBoard = GameBoard.getNewBoard(gameBoard, 255, 72340172838076673l, 8, 8);
//         TerminalUI.printLong(gameBoard, 8, 8);
//         TerminalUI.printSeparator();

//         gameBoard = GameBoard.setPiece(gameBoard, piecemask << 4);
//         TerminalUI.printLong(gameBoard, 8, 8);
//         gameBoard = GameBoard.getNewBoard(gameBoard, 255, 72340172838076673l, 8, 8);
//         TerminalUI.printLong(gameBoard, 8, 8);
//         TerminalUI.printSeparator();

//         gameBoard = GameBoard.setPiece(gameBoard, piecemask << (4 * 8 + 4));
//         TerminalUI.printLong(gameBoard, 8, 8);
//         gameBoard = GameBoard.getNewBoard(gameBoard, 255, 72340172838076673l, 8, 8);
//         TerminalUI.printLong(gameBoard, 8, 8);
//         TerminalUI.printSeparator();
//     }

    
//     private static void TerminalGetTest() {
//         int pieceIndex = TerminalUI.getPieceIndex(3);
//         TerminalUI.printLine("read piece index: " + pieceIndex);

//         int positionX = TerminalUI.getPositionX(5);
//         TerminalUI.printLine("read position X: " + positionX);

//         int positionY = TerminalUI.getPositionY(8);
//         TerminalUI.printLine("read position Y: " + positionY);
//     }

// }
