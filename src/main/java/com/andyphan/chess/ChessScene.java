package com.andyphan.chess;

import com.andyphan.chessopeningtrainer.ChessOpening;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class ChessScene extends Scene {
    public ChessScene(ChessOpening chessOpening) {
        super(new VBox(10), ChessBoard.BOARD_SIZE*ChessBoard.TILE_SIZE, ChessBoard.BOARD_SIZE*ChessBoard.TILE_SIZE);
        ChessBoard chessBoard = new ChessBoard();
        VBox layout = (VBox) getRoot();
        layout.getChildren().add(chessBoard);
    }
}
