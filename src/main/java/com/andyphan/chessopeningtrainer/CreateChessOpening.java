package com.andyphan.chessopeningtrainer;

import com.andyphan.chess.Alliance;
import com.andyphan.chess.ChessScene;
import com.andyphan.chess.pieces.Pawn;
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
            if (playerTurn.getCurrentTurn() == Alliance.WHITE) {
                stringBuilder.append(moveCounter).append(". ");
                moveCounter++;
            }
            if (selectedPiece.getClass() == Pawn.class) {
                firstHalf.append(selectedTile.getTileName());
            }
        }
    }

    @Override
    protected void movePiece(int targetCol, int targetRow) {
        super.movePiece(targetCol, targetRow);
        if (isValidMove) {
            if (selectedPiece.getClass() == Pawn.class) {
                if (targetPiece != null) {
                    stringBuilder.append(firstHalf.toString().charAt(0)).append("x").append(targetTile.getTileName()).append(" ");
                }
                else {
                    stringBuilder.append(targetTile.getTileName()).append(" ");
                }
            }
            moves = stringBuilder.toString();
        }
        firstHalf.setLength(0);
    }
}
