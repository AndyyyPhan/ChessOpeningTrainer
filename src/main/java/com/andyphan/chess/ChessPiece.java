package com.andyphan.chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ChessPiece extends ImageView {
    public ChessPiece(String imageName) {
//        Image image = new Image(getResource("/pieces/" + imageName));
        Image image = new Image(getClass().getResourceAsStream("/pieces/" + imageName));
        setFitHeight(ChessBoard.TILE_SIZE);
        setFitWidth(ChessBoard.TILE_SIZE);
        setImage(image);
    }

    private static String getResource(String filename) {
        return Objects.requireNonNull(ChessPiece.class.getResource(filename)).toExternalForm();
    }
}
