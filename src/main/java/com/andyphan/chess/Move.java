package com.andyphan.chess;

import com.andyphan.database.ChessOpening;

public class Move {
    private ChessOpening chessOpening;

    public Move(ChessOpening chessOpening) {
        this.chessOpening = chessOpening;
    }

    public String getAllMoves() {
        return chessOpening.getMoves();
    }

    public String[] getAllMovesInList() {
        String[] allMoves = getAllMoves().split("[0-9]+\\. ");
        String[] refactoredAllMoves = new String[allMoves.length-1];
        System.arraycopy(allMoves, 1, refactoredAllMoves, 0, allMoves.length-1);
        return refactoredAllMoves;
    }
}
