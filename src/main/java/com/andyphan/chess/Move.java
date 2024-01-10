package com.andyphan.chess;

public class Move {
    private Alliance currentMove = Alliance.WHITE;
    private Alliance nextMove = Alliance.BLACK;
    public Alliance getCurrentMove() {
        return currentMove;
    }
    public void setNextMove() {
        Alliance temp = currentMove;
        currentMove = nextMove;
        nextMove = temp;
    }
    public void setCurrentMove(Alliance alliance) {
        currentMove = alliance;
        if (alliance == Alliance.WHITE) nextMove = Alliance.BLACK;
        else nextMove = Alliance.WHITE;
    }
}
