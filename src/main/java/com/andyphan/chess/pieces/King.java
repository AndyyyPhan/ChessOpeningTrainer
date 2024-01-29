package com.andyphan.chess.pieces;

import com.andyphan.chess.ChessPiece;

public class King extends ChessPiece {
    private boolean hasMoved = false;
    public King(String imagePath) {
        super(imagePath);
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }
}
