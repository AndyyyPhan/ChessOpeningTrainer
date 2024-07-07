package com.andyphan.chess.pieces;

import com.andyphan.chess.Alliance;
import com.andyphan.chess.ChessBoard;
import com.andyphan.chess.ChessPiece;
import com.andyphan.chess.Tile;

public class Pawn extends ChessPiece {
    public Pawn(ChessBoard chessBoard, String imagePath) {
        super(chessBoard, imagePath);
    }
    @Override
    public boolean isValidMove(Tile targetTile) {
        if (targetTile.getChessPiece() != null) {
            if (targetTile.getChessPiece().getClass() == King.class ||
                    (targetTile.getChessPiece().getAlliance() == getAlliance())) return false;
        }
        if (getAlliance() == Alliance.WHITE) {
            return ((targetTile.getChessPiece() == null && targetTile.getRow() == getRow() - 1 && targetTile.getCol() == getCol()) ||
                    (chessBoard.getChessGridTileByName(getRow() - 1, getCol()).getChessPiece() == null &&
                            targetTile.getChessPiece() == null && (targetTile.getRow() == getRow() - 1 ||
                            targetTile.getRow() == getRow() - 2) && getRow() == 6 && targetTile.getCol() == getCol()) ||
                    (getCol() == targetTile.getCol() + 1 || getCol() == targetTile.getCol() - 1) &&
                            (targetTile.getRow() == getRow() - 1) && targetTile.getChessPiece() != null);
        }
        else if (getAlliance() == Alliance.BLACK) {
            return ((targetTile.getChessPiece() == null && targetTile.getRow() == getRow() + 1 && targetTile.getCol() == getCol()) ||
                    (chessBoard.getChessGridTileByName(getRow() + 1, getCol()).getChessPiece() == null &&
                            targetTile.getChessPiece() == null && (targetTile.getRow() == getRow() + 1 ||
                            targetTile.getRow() == getRow() + 2) && getRow() == 1 && targetTile.getCol() == getCol()) ||
                    (getCol() == targetTile.getCol() + 1 || getCol() == targetTile.getCol() - 1) &&
                            (targetTile.getRow() == getRow() + 1) && targetTile.getChessPiece() != null);
        }
        return false;
    }

    @Override
    public boolean isCheck() {
        if (getAlliance() == Alliance.WHITE) {
            Tile possibleLeftCheckTile = chessBoard.getChessGridTileByName(getRow() - 1, getCol() - 1);
            Tile possibleRightCheckTile = chessBoard.getChessGridTileByName(getRow() - 1, getCol() + 1);
            if (possibleLeftCheckTile.getChessPiece() == null && possibleRightCheckTile.getChessPiece() == null) return false;
            return ((possibleLeftCheckTile.getChessPiece() != null && possibleLeftCheckTile.getChessPiece().getClass() == King.class && possibleLeftCheckTile.getChessPiece().getAlliance() == Alliance.BLACK)
                    || (possibleRightCheckTile.getChessPiece() != null && possibleRightCheckTile.getChessPiece().getClass() == King.class && possibleRightCheckTile.getChessPiece().getAlliance() == Alliance.BLACK));
        }
        else if (getAlliance() == Alliance.BLACK) {
            Tile possibleLeftCheckTile = chessBoard.getChessGridTileByName(getRow() + 1, getCol() - 1);
            Tile possibleRightCheckTile = chessBoard.getChessGridTileByName(getRow() + 1, getCol() + 1);
            if (possibleLeftCheckTile.getChessPiece() == null && possibleRightCheckTile.getChessPiece() == null) return false;
            return ((possibleLeftCheckTile.getChessPiece() != null && possibleLeftCheckTile.getChessPiece().getClass() == King.class && possibleLeftCheckTile.getChessPiece().getAlliance() == Alliance.WHITE)
                    || (possibleRightCheckTile.getChessPiece() != null && possibleRightCheckTile.getChessPiece().getClass() == King.class && possibleRightCheckTile.getChessPiece().getAlliance() == Alliance.WHITE));
        }
        return false;
    }
}
