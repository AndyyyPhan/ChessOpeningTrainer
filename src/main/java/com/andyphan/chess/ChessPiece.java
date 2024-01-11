package com.andyphan.chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Objects;

public class ChessPiece extends ImageView {
    private Alliance alliance;
    private Tile tile;
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

    public Tile getTile() {
        return tile;
    }
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public ArrayList<PieceMoves> getCandidateMoves() {
        return null;
    }
}
