package com.andyphan.chessopeningtrainer;

import com.andyphan.chess.Alliance;
import com.andyphan.chess.ChessPiece;
import com.andyphan.chess.ChessScene;
import com.andyphan.chess.pieces.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

import static com.andyphan.chess.ChessBoard.BOARD_SIZE;

public class CreateChessOpening extends ChessScene {
    private String moves;
    private StringBuilder stringBuilder = new StringBuilder();
    private StringBuilder firstHalf = new StringBuilder();
    private int moveCounter = 1;
    private boolean isValidSelection = false;
    private boolean showingAllMoves = false;

    public CreateChessOpening(ChessOpening chessOpening) {
        super(chessOpening);
    }

    @Override
    protected void initializeButtons() {
        super.initializeButtons();
        layout.getChildren().removeAll(flipBoardButton, showAllMovesButton);
        Button save = new Button("Save");
        save.setOnAction(e -> {
            moves = stringBuilder.toString().trim();

            Stage saveStage = new Stage();
            saveStage.initModality(Modality.WINDOW_MODAL);
            saveStage.initOwner(SceneManager.getPrimaryStage());
            saveStage.setScene(new SaveChessOpening(this));
            saveStage.show();
        });
        Button reset = new Button("Reset");
        reset.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reset Moves");
            alert.setHeaderText("Reset Moves");
            alert.setContentText("You have reset all of your moves.");
            alert.showAndWait();
            resetBoard();
            stringBuilder.setLength(0);
            moves = stringBuilder.toString().trim();
            moveCounter = 1;
        });
        Button exit = new Button("Exit");
        exit.setOnAction(e -> {
            AllOpeningsMenu.setIsChessSceneOpen(false);
            AllOpeningsMenu.setActiveChessScene(null);
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
        });
        layout.getChildren().addAll(new HBox(5, flipBoardButton, showAllMovesButton), new HBox(5, save, reset, exit));
    }

    @Override
    protected void displayAllMoves() {
        if (this.moves == null || this.moves.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Moves");
            alert.setHeaderText("There are no moves in play.");
            alert.setContentText("Please make moves before \"Showing all moves\"");
            alert.showAndWait();
        }
        else {
            resetBoard();
            Timeline timeline = new Timeline();
            timeline.setCycleCount(1);
            Duration moveDuration = Duration.seconds(1);
            ArrayList<String> singleMove = new ArrayList<>();

            for (int i = 0; i < getAllMovesInList().length; i++) {
                String movePair = getAllMovesInList()[i].trim();
                String[] individualMove = movePair.split(" ");

                singleMove.addAll(Arrays.asList(individualMove));
            }
            for (int i = 0; i < singleMove.size(); i++) {
                int finalI = i;
                KeyFrame keyFrame = new KeyFrame(
                        moveDuration.multiply(i),
                        event -> playMove(singleMove.get(finalI))
                );
                timeline.getKeyFrames().add(keyFrame);
            }
            showAllMovesButton.setDisable(true);
            showingAllMoves = true;
            timeline.setDelay(Duration.seconds(1));

            timeline.setOnFinished(event -> {
                showAllMovesButton.setDisable(false);
                showingAllMoves = false;
            });

            timeline.play();
        }
    }

    private String[] getAllMovesInList() {
        String[] allMoves = moves.split("[0-9]+\\. ");
        String[] refactoredAllMoves = new String[allMoves.length-1];
        System.arraycopy(allMoves, 1, refactoredAllMoves, 0, allMoves.length-1);
        return refactoredAllMoves;
    }

    @Override
    protected void selectPiece(int col, int row) {
        selectedPiece = chessGrid[row][col].getChessPiece();
        if (selectedPiece != null && playerTurn.getCurrentTurn() == selectedPiece.getAlliance()) {
            selectedTile.setRowAndCol(row, col);
            selectedTile.setChessPiece(chessGrid[row][col].getChessPiece());
            selectedRow = row;
            selectedCol = col;
            isValidSelection = true;
        }
    }

    @Override
    protected void movePiece(int targetCol, int targetRow) {
        selectedPiece = selectedTile.getChessPiece();
        targetTile = chessGrid[targetRow][targetCol];
        targetPiece = targetTile.getChessPiece();
        boolean isValidMove = false;
        boolean isOtherKnightMove = false;
        boolean isOtherRookMove = false;
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
            else if (selectedPiece.getClass() == Rook.class) {
                for (int row = 0; row < BOARD_SIZE; row++) {
                    for (int col=0; col < BOARD_SIZE; col++) {
                        if ((selectedTile.getRow() != row && selectedTile.getCol() != col) &&
                                chessBoard.getChessGridTileByName(row, col).getChessPiece() != null &&
                                chessBoard.getChessGridTileByName(row, col).getChessPiece().getAlliance() == playerTurn.getCurrentTurn() &&
                                chessBoard.getChessGridTileByName(row, col).getChessPiece().getClass() == Rook.class) {
                            if (chessBoard.getChessGridTileByName(row, col).getChessPiece().isValidMove(targetTile)) {
                                isOtherRookMove = true;
                            }
                        }
                    }
                }
            }

            if (isValidSelection && !showingAllMoves) {
                if (playerTurn.getCurrentTurn() == Alliance.WHITE) {
                    stringBuilder.append(moveCounter).append(". ");
                    moveCounter++;
                }
                if (selectedPiece.getClass() == Knight.class) firstHalf.append("N");
                else if (selectedPiece.getClass() == Bishop.class) firstHalf.append("B");
                else if (selectedPiece.getClass() == Rook.class) firstHalf.append("R");
                else if (selectedPiece.getClass() == Queen.class) firstHalf.append("Q");
                else if (selectedPiece.getClass() == King.class && !(((King) selectedPiece).isCastling())) firstHalf.append("K");
                firstHalf.append(selectedTile.getTileName());
            }

            if (selectedPiece.getClass() == King.class && ((King) selectedPiece).isCastling() && !((King) selectedPiece).hasCastled()) {
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

        if (isValidMove && !showingAllMoves) {
            System.out.println(selectedPiece.getClass());
            if (selectedPiece.getClass() == King.class) System.out.println(((King) selectedPiece).isCastling());
            if (selectedPiece.getClass() == Pawn.class) {
                if (targetPiece == null) stringBuilder.append(targetTile.getTileName()).append(" ");
                else stringBuilder.append(firstHalf.toString().charAt(0)).append("x").append(targetTile.getTileName()).append(" ");
            }
            else if (selectedPiece.getClass() == Knight.class) {
                isOtherMove(isOtherKnightMove);
            }
            else if (selectedPiece.getClass() == Bishop.class || selectedPiece.getClass() == Queen.class || (selectedPiece.getClass() == King.class && !((King) selectedPiece).isCastling())) {
                if (targetPiece == null) stringBuilder.append(firstHalf.toString().charAt(0)).append(targetTile.getTileName()).append(" ");
                else stringBuilder.append(firstHalf.toString().charAt(0)).append("x").append(targetTile.getTileName()).append(" ");
            }
            else if (selectedPiece.getClass() == Rook.class) {
                isOtherMove(isOtherRookMove);
            }
            else if (selectedPiece.getClass() == King.class && ((King) selectedPiece).isCastling()) {
                if (((King) selectedPiece).isShortCastled()) stringBuilder.append("O-O").append(" ");
                else stringBuilder.append("O-O-O").append(" ");
                ((King) selectedPiece).setHasCastled(true);
                ((King) selectedPiece).setCastling(false);
            }
            if (selectedPiece.isCheck()) {
                stringBuilder.setLength(stringBuilder.length() - 1);
                stringBuilder.append("+").append(" ");
            }
            moves = stringBuilder.toString().trim();
        }
        System.out.println(stringBuilder.toString());
        System.out.println(firstHalf);
        firstHalf.setLength(0);
    }

    private void isOtherMove(boolean chessPiece) {
        if (targetPiece == null) {
            {
                if (chessPiece) stringBuilder.append(firstHalf.substring(0, 2)).append(targetTile.getTileName()).append(" ");
                else stringBuilder.append(firstHalf.toString().charAt(0)).append(targetTile.getTileName()).append(" ");
            }
        }
        else {
            if (chessPiece) stringBuilder.append(firstHalf.substring(0, 2)).append("x").append(targetTile.getTileName()).append(" ");
            else stringBuilder.append(firstHalf.toString().charAt(0)).append("x").append(targetTile.getTileName()).append(" ");
        }
    }

    public String getMoves() {
        return moves;
    }

    @Override
    protected void handleCastling(int targetRow, ChessPiece selectedPiece) {
        targetPiece = targetTile.getChessPiece();
        chessBoard.getChildren().remove(selectedPiece);
        chessBoard.getChildren().remove(targetPiece);
        chessGrid[selectedRow][selectedCol].setChessPiece(null);
        targetTile.setChessPiece(null);
        if (selectedPiece.getAlliance() == Alliance.WHITE) {
            if (targetTile.getTileName().equals("a1")) {
                if (!chessBoard.getFlipped()) {
                    chessGrid[7][2].setChessPiece(selectedPiece);
                    chessGrid[7][3].setChessPiece(targetPiece);
                    chessBoard.add(selectedPiece, 2, targetRow);
                    chessBoard.add(targetPiece, 3, targetRow);
                } else {
                    chessGrid[0][5].setChessPiece(selectedPiece);
                    chessGrid[0][4].setChessPiece(targetPiece);
                    chessBoard.add(selectedPiece, 5, targetRow);
                    chessBoard.add(targetPiece, 4, targetRow);
                }
                ((King) selectedPiece).setLongCastled(true);
                selectedPiece.setTile(chessBoard.getChessGridTileByName(7, 2));
                targetPiece.setTile(chessBoard.getChessGridTileByName(7, 3));
            } else if (targetTile.getTileName().equals("h1")) {
                if (!chessBoard.getFlipped()) {
                    chessGrid[7][6].setChessPiece(selectedPiece);
                    chessGrid[7][5].setChessPiece(targetPiece);
                    chessBoard.add(selectedPiece, 6, targetRow);
                    chessBoard.add(targetPiece, 5, targetRow);
                } else {
                    chessGrid[0][1].setChessPiece(selectedPiece);
                    chessGrid[0][2].setChessPiece(targetPiece);
                    chessBoard.add(selectedPiece, 1, targetRow);
                    chessBoard.add(targetPiece, 2, targetRow);
                }
                ((King) selectedPiece).setShortCastled(true);
                selectedPiece.setTile(chessBoard.getChessGridTileByName(7, 6));
                targetPiece.setTile(chessBoard.getChessGridTileByName(7, 5));
            }
        }
        else {
            if (targetTile.getTileName().equals("a8")) {
                if (!chessBoard.getFlipped()) {
                    chessGrid[0][2].setChessPiece(selectedPiece);
                    chessGrid[0][3].setChessPiece(targetPiece);
                    chessBoard.add(selectedPiece, 2, targetRow);
                    chessBoard.add(targetPiece, 3, targetRow);
                } else {
                    chessGrid[7][5].setChessPiece(selectedPiece);
                    chessGrid[7][4].setChessPiece(targetPiece);
                    chessBoard.add(selectedPiece, 5, targetRow);
                    chessBoard.add(targetPiece, 4, targetRow);
                }
                ((King) selectedPiece).setLongCastled(true);
                selectedPiece.setTile(chessBoard.getChessGridTileByName(0, 2));
                targetPiece.setTile(chessBoard.getChessGridTileByName(0, 3));
            } else if (targetTile.getTileName().equals("h8")) {
                if (!chessBoard.getFlipped()) {
                    chessGrid[0][6].setChessPiece(selectedPiece);
                    chessGrid[0][5].setChessPiece(targetPiece);
                    chessBoard.add(selectedPiece, 6, targetRow);
                    chessBoard.add(targetPiece, 5, targetRow);
                } else {
                    chessGrid[7][1].setChessPiece(selectedPiece);
                    chessGrid[7][2].setChessPiece(targetPiece);
                    chessBoard.add(selectedPiece, 1, targetRow);
                    chessBoard.add(targetPiece, 2, targetRow);
                }
                ((King) selectedPiece).setShortCastled(true);
                selectedPiece.setTile(chessBoard.getChessGridTileByName(0, 6));
                targetPiece.setTile(chessBoard.getChessGridTileByName(0, 5));
            }
        }
        ((King) selectedPiece).setHasMoved(true);
        ((Rook) targetPiece).setHasMoved(true);
        playerTurn.setNextTurn();
    }
}
