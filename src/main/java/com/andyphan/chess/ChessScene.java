package com.andyphan.chess;

import com.andyphan.chess.pieces.*;
import com.andyphan.chessopeningtrainer.ChessOpening;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import static com.andyphan.chess.ChessBoard.BOARD_SIZE;
import static com.andyphan.chess.ChessBoard.TILE_SIZE;

public class ChessScene extends Scene {
    private final ChessBoard chessBoard = new ChessBoard();
    protected ChessPiece[][] pieceViews = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
    private int selectedPieceRow = -1;
    private int selectedPieceColumn = -1;
    public ChessScene(ChessOpening chessOpening) {
        super(new VBox(10), BOARD_SIZE*TILE_SIZE+50, BOARD_SIZE*TILE_SIZE+100);
        initializePieces();

        chessBoard.setOnMouseClicked(event -> handleMouseClick((int) event.getX()/ TILE_SIZE, (int) event.getY() / TILE_SIZE));

        Button flipBoardButton = new Button("Flip Board");
        flipBoardButton.setOnAction(event -> flipBoard());

        VBox layout = (VBox) getRoot();
        layout.getChildren().addAll(chessBoard, flipBoardButton);
    }

    private void handleMouseClick(int clickedCol, int clickedRow) {
        if (selectedPieceColumn == -1 && selectedPieceRow == -1) {
            selectPiece(clickedCol, clickedRow);
        }
        else {
            movePiece(clickedCol, clickedRow);
        }
    }

    private void selectPiece(int col, int row) {
        ChessPiece selectedPiece = pieceViews[row][col];
        if (selectedPiece != null) {
            selectedPieceRow = row;
            selectedPieceColumn = col;
        }
    }

    private void movePiece(int targetCol, int targetRow) {
        ChessPiece selectedPiece = pieceViews[selectedPieceRow][selectedPieceColumn];
        chessBoard.getChildren().remove(selectedPiece);
        pieceViews[selectedPieceRow][selectedPieceColumn] = null;
        pieceViews[targetRow][targetCol] = selectedPiece;
        chessBoard.add(selectedPiece, targetCol, targetRow);
        System.out.println(getChessMove(selectedPiece, targetCol, targetRow));

        selectedPieceRow = -1;
        selectedPieceColumn = -1;
    }

    private void addPiece(String imageName, int col, int row) {
        if (imageName.contains("Pawn")) pieceViews[row][col] = new Pawn(imageName);
        else if (imageName.contains("Knight")) pieceViews[row][col] = new Knight(imageName);
        else if (imageName.contains("Bishop")) pieceViews[row][col] = new Bishop(imageName);
        else if (imageName.contains("Rook")) pieceViews[row][col] = new Rook(imageName);
        else if (imageName.contains("Queen")) pieceViews[row][col] = new Queen(imageName);
        else if (imageName.contains("King")) pieceViews[row][col] = new King(imageName);
        chessBoard.add(pieceViews[row][col], col, row);
    }

    private void initializePieces() {
        if (!chessBoard.getFlipped()) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                addPiece("whitePawn.png", col, 6);
                addPiece("blackPawn.png", col, 1);
            }
            addPiece("whiteKnight.png", 1, 7);
            addPiece("whiteKnight.png", 6, 7);
            addPiece("blackKnight.png", 1, 0);
            addPiece("blackKnight.png", 6, 0);

            addPiece("whiteBishop.png", 2, 7);
            addPiece("whiteBishop.png", 5, 7);
            addPiece("blackBishop.png", 2, 0);
            addPiece("blackBishop.png", 5, 0);

            addPiece("whiteRook.png", 0, 7);
            addPiece("whiteRook.png", 7, 7);
            addPiece("blackRook.png", 0, 0);
            addPiece("blackRook.png", 7, 0);

            addPiece("whiteQueen.png", 3, 7);
            addPiece("blackQueen.png", 3, 0);

            addPiece("whiteKing.png", 4, 7);
            addPiece("blackKing.png", 4, 0);
        }
        else {
            for (int col = 0; col < BOARD_SIZE; col++) {
                addPiece("whitePawn.png", col, 1);
                addPiece("blackPawn.png", col, 6);
            }
            addPiece("whiteKnight.png", 1, 0);
            addPiece("whiteKnight.png", 6, 0);
            addPiece("blackKnight.png", 1, 7);
            addPiece("blackKnight.png", 6, 7);

            addPiece("whiteBishop.png", 2, 0);
            addPiece("whiteBishop.png", 5, 0);
            addPiece("blackBishop.png", 2, 7);
            addPiece("blackBishop.png", 5, 7);

            addPiece("whiteRook.png", 0, 0);
            addPiece("whiteRook.png", 7, 0);
            addPiece("blackRook.png", 0, 7);
            addPiece("blackRook.png", 7, 7);

            addPiece("whiteQueen.png", 4, 0);
            addPiece("blackQueen.png", 4, 7);

            addPiece("whiteKing.png", 3, 0);
            addPiece("blackKing.png", 3, 7);
        }
    }

    private String getChessMove(ChessPiece chessPiece, int col, int row) {
        StringBuilder stringBuilder = new StringBuilder();
        if (chessPiece.getClass() == Knight.class) stringBuilder.append("N");
        else if (chessPiece.getClass() == Bishop.class) stringBuilder.append("B");
        else if (chessPiece.getClass() == Rook.class) stringBuilder.append("R");
        else if (chessPiece.getClass() == Queen.class) stringBuilder.append("Q");
        else if (chessPiece.getClass() == King.class) stringBuilder.append("K");
        if (!chessBoard.getFlipped()) {
            if (col == 0) stringBuilder.append("a");
            else if (col == 1) stringBuilder.append("b");
            else if (col == 2) stringBuilder.append("c");
            else if (col == 3) stringBuilder.append("d");
            else if (col == 4) stringBuilder.append("e");
            else if (col == 5) stringBuilder.append("f");
            else if (col == 6) stringBuilder.append("g");
            else if (col == 7) stringBuilder.append("h");
            if (row == 0) stringBuilder.append(8);
            else if (row == 1) stringBuilder.append(7);
            else if (row == 2) stringBuilder.append(6);
            else if (row == 3) stringBuilder.append(5);
            else if (row == 4) stringBuilder.append(4);
            else if (row == 5) stringBuilder.append(3);
            else if (row == 6) stringBuilder.append(2);
            else if (row == 7) stringBuilder.append(1);
        }
        else {
            if (col == 0) stringBuilder.append("h");
            else if (col == 1) stringBuilder.append("g");
            else if (col == 2) stringBuilder.append("f");
            else if (col == 3) stringBuilder.append("e");
            else if (col == 4) stringBuilder.append("d");
            else if (col == 5) stringBuilder.append("c");
            else if (col == 6) stringBuilder.append("b");
            else if (col == 7) stringBuilder.append("a");
            if (row == 0) stringBuilder.append(1);
            else if (row == 1) stringBuilder.append(2);
            else if (row == 2) stringBuilder.append(3);
            else if (row == 3) stringBuilder.append(4);
            else if (row == 4) stringBuilder.append(5);
            else if (row == 5) stringBuilder.append(6);
            else if (row == 6) stringBuilder.append(7);
            else if (row == 7) stringBuilder.append(8);
        }
        return stringBuilder.toString();
    }

    private void flipBoard() {
        chessBoard.getChildren().clear();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                pieceViews[row][col] = null;
            }
        }
        chessBoard.setFlipped(!chessBoard.getFlipped());
        chessBoard.drawBoard();
        initializePieces();
    }
}
