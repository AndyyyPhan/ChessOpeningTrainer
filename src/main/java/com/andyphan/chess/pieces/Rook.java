package com.andyphan.chess.pieces;

import com.andyphan.chess.ChessBoard;
import com.andyphan.chess.ChessPiece;
import com.andyphan.chess.Tile;

import static com.andyphan.chess.ChessBoard.BOARD_SIZE;

public class Rook extends ChessPiece {
    private boolean hasMoved = false;
    public Rook(ChessBoard chessBoard, String imagePath) {
        super(chessBoard, imagePath);
    }

    @Override
    public boolean isValidMove(Tile targetTile) {
        if (targetTile.getChessPiece() != null) {
            if (targetTile.getChessPiece().getClass() == King.class ||
                    (targetTile.getChessPiece().getAlliance() == getAlliance())) return false;
        }
        if (getRow() != targetTile.getRow() && getCol() != targetTile.getCol()) return false;
        Tile currentTile = getTile();
        if (targetTile.getCol() > getCol()) {
            while (currentTile.getCol() < targetTile.getCol() - 1 && currentTile.getCol() < BOARD_SIZE) {
                currentTile = chessBoard.getChessGridTileByName(currentTile.getRow(), currentTile.getCol()+1);
                if (currentTile.getChessPiece() != null) return false;
            }
            if (getClass() == Rook.class) setHasMoved(true);
            return true;
        }
        else if (targetTile.getCol() < getCol()) {
            while (currentTile.getCol() > targetTile.getCol() + 1 && currentTile.getCol() > 0) {
                currentTile = chessBoard.getChessGridTileByName(currentTile.getRow(), currentTile.getCol()-1);
                if (currentTile.getChessPiece() != null) return false;
            }
            if (getClass() == Rook.class) setHasMoved(true);
            return true;
        }
        else if (targetTile.getRow() > getRow()) {
            while (currentTile.getRow() < targetTile.getRow() - 1 && currentTile.getRow() < BOARD_SIZE) {
                currentTile = chessBoard.getChessGridTileByName(currentTile.getRow()+1, currentTile.getCol());
                if (currentTile.getChessPiece() != null) return false;
            }
            if (getClass() == Rook.class) setHasMoved(true);
            return true;
        }
        else if (targetTile.getRow() < getRow()) {
            while (currentTile.getRow() > targetTile.getRow() + 1 && currentTile.getRow() > 0) {
                currentTile = chessBoard.getChessGridTileByName(currentTile.getRow()-1, currentTile.getCol());
                if (currentTile.getChessPiece() != null) return false;
            }
            if (getClass() == Rook.class) setHasMoved(true);
            return true;
        }
        return false;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }
}
