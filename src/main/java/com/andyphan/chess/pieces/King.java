package com.andyphan.chess.pieces;

import com.andyphan.chess.ChessPiece;

public class King extends ChessPiece {
    private boolean hasMoved = false;
    private boolean castling = false;

    public King(String imagePath) {
        super(imagePath);
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
