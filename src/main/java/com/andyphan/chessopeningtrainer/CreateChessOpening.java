package com.andyphan.chessopeningtrainer;

import com.andyphan.chess.ChessPiece;
import com.andyphan.chess.ChessScene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class CreateChessOpening extends ChessScene {
    private String moves;
    private StringBuilder stringBuilder = new StringBuilder();
    private StringBuilder firstHalf = new StringBuilder();
    private int moveCounter = 1;
    public CreateChessOpening(ChessOpening chessOpening) {
        super(chessOpening);
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();
        layout.getChildren().removeAll(flipBoardButton, showAllMovesButton);
        showAllMovesButton.setDisable(true);
        Button save = new Button("Save");
        save.setOnAction(e -> {
            moves = stringBuilder.toString();
            System.out.println(moves);
        });
        Button exit = new Button("Exit");
        layout.getChildren().addAll(new HBox(5, flipBoardButton, showAllMovesButton), new HBox(5, save, exit));
    }

    @Override
    protected void selectPiece(int col, int row) {
        super.selectPiece(col, row);
        if (isValidSelection) {
            firstHalf.append(moveCounter).append(". ").append(selectedTile.getTileName()).append(" ");
        }
    }

    @Override
    protected void movePiece(int targetCol, int targetRow) {
        super.movePiece(targetCol, targetRow);
        if (isValidMove) {
            stringBuilder.append(firstHalf).append(targetTile.getTileName()).append(" ");
            moves = stringBuilder.toString();
            moveCounter++;
        }
        firstHalf.setLength(0);
    }
}
