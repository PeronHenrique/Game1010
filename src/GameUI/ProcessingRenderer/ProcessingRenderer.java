package GameUI.ProcessingRenderer;

import java.util.Map;

import GameLogic.GameBoard;
import GameLogic.Move;
import GameLogic.Piece;
import GameUI.UIRenderer;
import processing.core.PApplet;
import processing.core.PGraphics;

public class ProcessingRenderer extends PApplet implements UIRenderer {

    public final int LIGHT_GREEN = color(146, 177, 97);
    public final int DARK_GREEN = color(101, 157, 93);
    public final int LIGHT_BLUE = color(93, 155, 170);
    public final int DARK_BLUE = color(101, 124, 178);
    public final int DARKER_BLUE = color(117, 102, 178);
    public final int LIGHT_PURPLE = color(184, 116, 167);
    public final int DARK_PURPLE = color(166, 114, 182);
    public final int PINK = color(203, 108, 134);
    public final int ORANGE = color(203, 135, 109);
    public final int LIGHTER_GREY = color(213, 220, 213);
    public final int LIGHT_GREY = color(203, 205, 195);
    public final int MEDIUM_GREY = color(147, 156, 169);
    public final int DARK_GREY = color(127, 136, 149);

    private final int BACKGROUND = color(221, 224, 203);
    private final int SELECTED_COLOR = color(201, 204, 183);
    private final int TEXT_COLOR = MEDIUM_GREY;
    private final int EMPTY_COLOR = LIGHT_GREY;
    private final int PIECE_COLOR = ORANGE;

    private PGraphics canva;
    private PGraphics helper;

    private int bWidth;
    private int bHeight;
    private long board;
    private int points;
    private Piece[] pieces;

    private int moveP = 3;
    private int moveX = 20;
    private int moveY = 20;

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
    public void mousePressed() {

        // if clicked in the board
        int x = 300;
        int y = 350;
        int w = 200;
        int h = 200;
        int mX = mouseX * 5 / 4;
        int mY = mouseY * 5 / 4;
        if (mX > x - w && mX < x + w && mY > y - h && mY < y + h && moveP < pieces.length) {
            moveX = (mX - x + w) / (400 / bWidth);
            moveY = (mY - y + h) / (400 / bHeight);
            return;
        }

        // if clicked in a piece
        y = 675;
        w = 65;
        h = 65;
        for (int i = 0; i < pieces.length; i++) {
            x = ((i + 1) * canva.width) / (pieces.length + 1);
            if (mX > x - w && mX < x + w && mY > y - h && mY < y + h && pieces[i] != null) {
                moveP = i;
                return;
            }
        }
    }

    @Override
    public void draw() {
        canva.beginDraw();
        canva.background(BACKGROUND);
        drawPoints();

        helper = drawBitmap(board, bHeight, bWidth, helper);
        canva.image(helper, 300, 350, 400, 400);

        for (int i = 0; i < pieces.length; i++) {
            canva.rectMode(CENTER);
            canva.stroke(TEXT_COLOR);
            if (moveP == i)
                canva.fill(SELECTED_COLOR);
            else
                canva.fill(BACKGROUND);

            canva.rect(((i + 1) * canva.width) / (pieces.length + 1), 675, 130, 130, 10);
            if (pieces[i] != null) {
                helper = drawBitmap(pieces[i].bitmap, pieces[i].height, pieces[i].width, helper);

                canva.image(helper, ((i + 1) * canva.width) / (pieces.length + 1), 675);
            }
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
        screen = createGraphics(columns * 25, rows * 25);
        screen.beginDraw();
        screen.pushStyle();

        screen.rectMode(CORNER);
        screen.background(0, 0, 0, 0);
        screen.noStroke();

        long mask = 1;
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < columns; i++) {
                float x_ = 25 * i + 2;
                float y_ = 25 * j + 2;

                if ((bitmap & mask) != 0)
                    screen.fill(PIECE_COLOR);
                else
                    screen.fill(EMPTY_COLOR);

                mask <<= 1;
                screen.rect(x_, y_, 21, 21, 7);
            }
        }

        screen.popStyle();
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
        displayGame(gameBoard, points, pieces);
        this.GameOver = true;
    }

    @Override
    public void displayInvalidMove() {

    }

    @Override
    public Move getMove(int nPieces, int width, int height) {
        if (moveP < nPieces && moveX < width && moveY < height) {
            Move move = new Move(moveP, moveX, moveY);
            moveP = nPieces;
            moveX = width;
            moveY = height;
            return move;
        }
        
        return new Move(nPieces, width, height);
    }

}
