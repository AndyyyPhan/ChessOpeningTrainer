package com.andyphan.chess;

import com.andyphan.chess.pieces.*;
import com.andyphan.chessopeningtrainer.ChessOpening;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import java.util.Objects;

import static com.andyphan.chess.ChessBoard.BOARD_SIZE;
import static com.andyphan.chess.ChessBoard.TILE_SIZE;
import static java.lang.Math.abs;

public class ChessScene extends Scene {
    private final ChessBoard chessBoard = new ChessBoard();
    protected Tile[][] chessGrid = chessBoard.getChessGrid();
    private Tile selectedTile = new Tile(-1, -1);
    private Tile targetTile = new Tile(-1, -1);
    private int selectedRow;
    private int selectedCol;
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
            selectedRow = row;
            selectedCol = col;
        }
    }

    private void movePiece(int targetCol, int targetRow) {
        ChessPiece selectedPiece = selectedTile.getChessPiece();
        targetTile.setRowAndCol(chessGrid[targetRow][targetCol].getRow(), chessGrid[targetRow][targetCol].getCol());
        targetTile.setChessPiece(chessGrid[targetRow][targetCol].getChessPiece());
        if (selectedPiece != chessGrid[targetRow][targetCol].getChessPiece() && isValidMove(selectedPiece, targetTile)) {
            chessBoard.getChildren().remove(selectedPiece);
            chessGrid[selectedRow][selectedCol].setChessPiece(null);
            selectedTile.resetTile();
            if (targetTile.getChessPiece() != null) chessBoard.getChildren().remove(targetTile.getChessPiece());
            chessGrid[targetRow][targetCol].setChessPiece(selectedPiece);
            chessBoard.add(selectedPiece, targetCol, targetRow);
            selectedPiece.setTile(chessGrid[targetRow][targetCol]);
            playerMove.setNextMove();
        }
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

    private boolean isValidMove(ChessPiece chessPiece, Tile targetTile) {
        if (targetTile.getChessPiece() != null && targetTile.getChessPiece().getAlliance() == chessPiece.getAlliance()) return false;
        if (chessPiece.getClass() == Pawn.class) return isPawnMoveValid(chessPiece, targetTile);
        else if (chessPiece.getClass() == Knight.class) return isKnightMoveValid(chessPiece, targetTile);
        else if (chessPiece.getClass() == Bishop.class) return isBishopMoveValid(chessPiece, targetTile);
        else if (chessPiece.getClass() == Rook.class) return isRookMoveValid(chessPiece, targetTile);
        return false;
    }

    private boolean isPawnMoveValid(ChessPiece chessPiece, Tile targetTile) {
        if (chessPiece.getAlliance() == Alliance.WHITE) {
            return ((targetTile.getChessPiece() == null && targetTile.getRow() == chessPiece.getRow() - 1 && targetTile.getCol() == chessPiece.getCol()) ||
                    (chessGrid[chessPiece.getRow() - 1][chessPiece.getCol()].getChessPiece() == null &&
                            targetTile.getChessPiece() == null && (targetTile.getRow() == chessPiece.getRow() - 1 ||
                            targetTile.getRow() == chessPiece.getRow() - 2) && chessPiece.getRow() == 6 && targetTile.getCol() == chessPiece.getCol()) ||
                    (chessPiece.getCol() == targetTile.getCol() + 1 || chessPiece.getCol() == targetTile.getCol() - 1) &&
                            (targetTile.getRow() == chessPiece.getRow() - 1) && targetTile.getChessPiece() != null);
        }
        else if (chessPiece.getAlliance() == Alliance.BLACK) {
            return ((targetTile.getChessPiece() == null && targetTile.getRow() == chessPiece.getRow() + 1 && targetTile.getCol() == chessPiece.getCol()) ||
                    chessGrid[chessPiece.getRow() + 1][chessPiece.getCol()].getChessPiece() == null &&
                            targetTile.getChessPiece() == null && (targetTile.getRow() == chessPiece.getRow() + 1 ||
                            targetTile.getRow() == chessPiece.getRow() + 2 && chessPiece.getRow() == 1 && targetTile.getCol() == chessPiece.getCol()) ||
                    (chessPiece.getCol() == targetTile.getCol() + 1 || chessPiece.getCol() == targetTile.getCol() - 1) &&
                            (targetTile.getRow() == chessPiece.getRow() + 1) && targetTile.getChessPiece() != null);
        }
        return false;
    }

    private boolean isKnightMoveValid(ChessPiece chessPiece, Tile targetTile) {
        return (((targetTile.getCol() == chessPiece.getCol() - 1 || targetTile.getCol() == chessPiece.getCol() + 1) &&
                (targetTile.getRow() == chessPiece.getRow() - 2 || targetTile.getRow() == chessPiece.getRow() + 2)) ||
                ((targetTile.getCol() == chessPiece.getCol() - 2 || targetTile.getCol() == chessPiece.getCol() + 2) &&
                        (targetTile.getRow() == chessPiece.getRow() - 1 || targetTile.getRow() == chessPiece.getRow() + 1)));
    }

    private boolean isBishopMoveValid(ChessPiece chessPiece, Tile targetTile) {
        if (abs(chessPiece.getRow() - targetTile.getRow()) != abs(chessPiece.getCol() - targetTile.getCol())) return false;
        Tile currentTile = chessPiece.getTile();
        if (targetTile.getRow() < chessPiece.getRow()) {
            if (targetTile.getCol() > chessPiece.getCol()) {
                while (!Objects.equals(currentTile.getTileName(), chessBoard.getChessGridTileByName(targetTile.getRow()+1, targetTile.getCol()-1).getTileName()) && currentTile.getRow() - 1 > 0 && currentTile.getCol() + 1 < BOARD_SIZE) {
                    currentTile = chessBoard.getChessGridTileByName(currentTile.getRow() - 1, currentTile.getCol() + 1);
                    if (currentTile.getChessPiece() != null) return false;
                }
                return true;
            }
            else if (targetTile.getCol() < chessPiece.getCol()) {
                while (!Objects.equals(currentTile.getTileName(), chessBoard.getChessGridTileByName(targetTile.getRow()+1, targetTile.getCol()+1).getTileName()) && currentTile.getRow() - 1 > 0 && currentTile.getCol() - 1 > 0) {
                    currentTile = chessBoard.getChessGridTileByName(currentTile.getRow() - 1, currentTile.getCol() - 1);
                    if (currentTile.getChessPiece() != null) return false;
                }
                return true;
            }
        }
        else if (targetTile.getRow() > chessPiece.getRow()) {
            if (targetTile.getCol() > chessPiece.getCol()) {
                while (!Objects.equals(currentTile.getTileName(), chessBoard.getChessGridTileByName(targetTile.getRow()-1, targetTile.getCol()-1).getTileName()) && currentTile.getRow() + 1 < BOARD_SIZE && currentTile.getCol() + 1 < BOARD_SIZE) {
                    currentTile = chessBoard.getChessGridTileByName(currentTile.getRow() + 1, currentTile.getCol() + 1);
                    if (currentTile.getChessPiece() != null) return false;
                }
                return true;
            }
            else if (targetTile.getCol() < chessPiece.getCol()) {
                while (!Objects.equals(currentTile.getTileName(), chessBoard.getChessGridTileByName(targetTile.getRow()-1, targetTile.getCol()+1).getTileName()) && currentTile.getRow() + 1 < BOARD_SIZE && currentTile.getCol() - 1 > 0) {
                    currentTile = chessBoard.getChessGridTileByName(currentTile.getRow() + 1, currentTile.getCol() - 1);
                    if (currentTile.getChessPiece() != null) return false;
                }
                return true;
            }
        }
        return false;
    }

    private boolean isRookMoveValid(ChessPiece chessPiece, Tile targetTile) {
        if (chessPiece.getRow() != targetTile.getRow() && chessPiece.getCol() != targetTile.getCol()) return false;
        Tile currentTile = chessPiece.getTile();
        if (targetTile.getCol() > chessPiece.getCol()) {
            while (currentTile.getCol() < targetTile.getCol() - 1 && currentTile.getCol() < BOARD_SIZE) {
                currentTile = chessBoard.getChessGridTileByName(currentTile.getRow(), currentTile.getCol()+1);
                if (currentTile.getChessPiece() != null) return false;
            }
            return true;
        }
        else if (targetTile.getCol() < chessPiece.getCol()) {
            while (currentTile.getCol() > targetTile.getCol() + 1 && currentTile.getCol() > 0) {
                currentTile = chessBoard.getChessGridTileByName(currentTile.getRow(), currentTile.getCol()-1);
                if (currentTile.getChessPiece() != null) return false;
            }
            return true;
        }
        else if (targetTile.getRow() > chessPiece.getRow()) {
            while (currentTile.getRow() < targetTile.getRow() - 1 && currentTile.getRow() < BOARD_SIZE) {
                currentTile = chessBoard.getChessGridTileByName(currentTile.getRow()+1, currentTile.getCol());
                if (currentTile.getChessPiece() != null) return false;
            }
            return true;
        }
        else if (targetTile.getRow() < chessPiece.getRow()) {
            while (currentTile.getRow() > targetTile.getRow() + 1 && currentTile.getRow() > 0) {
                currentTile = chessBoard.getChessGridTileByName(currentTile.getRow()-1, currentTile.getCol());
                if (currentTile.getChessPiece() != null) return false;
            }
            return true;
        }
        return false;
    }
}
