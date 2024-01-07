package com.andyphan.chess;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ChessBoard extends GridPane {
    protected static final int TILE_SIZE = 80;
    protected static final int BOARD_SIZE = 8;

    public ChessBoard() {
        drawBoard();
    }

    private void drawBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                drawSquare(row, col, (row+col) % 2 == 0 ? Color.rgb(215, 215, 215) : Color.rgb(123, 124, 124));
            }
        }
    }

    private void drawSquare(int row, int col, Color color) {
        Rectangle square = new Rectangle(TILE_SIZE, TILE_SIZE);
        square.setFill(color);
        add(square, col, row);
    }
}
