package com.andyphan.chess.pieces;

import com.andyphan.chess.ChessBoard;
import com.andyphan.chess.ChessPiece;
import com.andyphan.chess.Tile;

public class Knight extends ChessPiece {
    public Knight(ChessBoard chessBoard, String imagePath) {
        super(chessBoard, imagePath);
    }
    @Override
    public boolean isValidMove(Tile targetTile) {
        if (targetTile.getChessPiece() != null) {
            if (targetTile.getChessPiece().getClass() == King.class ||
                    (targetTile.getChessPiece().getAlliance() == getAlliance())) return false;
        }
        return (((targetTile.getCol() == getCol() - 1 || targetTile.getCol() == getCol() + 1) &&
                (targetTile.getRow() == getRow() - 2 || targetTile.getRow() == getRow() + 2)) ||
                ((targetTile.getCol() == getCol() - 2 || targetTile.getCol() == getCol() + 2) &&
                        (targetTile.getRow() == getRow() - 1 || targetTile.getRow() == getRow() + 1)));
    }
}
