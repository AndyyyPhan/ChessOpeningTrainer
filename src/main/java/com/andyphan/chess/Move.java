package com.andyphan.chess;

import com.andyphan.chessopeningtrainer.ChessOpening;

public class Move {
    private ChessOpening chessOpening;
    private String currentMove;
    private int moveNumber = 1;

    public Move(ChessOpening chessOpening) {
        this.chessOpening = chessOpening;
    }
    public String getCurrentMove() {
        return currentMove;
    }

    public void setCurrentMove(String currentMove) {
        this.currentMove = currentMove;
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

    public Tile translateToTile() {
        return new Tile(currentMove);
    }
}
