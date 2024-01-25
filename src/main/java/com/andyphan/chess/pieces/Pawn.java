package com.andyphan.chess.pieces;

import com.andyphan.chess.Alliance;
import com.andyphan.chess.ChessPiece;
import com.andyphan.chess.PieceMoves;

import java.util.ArrayList;
import java.util.Collections;

public class Pawn extends ChessPiece {
    private ArrayList<PieceMoves> candidateMoves = new ArrayList<>();
    public Pawn(String imagePath) {
        super(imagePath);
    }

    @Override
    public ArrayList<PieceMoves> getCandidateMoves() {
        candidateMoves.clear();
        if (getAlliance() == Alliance.WHITE && getCol() == getTargetTile().getCol()) {
            if (getRow() == 6) Collections.addAll(candidateMoves, PieceMoves.DOUBLE_MOVE, PieceMoves.MOVE);
            else candidateMoves.add(PieceMoves.MOVE);
        }
        else if (getAlliance() == Alliance.BLACK && getCol() == getTargetTile().getCol()){
            if (getRow() == 1) Collections.addAll(candidateMoves, PieceMoves.DOUBLE_MOVE, PieceMoves.MOVE);
            else candidateMoves.add(PieceMoves.MOVE);
        }
        return candidateMoves;
    }
}
