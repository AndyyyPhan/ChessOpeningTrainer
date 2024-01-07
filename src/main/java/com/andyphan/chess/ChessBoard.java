package com.andyphan.chess;

import com.andyphan.chess.pieces.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ChessBoard extends GridPane {
    protected static final int TILE_SIZE = 80;
    protected static final int BOARD_SIZE = 8;

    public ChessBoard() {
        drawBoard();
        initializePieces();
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

    private void initializePieces() {
        for (int col = 0; col < BOARD_SIZE; col++) {
            add(new Pawn("whitePawn.png"), col, 6);
            add(new Pawn("blackPawn.png"), col, 1);
        }
        add(new Knight("whiteKnight.png"), 1, 7);
        add(new Knight("whiteKnight.png"), 6, 7);
        add(new Knight("blackKnight.png"), 1, 0);
        add(new Knight("blackKnight.png"), 6, 0);

        add(new Bishop("whiteBishop.png"), 2, 7);
        add(new Bishop("whiteBishop.png"), 5, 7);
        add(new Bishop("blackBishop.png"), 2, 0);
        add(new Bishop("blackBishop.png"), 5, 0);

        add(new Rook("whiteRook.png"), 0, 7);
        add(new Rook("whiteRook.png"), 7, 7);
        add(new Rook("blackRook.png"), 0, 0);
        add(new Rook("blackRook.png"), 7, 0);

        add(new Queen("whiteQueen.png"), 3, 7);
        add(new Queen("blackQueen.png"), 3, 0);

        add(new King("whiteKing.png"), 4, 7);
        add(new King("blackKing.png"), 4, 0);
    }
}
