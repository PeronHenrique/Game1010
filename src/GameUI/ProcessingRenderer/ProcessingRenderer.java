package GameUI.ProcessingRenderer;

import java.util.Map;

import GameLogic.GameBoard;
import GameLogic.Move;
import GameLogic.Piece;
import GameUI.UIRenderer;
import processing.core.PApplet;
import processing.core.PGraphics;

public class ProcessingRenderer extends PApplet implements UIRenderer {

    final int LIGHT_GREEN = color(146, 177, 97);
    final int DARK_GREEN = color(101, 157, 93);
    final int LIGHT_BLUE = color(93, 155, 170);
    final int DARK_BLUE = color(101, 124, 178);
    final int DARKER_BLUE = color(117, 102, 178);
    final int LIGHT_PURPLE = color(184, 116, 167);
    final int DARK_PURPLE = color(166, 114, 182);
    final int PINK = color(203, 108, 134);
    final int ORANGE = color(203, 135, 109);
    final int LIGHTER_GREY = color(221, 224, 203);
    final int LIGHT_GREY = color(203, 205, 195);
    final int MEDIUM_GREY = color(147, 156, 169);
    final int DARK_GREY = color(127, 136, 149);

    final int BACKGROUND = LIGHTER_GREY;
    final int TEXT_COLOR = MEDIUM_GREY;
    final int EMPTY_COLOR = LIGHT_GREY;
    final int PIECE_COLOR = PINK;

    private PGraphics canva;
    private PGraphics helper;

    private int bWidth;
    private int bHeight;
    private long board;
    private int points;
    private Piece[] pieces;
    private boolean GameOver;

    public ProcessingRenderer(int bWidth, int bHeight, int nPieces) {
        PApplet.runSketch(new String[] { "ProcessingRenderer" }, this);
        pieces = new Piece[nPieces];
        points = 0;
        board = 0;
        this.bWidth = bWidth;
        this.bHeight = bHeight;
        GameOver = false;
    }

    @Override
    public void settings() {
        size(480, 640);
    }

    @Override
    public void setup() {
        canva = createGraphics(600, 800);
        canva.imageMode(CENTER);
    }

    @Override
    public void draw() {
        canva.beginDraw();
        canva.background(BACKGROUND);
        drawPoints();

        helper = drawBitmap(board, bHeight, bWidth, helper);
        canva.image(helper, 300, 350, 400, 400);

        for (int i = 0; i < pieces.length; i++)
            if (pieces[i] != null) {
                helper = drawBitmap(pieces[i].bitmap, pieces[i].height, pieces[i].width, helper);
                canva.image(helper, ((i + 1) * canva.width) / (pieces.length + 1), 675);
            }

        canva.endDraw();
        image(canva, 0, 0, width, height);
    }

    private void drawPoints() {
        canva.textSize(50);
        canva.textAlign(CENTER, CENTER);
        canva.fill(TEXT_COLOR);
        if (GameOver) {
            canva.text("GAME OVER", 300, 50);
            canva.text(nfc(points), 300, 100);
        } else {
            canva.text(nfc(points), 300, 75);
        }
    }

    private PGraphics drawBitmap(long bitmap, int rows, int columns, PGraphics screen) {
        screen = createGraphics(columns * 30, rows * 30);
        screen.beginDraw();
        screen.background(BACKGROUND);
        screen.noStroke();
        long mask = 1;
            for (int i = 0; i < columns; i++) {
        for (int j = 0; j < rows; j++) {
                float x_ = 30 * i + 2;
                float y_ = 30 * j + 2;

                if ((bitmap & mask) != 0)
                    screen.fill(PIECE_COLOR);
                else
                    screen.fill(EMPTY_COLOR);

                mask <<= 1;
                screen.rect(x_, y_, 28, 28, 8);
            }
        }
        screen.endDraw();
        return screen;
    }

    @Override
    public void displayGame(GameBoard gameBoard, int points, Map<Integer, Piece> pieces) {
        this.points = points;
        this.board = gameBoard.getBoardLong();

        for (int i = 0; i < this.pieces.length; i++)
            this.pieces[i] = pieces.get(i);
    }

    @Override
    public void drawGameOver(GameBoard gameBoard, int points, Map<Integer, Piece> pieces) {
        this.GameOver = true;

    }

    @Override
    public void displayInvalidMove() {

    }

    @Override
    public Move getMove(int nPieces, int width, int height) {
        // TODO Auto-generated method stub
        return new Move(0, 0, 0);
    }

}
