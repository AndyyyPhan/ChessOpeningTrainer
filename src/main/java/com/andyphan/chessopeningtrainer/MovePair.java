package com.andyphan.chessopeningtrainer;

import javafx.beans.property.SimpleStringProperty;

public class MovePair {
    private final SimpleStringProperty whiteMove;
    private final SimpleStringProperty blackMove;

    public MovePair(String whiteMove, String blackMove) {
        this.whiteMove = new SimpleStringProperty(whiteMove);
        this.blackMove = new SimpleStringProperty(blackMove);
    }

    public String getWhiteMove() {
        return whiteMove.get();
    }

    public void setWhiteMove(String whiteMove) {
        this.whiteMove.set(whiteMove);
    }

    public String getBlackMove() {
        return blackMove.get();
    }

    public void setBlackMove(String blackMove) {
        this.blackMove.set(blackMove);
    }
}
