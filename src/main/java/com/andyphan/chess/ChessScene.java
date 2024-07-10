package com.andyphan.chess;

import com.andyphan.chess.pieces.*;
import com.andyphan.chessopeningtrainer.ChessOpening;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

import static com.andyphan.chess.ChessBoard.BOARD_SIZE;
import static com.andyphan.chess.ChessBoard.TILE_SIZE;

public class ChessScene extends Scene {
    protected final VBox layout = (VBox) getRoot();
    protected final ChessBoard chessBoard = new ChessBoard();
    protected Tile[][] chessGrid = chessBoard.getChessGrid();
    protected final Tile selectedTile = new Tile(-1, -1);
    protected Tile targetTile = new Tile(-1, -1);
    protected int selectedRow;
    protected int selectedCol;
    protected ChessPiece selectedPiece;
    protected ChessPiece targetPiece;
    protected final Turn playerTurn = new Turn();
    private final Move move;
    protected Button flipBoardButton = new Button("Flip Board");
    protected Button showAllMovesButton = new Button("Show All Moves");
    public ChessScene(ChessOpening chessOpening) {
        super(new VBox(10), BOARD_SIZE * TILE_SIZE + 50, BOARD_SIZE * TILE_SIZE + 100);
        layout.getChildren().add(chessBoard);
        move = new Move(chessOpening);
        initializePieces();
        initializeButtons();


        chessBoard.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                selectedTile.resetTile();
            }
            handleMouseClick((int) event.getX() / TILE_SIZE, (int) event.getY() / TILE_SIZE);

        });
    }

    private void handleMouseClick(int clickedCol, int clickedRow) {
        if (selectedTile.getRow() == -1 && selectedTile.getCol() == -1) {
            selectPiece(clickedCol, clickedRow);
        }
        else {
            movePiece(clickedCol, clickedRow);
        }
    }

    protected void selectPiece(int col, int row) {
        selectedPiece = chessGrid[row][col].getChessPiece();
        if (selectedPiece != null && playerTurn.getCurrentTurn() == selectedPiece.getAlliance()) {
            selectedTile.setRowAndCol(row, col);
            selectedTile.setChessPiece(chessGrid[row][col].getChessPiece());
            selectedRow = row;
            selectedCol = col;
        }
    }

    protected void movePiece(int targetCol, int targetRow) {
        selectedPiece = selectedTile.getChessPiece();
        targetTile = chessGrid[targetRow][targetCol];
        targetPiece = targetTile.getChessPiece();
        if (selectedPiece != targetPiece && selectedPiece.isValidMove(targetTile)) {
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
        }
        selectedTile.setRowAndCol(-1, -1);
    }

    private void initializePieces() {
        for (Tile[] tileArray : chessGrid) {
            for (Tile tile : tileArray) {
                if (tile.getTileName().contains("2")) addPiece("whitePawn.png", tile);
                else if (tile.getTileName().contains("7")) addPiece("blackPawn.png", tile);
            }
        }
        addPiece("whiteKnight.png", chessBoard.getChessGridTile(7, 1));
        addPiece("whiteKnight.png", chessBoard.getChessGridTile(7, 6));
        addPiece("blackKnight.png", chessBoard.getChessGridTile(0, 1));
        addPiece("blackKnight.png", chessBoard.getChessGridTile(0, 6));

        addPiece("whiteBishop.png", chessBoard.getChessGridTile(7, 2));
        addPiece("whiteBishop.png", chessBoard.getChessGridTile(7, 5));
        addPiece("blackBishop.png", chessBoard.getChessGridTile(0, 2));
        addPiece("blackBishop.png", chessBoard.getChessGridTile(0, 5));

        addPiece("whiteRook.png", chessBoard.getChessGridTile(7, 0));
        addPiece("whiteRook.png", chessBoard.getChessGridTile(7, 7));
        addPiece("blackRook.png", chessBoard.getChessGridTile(0, 0));
        addPiece("blackRook.png", chessBoard.getChessGridTile(0, 7));

        addPiece("whiteQueen.png", chessBoard.getChessGridTile(7, 3));
        addPiece("blackQueen.png", chessBoard.getChessGridTile(0, 3));

        addPiece("whiteKing.png", chessBoard.getChessGridTile(7, 4));
        addPiece("blackKing.png", chessBoard.getChessGridTile(0, 4));
    }

    protected void initializeButtons() {
        PauseTransition buttonPause = new PauseTransition(Duration.seconds(0.5));
        flipBoardButton.setOnAction(event -> {
            if (!buttonPause.getStatus().equals(PauseTransition.Status.RUNNING)) {
                flipBoard();
                buttonPause.playFromStart();
            }
        });
        showAllMovesButton.setOnAction(event -> displayAllMoves());
        layout.getChildren().addAll(flipBoardButton, showAllMovesButton);
    }

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
        ((King) selectedPiece).setHasCastled(true);
        ((King) selectedPiece).setCastling(false);
        ((Rook) targetPiece).setHasMoved(true);
        playerTurn.setNextTurn();
    }

    private void addPiece(String imageName, Tile tile) {
        if (imageName.contains("Pawn")) chessBoard.getChessGridTile(tile.getRow(), tile.getCol()).setChessPiece(new Pawn(chessBoard, imageName));
        else if (imageName.contains("Knight")) chessBoard.getChessGridTile(tile.getRow(), tile.getCol()).setChessPiece(new Knight(chessBoard, imageName));
        else if (imageName.contains("Bishop")) chessBoard.getChessGridTile(tile.getRow(), tile.getCol()).setChessPiece(new Bishop(chessBoard, imageName));
        else if (imageName.contains("Rook")) chessBoard.getChessGridTile(tile.getRow(), tile.getCol()).setChessPiece(new Rook(chessBoard, imageName));
        else if (imageName.contains("Queen")) chessBoard.getChessGridTile(tile.getRow(), tile.getCol()).setChessPiece(new Queen(chessBoard, imageName));
        else if (imageName.contains("King")) chessBoard.getChessGridTile(tile.getRow(), tile.getCol()).setChessPiece(new King(chessBoard, imageName));
        chessBoard.add(chessBoard.getChessGridTile(tile.getRow(), tile.getCol()).getChessPiece(), tile.getCol(), tile.getRow());
        chessBoard.getChessGridTile(tile.getRow(), tile.getCol()).getChessPiece().setTile(tile);
        chessGrid[tile.getRow()][tile.getCol()].getChessPiece().setTile(tile);
    }

    private void setupPieces() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (chessGrid[row][col].getChessPiece() != null) {
                    chessBoard.add(chessGrid[row][col].getChessPiece(), col, row);
                }
            }
        }
    }

    private void flipBoard() {
        Tile[][] chessGridCopy = new Tile[BOARD_SIZE][BOARD_SIZE];
        int rows = chessGrid.length;
        int cols = chessGrid[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                chessGridCopy[row][col] = new Tile(row,col);
                chessGridCopy[row][col].setEqualToTile(chessGrid[row][col]);
            }
        }
        chessBoard.getChildren().clear();
        chessBoard.setFlipped(!chessBoard.getFlipped());
        chessBoard.drawBoard();
        chessBoard.setFlippedChessGrid(chessGridCopy);
        setupPieces();
        selectedTile.resetTile();
    }

    protected void resetBoard() {
        boolean wasFlipped = chessBoard.getFlipped();
        if (chessBoard.getFlipped()) flipBoard();
        Tile[][] chessGridCopy = new Tile[BOARD_SIZE][BOARD_SIZE];
        int rows = chessGrid.length;
        int cols = chessGrid[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                chessGridCopy[row][col] = new Tile(row,col);
            }
        }
        chessBoard.getChildren().clear();
        chessBoard.setChessGrid(chessGridCopy);
        chessGrid = chessGridCopy;
        chessBoard.drawBoard();
        initializePieces();
        selectedTile.resetTile();
        if (wasFlipped) flipBoard();
    }

    protected void displayAllMoves() {
        resetBoard();
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        Duration moveDuration = Duration.seconds(1);
        ArrayList<String> singleMove = new ArrayList<>();

        for (int i = 0; i < move.getAllMovesInList().length; i++) {
            String movePair = move.getAllMovesInList()[i].trim();
            String[] individualMove = movePair.split(" ");

            singleMove.addAll(Arrays.asList(individualMove));
        }
        for (int i=0; i < singleMove.size(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(
                    moveDuration.multiply(i),
                    event -> playMove(singleMove.get(finalI))
            );
            timeline.getKeyFrames().add(keyFrame);
        }
        showAllMovesButton.setDisable(true);
        timeline.setDelay(Duration.seconds(1));

        timeline.setOnFinished(event -> showAllMovesButton.setDisable(false));

        timeline.play();
    }

    protected void playMove(String move) {
        if (move.contains("+")) move = move.substring(0, move.length()-1);
        Tile moveTile = new Tile(move);
        moveTile = chessBoard.getChessGridTileByName(moveTile.getRow(), moveTile.getCol());
        Tile movingTile = new Tile(-1, -1);
        if (move.length() == 2) {
            for (int row=0; row<BOARD_SIZE; row++) {
                if (chessBoard.getChessGridTileByName(row, moveTile.getCol()).getChessPiece() != null) {
                    if (chessBoard.getChessGridTileByName(row, moveTile.getCol()).getChessPiece().getClass() == Pawn.class &&
                            chessBoard.getChessGridTileByName(row, moveTile.getCol()).getChessPiece().getAlliance() == playerTurn.getCurrentTurn()
                            && chessBoard.getChessGridTileByName(row, moveTile.getCol()).getChessPiece().isValidMove(moveTile))
                        movingTile = chessBoard.getChessGridTileByName(row, moveTile.getCol());
                }
            }
        }
        else if (move.contains("N") && move.length() == 3) {
            for (int row=0; row<BOARD_SIZE; row++) {
                for (int col=0; col<BOARD_SIZE; col++) {
                    if (chessBoard.getChessGridTileByName(row, col).getChessPiece() != null) {
                        if (chessBoard.getChessGridTileByName(row, col).getChessPiece().getClass() == Knight.class &&
                        chessBoard.getChessGridTileByName(row, col).getChessPiece().getAlliance() == playerTurn.getCurrentTurn()
                        && chessBoard.getChessGridTileByName(row, col).getChessPiece().isValidMove(moveTile))
                            movingTile = chessBoard.getChessGridTileByName(row, col);
                    }
                }
            }
        }
        else if (move.contains("B") && move.length() == 3) {
            for (int row=0; row<BOARD_SIZE; row++) {
                for (int col=0; col<BOARD_SIZE; col++) {
                    if (chessBoard.getChessGridTileByName(row, col).getChessPiece() != null) {
                        if (chessBoard.getChessGridTileByName(row, col).getChessPiece().getClass() == Bishop.class &&
                                chessBoard.getChessGridTileByName(row, col).getChessPiece().getAlliance() == playerTurn.getCurrentTurn()
                                && chessBoard.getChessGridTileByName(row, col).getChessPiece().isValidMove(moveTile))
                            movingTile = chessBoard.getChessGridTileByName(row, col);
                    }
                }
            }
        }
        else if (move.contains("R") && move.length() == 3) {
            for (int row=0; row<BOARD_SIZE; row++) {
                for (int col=0; col<BOARD_SIZE; col++) {
                    if (chessBoard.getChessGridTileByName(row, col).getChessPiece() != null) {
                        if (chessBoard.getChessGridTileByName(row, col).getChessPiece().getClass() == Rook.class &&
                                chessBoard.getChessGridTileByName(row, col).getChessPiece().getAlliance() == playerTurn.getCurrentTurn()
                                && chessBoard.getChessGridTileByName(row, col).getChessPiece().isValidMove(moveTile))
                            movingTile = chessBoard.getChessGridTileByName(row, col);
                    }
                }
            }
        }
        else if (move.contains("Q") && move.length() == 3) {
            for (int row=0; row<BOARD_SIZE; row++) {
                for (int col=0; col<BOARD_SIZE; col++) {
                    if (chessBoard.getChessGridTileByName(row, col).getChessPiece() != null) {
                        if (chessBoard.getChessGridTileByName(row, col).getChessPiece().getClass() == Queen.class &&
                                chessBoard.getChessGridTileByName(row, col).getChessPiece().getAlliance() == playerTurn.getCurrentTurn()
                                && chessBoard.getChessGridTileByName(row, col).getChessPiece().isValidMove(moveTile))
                            movingTile = chessBoard.getChessGridTileByName(row, col);
                    }
                }
            }
        }
        else if (move.contains("x")) {
            String takingOn = move.substring(move.length()-2);
            Tile takingOnTile = new Tile(takingOn);
            takingOnTile = chessBoard.getChessGridTileByName(takingOnTile.getRow(), takingOnTile.getCol());
            if (Character.isLowerCase(move.charAt(0))) {
                for (int row = 0; row < BOARD_SIZE; row++) {
                    if (chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(0))).getChessPiece() != null &&
                            chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(0))).getChessPiece().getAlliance() == playerTurn.getCurrentTurn()) {
                        if (chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(0))).getChessPiece().isValidMove(takingOnTile)) {
                            movingTile = chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(0)));
                            moveTile = takingOnTile;
                        }
                    }
                }
            }
            else {
                if (move.length() == 4) {
                    if (move.charAt(0) == 'N') {
                        for (int row = 0; row < BOARD_SIZE; row++) {
                            for (int col = 0; col < BOARD_SIZE; col++) {
                                if (chessBoard.getChessGridTileByName(row, col).getChessPiece() != null &&
                                        chessBoard.getChessGridTileByName(row, col).getChessPiece().getAlliance() == playerTurn.getCurrentTurn() &&
                                chessBoard.getChessGridTileByName(row, col).getChessPiece().getClass() == Knight.class) {
                                    if (chessBoard.getChessGridTileByName(row, col).getChessPiece().isValidMove(takingOnTile)) {
                                        movingTile = chessBoard.getChessGridTileByName(row, col);
                                        moveTile = takingOnTile;
                                    }
                                }
                            }
                        }
                    }
                    else if (move.charAt(0) == 'B') {
                        for (int row = 0; row < BOARD_SIZE; row++) {
                            for (int col = 0; col < BOARD_SIZE; col++) {
                                if (chessBoard.getChessGridTileByName(row, col).getChessPiece() != null &&
                                        chessBoard.getChessGridTileByName(row, col).getChessPiece().getAlliance() == playerTurn.getCurrentTurn() &&
                                        chessBoard.getChessGridTileByName(row, col).getChessPiece().getClass() == Bishop.class) {
                                    if (chessBoard.getChessGridTileByName(row, col).getChessPiece().isValidMove(takingOnTile)) {
                                        movingTile = chessBoard.getChessGridTileByName(row, col);
                                        moveTile = takingOnTile;
                                    }
                                }
                            }
                        }
                    }
                    else if (move.charAt(0) == 'R') {
                        for (int row = 0; row < BOARD_SIZE; row++) {
                            for (int col = 0; col < BOARD_SIZE; col++) {
                                if (chessBoard.getChessGridTileByName(row, col).getChessPiece() != null &&
                                        chessBoard.getChessGridTileByName(row, col).getChessPiece().getAlliance() == playerTurn.getCurrentTurn() &&
                                        chessBoard.getChessGridTileByName(row, col).getChessPiece().getClass() == Rook.class) {
                                    if (chessBoard.getChessGridTileByName(row, col).getChessPiece().isValidMove(takingOnTile)) {
                                        movingTile = chessBoard.getChessGridTileByName(row, col);
                                        moveTile = takingOnTile;
                                    }
                                }
                            }
                        }
                    }
                    else if (move.charAt(0) == 'Q') {
                        for (int row = 0; row < BOARD_SIZE; row++) {
                            for (int col = 0; col < BOARD_SIZE; col++) {
                                if (chessBoard.getChessGridTileByName(row, col).getChessPiece() != null &&
                                        chessBoard.getChessGridTileByName(row, col).getChessPiece().getAlliance() == playerTurn.getCurrentTurn() &&
                                        chessBoard.getChessGridTileByName(row, col).getChessPiece().getClass() == Queen.class) {
                                    if (chessBoard.getChessGridTileByName(row, col).getChessPiece().isValidMove(takingOnTile)) {
                                        movingTile = chessBoard.getChessGridTileByName(row, col);
                                        moveTile = takingOnTile;
                                    }
                                }
                            }
                        }
                    }
                }
                else if (move.length() == 5) {
                    if (move.charAt(0) == 'N') {
                        for (int row = 0; row < BOARD_SIZE; row++) {
                            if (chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(1))).getChessPiece() != null &&
                                    chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(1))).getChessPiece().getAlliance() == playerTurn.getCurrentTurn() &&
                                    chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(1))).getChessPiece().getClass() == Knight.class) {
                                if (chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(1))).getChessPiece().isValidMove(takingOnTile)) {
                                    movingTile = chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(1)));
                                    moveTile = takingOnTile;
                                }
                            }
                        }
                    }
                }
            }
        }
        else if (move.length() == 4 && move.charAt(0) == 'N') {
            String takingOn = move.substring(move.length()-2);
            Tile takingOnTile = new Tile(takingOn);
            takingOnTile = chessBoard.getChessGridTileByName(takingOnTile.getRow(), takingOnTile.getCol());
            for (int row = 0; row < BOARD_SIZE; row++) {
                if (chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(1))).getChessPiece() != null &&
                        chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(1))).getChessPiece().getAlliance() == playerTurn.getCurrentTurn() &&
                        chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(1))).getChessPiece().getClass() == Knight.class) {
                    if (chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(1))).getChessPiece().isValidMove(takingOnTile)) {
                        movingTile = chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(1)));
                        moveTile = takingOnTile;
                    }
                }
            }
        }
        else if (move.length() == 4 && move.charAt(0) == 'R') {
            String takingOn = move.substring(move.length()-2);
            Tile takingOnTile = new Tile(takingOn);
            takingOnTile = chessBoard.getChessGridTileByName(takingOnTile.getRow(), takingOnTile.getCol());
            for (int row = 0; row < BOARD_SIZE; row++) {
                if (chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(1))).getChessPiece() != null &&
                        chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(1))).getChessPiece().getAlliance() == playerTurn.getCurrentTurn() &&
                        chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(1))).getChessPiece().getClass() == Rook.class) {
                    if (chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(1))).getChessPiece().isValidMove(takingOnTile)) {
                        movingTile = chessBoard.getChessGridTileByName(row, moveTile.translateColToNumber(move.charAt(1)));
                        moveTile = takingOnTile;
                    }
                }
            }
        }
        else if (move.equals("O-O")) {
            if (playerTurn.getCurrentTurn() == Alliance.WHITE) {
                if (chessBoard.getChessGridTileByName(7, 4).getChessPiece().isValidMove(chessBoard.getChessGridTileByName(7, 7))) {
                    movingTile = chessBoard.getChessGridTileByName(7, 4);
                    moveTile = chessBoard.getChessGridTileByName(7, 7);
                }
            }
            else if (playerTurn.getCurrentTurn() == Alliance.BLACK){
                if (chessBoard.getChessGridTileByName(0, 4).getChessPiece().isValidMove(chessBoard.getChessGridTileByName(0, 7))) {
                    movingTile = chessBoard.getChessGridTileByName(0, 4);
                    moveTile = chessBoard.getChessGridTileByName(0, 7);
                }
            }
        }
        else if (move.equals("O-O-O")) {
            if (playerTurn.getCurrentTurn() == Alliance.WHITE) {
                if (chessBoard.getChessGridTileByName(7, 4).getChessPiece().isValidMove(chessBoard.getChessGridTileByName(7, 0))) {
                    movingTile = chessBoard.getChessGridTileByName(7, 4);
                    moveTile = chessBoard.getChessGridTileByName(7, 0);
                }
            }
            else if (playerTurn.getCurrentTurn() == Alliance.BLACK){
                if (chessBoard.getChessGridTileByName(0, 4).getChessPiece().isValidMove(chessBoard.getChessGridTileByName(0, 0))) {
                    movingTile = chessBoard.getChessGridTileByName(0, 4);
                    moveTile = chessBoard.getChessGridTileByName(0, 0);
                }
            }
        }
        if (!chessBoard.getFlipped()) {
            selectPiece(movingTile.getCol(), movingTile.getRow());
            movePiece(moveTile.getCol(), moveTile.getRow());
        }
        else {
            selectPiece(7 - movingTile.getCol(), 7 - movingTile.getRow());
            movePiece(7 - moveTile.getCol(), 7 - moveTile.getRow());
        }
    }
}
