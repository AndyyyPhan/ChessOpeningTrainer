package com.andyphan.chess;

public class Move {
    private Alliance currentMove = Alliance.WHITE;
    private Alliance nextMove = Alliance.BLACK;
    protected Alliance getCurrentMove() {
        return currentMove;
    }
    protected void setNextMove() {
        Alliance temp = currentMove;
        currentMove = nextMove;
        nextMove = temp;
    }
}
