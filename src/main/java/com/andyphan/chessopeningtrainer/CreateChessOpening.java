package com.andyphan.chessopeningtrainer;

import com.andyphan.chess.ChessScene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class CreateChessOpening extends ChessScene {
    public CreateChessOpening(ChessOpening chessOpening) {
        super(chessOpening);
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();
        layout.getChildren().removeAll(flipBoardButton, showAllMovesButton);
        showAllMovesButton.setDisable(true);
        Button save = new Button("Save");
        Button exit = new Button("Exit");
        layout.getChildren().addAll(new HBox(5, flipBoardButton, showAllMovesButton), new HBox(5, save, exit));
    }
}
