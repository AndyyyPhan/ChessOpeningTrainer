package com.andyphan.chess.pieces;

import com.andyphan.chess.Alliance;
import com.andyphan.chess.ChessBoard;
import com.andyphan.chess.ChessPiece;
import com.andyphan.chess.Tile;

public class King extends ChessPiece {
    private boolean hasMoved = false;
    private boolean castling = false;

    public King(ChessBoard chessBoard, String imagePath) {
        super(chessBoard, imagePath);
    }

    @Override
    public boolean isValidMove(Tile targetTile) {
        if (targetTile.getChessPiece() != null) {
            if (targetTile.getChessPiece().getClass() == King.class ||
                    (targetTile.getChessPiece().getAlliance() == getAlliance() && targetTile.getChessPiece().getClass() != Rook.class)) return false;
        }
        if (getAlliance() == Alliance.WHITE) {
            if (!(getHasMoved()) && targetTile.getChessPiece() != null) {
                if (targetTile.getChessPiece().getClass() == Rook.class && !((Rook) targetTile.getChessPiece()).getHasMoved()) {
                    if ((chessBoard.getChessGridTileByName(7, 5).getChessPiece() == null &&
                            chessBoard.getChessGridTileByName(7, 6).getChessPiece() == null && targetTile.getCol() == 7) || (
                            chessBoard.getChessGridTileByName(7, 1).getChessPiece() == null &&
                                    chessBoard.getChessGridTileByName(7, 2).getChessPiece() == null &&
                                    chessBoard.getChessGridTileByName(7, 3).getChessPiece() == null && targetTile.getCol() == 0)) {
                        setCastling(true);
                        return true;
                    }
                }
            }
        }
        else if (getAlliance() == Alliance.BLACK) {
            if (!(getHasMoved()) && targetTile.getChessPiece() != null) {
                if (targetTile.getChessPiece().getClass() == Rook.class && !((Rook) targetTile.getChessPiece()).getHasMoved()) {
                    if ((chessBoard.getChessGridTileByName(0, 5).getChessPiece() == null &&
                            chessBoard.getChessGridTileByName(0, 6).getChessPiece() == null && targetTile.getCol() == 7) || (
                            chessBoard.getChessGridTileByName(0, 1).getChessPiece() == null &&
                                    chessBoard.getChessGridTileByName(0, 2).getChessPiece() == null &&
                                    chessBoard.getChessGridTileByName(0, 3).getChessPiece() == null && targetTile.getCol() == 0)) {
                        setCastling(true);
                        return true;
                    }
                }
            }
        }
        if ((getCol() + 1 == targetTile.getCol() && (getRow() == targetTile.getRow() ||
                getRow() + 1 == targetTile.getRow() || getRow() - 1 == targetTile.getRow())) ||
                (getCol() - 1 == targetTile.getCol() && (getRow() == targetTile.getRow() ||
                        getRow() + 1 == targetTile.getRow() || getRow() - 1 == targetTile.getRow())) ||
                (getRow() - 1 == targetTile.getRow() && getCol() == targetTile.getCol())
                || (getRow() + 1 == targetTile.getRow() && getCol() == targetTile.getCol())) {
            setHasMoved(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean isCheck() {
        return false;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public boolean isCastling() {
        return castling;
    }

    public void setCastling(boolean castling) {
        this.castling = castling;
    }
}
