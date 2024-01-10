package com.andyphan.chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Objects;

public class ChessPiece extends ImageView {
    private Alliance alliance;
    private int row;
    private int col;
    private ArrayList<PieceMoves> candidateMoves = new ArrayList<>();
    public ChessPiece(String imageName) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pieces/" + imageName)));
        setFitHeight(ChessBoard.TILE_SIZE);
        setFitWidth(ChessBoard.TILE_SIZE);
        setImage(image);
        if (imageName.contains("white")) setAlliance(Alliance.WHITE);
        else setAlliance(Alliance.BLACK);
    }
    public void setAlliance(Alliance alliance) {
        this.alliance = alliance;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public ArrayList<PieceMoves> getCandidateMoves() {
        return null;
    }
}
