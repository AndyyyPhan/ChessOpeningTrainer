package com.andyphan.chessopeningtrainer;

import com.andyphan.chess.Alliance;
import com.andyphan.chess.ChessScene;
import com.andyphan.chess.pieces.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import static com.andyphan.chess.ChessBoard.BOARD_SIZE;

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
        selectedPiece = chessGrid[row][col].getChessPiece();
        boolean isValidSelection = false;
        if (selectedPiece != null && playerTurn.getCurrentTurn() == selectedPiece.getAlliance()) {
            selectedTile.setRowAndCol(row, col);
            selectedTile.setChessPiece(chessGrid[row][col].getChessPiece());
            selectedRow = row;
            selectedCol = col;
            isValidSelection = true;
        }

        if (isValidSelection) {
            if (playerTurn.getCurrentTurn() == Alliance.WHITE) {
                stringBuilder.append(moveCounter).append(". ");
                moveCounter++;
            }
            if (selectedPiece.getClass() == Knight.class) firstHalf.append("N");
            else if (selectedPiece.getClass() == Bishop.class) firstHalf.append("B");
            else if (selectedPiece.getClass() == Rook.class) firstHalf.append("R");
            else if (selectedPiece.getClass() == Queen.class) firstHalf.append("Q");
            else if (selectedPiece.getClass() == King.class) firstHalf.append("K");

            firstHalf.append(selectedTile.getTileName());
        }
    }

    @Override
    protected void movePiece(int targetCol, int targetRow) {
        selectedPiece = selectedTile.getChessPiece();
        targetTile = chessGrid[targetRow][targetCol];
        targetPiece = targetTile.getChessPiece();
        boolean isValidMove = false;
        boolean isOtherKnightMove = false;
        if (selectedPiece != targetPiece && selectedPiece.isValidMove(targetTile)) {
            if (selectedPiece.getClass() == Knight.class) {
                for (int row = 0; row < BOARD_SIZE; row++) {
                    for (int col=0; col < BOARD_SIZE; col++) {
                        if ((selectedTile.getRow() != row && selectedTile.getCol() != col) &&
                                chessBoard.getChessGridTileByName(row, col).getChessPiece() != null &&
                                chessBoard.getChessGridTileByName(row, col).getChessPiece().getAlliance() == playerTurn.getCurrentTurn() &&
                                chessBoard.getChessGridTileByName(row, col).getChessPiece().getClass() == Knight.class) {
                            if (chessBoard.getChessGridTileByName(row, col).getChessPiece().isValidMove(targetTile)) {
                                isOtherKnightMove = true;
                            }
                        }
                    }
                }
            }
            if (selectedPiece.getClass() == King.class && ((King) selectedPiece).isCastling()) {
                handleCastling(targetRow, selectedPiece);
            }
            else {
                chessBoard.getChildren().remove(selectedPiece);
                chessGrid[selectedRow][selectedCol].setChessPiece(null);
                selectedTile.resetTile();
                if (targetPiece != null) chessBoard.getChildren().remove(targetPiece);
                targetTile.setChessPiece(selectedPiece);
                chessBoard.add(selectedPiece, targetCol, targetRow);
                selectedPiece.setTile(targetTile);
                playerTurn.setNextTurn();
            }
            isValidMove = true;
        }
        selectedTile.setRowAndCol(-1, -1);

        if (isValidMove) {
            if (selectedPiece.getClass() == Pawn.class) {
                if (targetPiece == null) stringBuilder.append(targetTile.getTileName()).append(" ");
                else stringBuilder.append(firstHalf.toString().charAt(0)).append("x").append(targetTile.getTileName()).append(" ");
            }
            else if (selectedPiece.getClass() == Knight.class) {
                if (targetPiece == null) {
                    {
                        if (isOtherKnightMove) stringBuilder.append(firstHalf.substring(0, 2)).append(targetTile.getTileName()).append(" ");
                        else stringBuilder.append(firstHalf.toString().charAt(0)).append(targetTile.getTileName()).append(" ");
                    }
                }
                else {
                    if (isOtherKnightMove) stringBuilder.append(firstHalf.substring(0, 2)).append("x").append(targetTile.getTileName()).append(" ");
                    else stringBuilder.append(firstHalf.toString().charAt(0)).append("x").append(targetTile.getTileName()).append(" ");
                }
            }
            else if (selectedPiece.getClass() == Bishop.class || selectedPiece.getClass() == Queen.class || selectedPiece.getClass() == King.class) {
                if (targetPiece == null) stringBuilder.append(firstHalf.toString().charAt(0)).append(targetTile.getTileName()).append(" ");
                else stringBuilder.append(firstHalf.toString().charAt(0)).append("x").append(targetTile.getTileName()).append(" ");
            }
            moves = stringBuilder.toString();
        }
        firstHalf.setLength(0);
    }
}
