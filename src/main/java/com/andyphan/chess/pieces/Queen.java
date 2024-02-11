package com.andyphan.chess.pieces;

import com.andyphan.chess.ChessBoard;
import com.andyphan.chess.ChessPiece;
import com.andyphan.chess.Tile;

public class Queen extends ChessPiece {
    public Queen(ChessBoard chessBoard, String imagePath) {
        super(chessBoard, imagePath);
    }

    @Override
    public boolean isValidMove(Tile targetTile) {
        if (targetTile.getChessPiece() != null) {
            if (targetTile.getChessPiece().getClass() == King.class ||
                    (targetTile.getChessPiece().getAlliance() == getAlliance())) return false;
        }
        Bishop bishop = new Bishop(chessBoard, getImageName());
        bishop.setTile(getTile());
        bishop.setAlliance(getAlliance());
        Rook rook = new Rook(chessBoard, getImageName());
        rook.setTile(getTile());
        rook.setAlliance(getAlliance());
        return bishop.isValidMove(targetTile) || rook.isValidMove(targetTile);
    }
}
