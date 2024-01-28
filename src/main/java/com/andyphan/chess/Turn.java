package com.andyphan.chess;

public class Turn {
    private Alliance currentTurn = Alliance.WHITE;
    private Alliance nextTurn = Alliance.BLACK;
    public Alliance getCurrentTurn() {
        return currentTurn;
    }
    public void setNextTurn() {
        Alliance temp = currentTurn;
        currentTurn = nextTurn;
        nextTurn = temp;
    }
    public void setCurrentTurn(Alliance alliance) {
        currentTurn = alliance;
        if (alliance == Alliance.WHITE) nextTurn = Alliance.BLACK;
        else nextTurn = Alliance.WHITE;
    }
}
