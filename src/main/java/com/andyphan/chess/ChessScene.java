package com.andyphan.chess;

import com.andyphan.chess.pieces.*;
import com.andyphan.chessopeningtrainer.ChessOpening;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

import static com.andyphan.chess.ChessBoard.BOARD_SIZE;
import static com.andyphan.chess.ChessBoard.TILE_SIZE;

public class ChessScene extends Scene {
    private final ChessBoard chessBoard = new ChessBoard();
    protected Tile[][] chessGrid = chessBoard.getChessGrid();
    private Tile selectedTile = new Tile(-1, -1);
    private Tile targetTile = new Tile(-1, -1);
    private final Move playerMove = new Move();
    public ChessScene(ChessOpening chessOpening) {
        super(new VBox(10), BOARD_SIZE * TILE_SIZE + 50, BOARD_SIZE * TILE_SIZE + 100);
        initializePieces();

        chessBoard.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                selectedTile.resetTile();
            }
            handleMouseClick((int) event.getX() / TILE_SIZE, (int) event.getY() / TILE_SIZE);

        });

        Button flipBoardButton = new Button("Flip Board");
        flipBoardButton.setOnAction(event -> flipBoard());

        VBox layout = (VBox) getRoot();
        layout.getChildren().addAll(chessBoard, flipBoardButton);
    }

    private void handleMouseClick(int clickedCol, int clickedRow) {
        if (selectedTile.getRow() == -1 && selectedTile.getCol() == -1) {
            selectPiece(clickedCol, clickedRow);
        }
        else {
            movePiece(clickedCol, clickedRow);
        }
    }

    private void selectPiece(int col, int row) {
        ChessPiece selectedPiece = chessGrid[row][col].getChessPiece();
        if (selectedPiece != null && playerMove.getCurrentMove() == selectedPiece.getAlliance()) {
            selectedTile.setRowAndCol(chessGrid[row][col].getRow(), chessGrid[row][col].getCol());
            selectedTile.setChessPiece(chessGrid[row][col].getChessPiece());
        }
        System.out.println(getChessMove(selectedTile));
    }

    private void movePiece(int targetCol, int targetRow) {
        ChessPiece selectedPiece = selectedTile.getChessPiece();
        targetTile.setEqualToTile(chessGrid[targetRow][targetCol]);
        selectedPiece.setTargetTile(targetTile);
        if (selectedPiece != chessGrid[targetRow][targetCol].getChessPiece()) {
            if (selectedPiece != chessGrid[targetRow][targetCol].getChessPiece() && isValidMove(selectedPiece, targetTile)) {
                chessBoard.getChildren().remove(selectedPiece);
                chessBoard.getChessGridTile(selectedTile.getRow(), selectedTile.getCol()).setChessPiece(null);
                selectedTile.resetTile();
                chessGrid[targetRow][targetCol].setChessPiece(selectedPiece);
                chessBoard.add(selectedPiece, targetCol, targetRow);
                selectedPiece.setTile(chessGrid[targetRow][targetCol]);
                playerMove.setNextMove();
            }
        }
        System.out.println(getChessMove(selectedPiece.getTile()));
        selectedTile.setRowAndCol(-1, -1);
    }

    private void addPiece(String imageName, Tile tile) {
        if (imageName.contains("Pawn")) chessBoard.getChessGridTile(tile.getRow(), tile.getCol()).setChessPiece(new Pawn(imageName));
        else if (imageName.contains("Knight")) chessBoard.getChessGridTile(tile.getRow(), tile.getCol()).setChessPiece(new Knight(imageName));
        else if (imageName.contains("Bishop")) chessBoard.getChessGridTile(tile.getRow(), tile.getCol()).setChessPiece(new Bishop(imageName));
        else if (imageName.contains("Rook")) chessBoard.getChessGridTile(tile.getRow(), tile.getCol()).setChessPiece(new Rook(imageName));
        else if (imageName.contains("Queen")) chessBoard.getChessGridTile(tile.getRow(), tile.getCol()).setChessPiece(new Queen(imageName));
        else if (imageName.contains("King")) chessBoard.getChessGridTile(tile.getRow(), tile.getCol()).setChessPiece(new King(imageName));
        chessBoard.add(chessBoard.getChessGridTile(tile.getRow(), tile.getCol()).getChessPiece(), tile.getCol(), tile.getRow());
        chessBoard.getChessGridTile(tile.getRow(), tile.getCol()).getChessPiece().setTile(tile);
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

    private void setupPieces() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (chessBoard.getChessGridTile(row, col).getChessPiece() != null) {
                    chessBoard.add(chessBoard.getChessGridTile(row, col).getChessPiece(), chessGrid[row][col].getCol(), chessGrid[row][col].getRow());
                }
            }
        }
    }

    private String getChessMove(Tile tile) {
        return tile.getTileName();
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
        chessBoard.setChessGrid(chessGridCopy);
        setupPieces();
        selectedTile.resetTile();
    }
//    private void resetSelectedTile() {
//        selectedTile.setChessPiece(null);
//        selectedTile.setRowAndCol(-1, -1);
//        selectedTile.setOccupied(false);
//    }

    private boolean isValidMove(ChessPiece chessPiece, Tile targetTile) {
        ArrayList<PieceMoves> candidateMoves = chessPiece.getCandidateMoves();
        if (chessPiece.getClass() == Pawn.class) {
            if (chessPiece.getAlliance() == Alliance.WHITE) {
                if (candidateMoves.contains(PieceMoves.MOVE) && !candidateMoves.contains(PieceMoves.DOUBLE_MOVE)) {
                    return targetTile.getChessPiece() == null && targetTile.getRow() == chessPiece.getRow() - 1;
                }
                else if (candidateMoves.contains(PieceMoves.DOUBLE_MOVE)) {
                    return chessGrid[chessPiece.getRow()-1][chessPiece.getCol()].getChessPiece() == null &&
                            targetTile.getChessPiece() == null && (targetTile.getRow() == chessPiece.getRow() - 1 ||
                            targetTile.getRow() == chessPiece.getRow() - 2);
                }
            }
            else if (chessPiece.getAlliance() == Alliance.BLACK) {
                if (candidateMoves.contains(PieceMoves.MOVE) && !candidateMoves.contains(PieceMoves.DOUBLE_MOVE)) {
                    return targetTile.getChessPiece() == null && targetTile.getRow() == chessPiece.getRow() + 1;
                }
                else if (candidateMoves.contains(PieceMoves.DOUBLE_MOVE)) {
                    return chessGrid[chessPiece.getRow()+1][chessPiece.getCol()].getChessPiece() == null &&
                            targetTile.getChessPiece() == null && (targetTile.getRow() == chessPiece.getRow() + 1 ||
                            targetTile.getRow() == chessPiece.getRow() + 2);
                }
            }
        }
        return false;
    }
}
