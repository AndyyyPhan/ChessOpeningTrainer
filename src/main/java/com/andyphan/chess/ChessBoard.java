package com.andyphan.chess;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ChessBoard extends GridPane {
    protected static final int TILE_SIZE = 80;
    public static final int BOARD_SIZE = 8;
    private boolean isFlipped = false;
    private Tile[][] chessGrid = new Tile[BOARD_SIZE][BOARD_SIZE];

    public ChessBoard() {
        drawBoard();
    }

    protected void drawBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                drawSquare(row, col, (row + col) % 2 == 0 ? Color.rgb(215, 215, 215) : Color.rgb(123, 124, 124));
                chessGrid[row][col] = new Tile(row, col);
                if (isFlipped) {
                    Text colLabel = new Text(String.valueOf((char) ('h' - col)));
                    colLabel.setFont(new Font(15));
                    colLabel.setStyle("-fx-font-weight: bold");
                    StackPane colPane = new StackPane(colLabel);
                    colPane.setMinSize(20, 20);
                    colPane.setAlignment(Pos.CENTER);
                    add(colPane, col, 8);

                    Text rowLabel = new Text(String.valueOf(row + 1));
                    rowLabel.setFont(new Font(15));
                    rowLabel.setStyle("-fx-font-weight: bold");
                    StackPane rowPane = new StackPane(rowLabel);
                    rowPane.setMinSize(20, 20);
                    rowPane.setAlignment(Pos.CENTER);
                    add(rowPane, 9, row);
                }
                else {
                    Text colLabel = new Text(String.valueOf((char) ('a' + col)));
                    colLabel.setFont(new Font(15));
                    colLabel.setStyle("-fx-font-weight: bold");
                    StackPane colPane = new StackPane(colLabel);
                    colPane.setMinSize(20, 20);
                    colPane.setAlignment(Pos.CENTER);
                    add(colPane, col, 8);

                    Text rowLabel = new Text(String.valueOf(8 - row));
                    rowLabel.setFont(new Font(15));
                    rowLabel.setStyle("-fx-font-weight: bold");
                    StackPane rowPane = new StackPane(rowLabel);
                    rowPane.setMinSize(20, 20);
                    rowPane.setAlignment(Pos.CENTER);
                    add(rowPane, 9, row);
                }
            }
        }
    }

    private void drawSquare(int row, int col, Color color) {
        Rectangle square = new Rectangle(TILE_SIZE, TILE_SIZE);
        square.setFill(color);
        add(square, col, row);
    }

    protected void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    protected boolean getFlipped() {
        return isFlipped;
    }
    public Tile[][] getChessGrid() {
        return chessGrid;
    }
    public void setChessGrid(Tile[][] chessGrid) {
        int rows = chessGrid.length;
        int cols = chessGrid[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                this.chessGrid[row][col].setEqualToTile(chessGrid[7-row][7-col]);
            }
        }
    }
    public Tile getChessGridTile(int row, int col) {
        for (Tile[] tileArray : chessGrid) {
            for (Tile tile : tileArray) {
                if (tile.getRow() == row && tile.getCol() == col) return tile;
            }
        }
        return null;
    }

    public Tile getChessGridTileByName(int row, int col) {
        Tile givenTile = new Tile(row, col);
        for (Tile[] tileArray : chessGrid) {
            for (Tile tile : tileArray) {
                if (givenTile.getTileName().equals(tile.getTileName())) return tile;
            }
        }
        return null;
    }

    public void setChessGridTileByName(int row, int col, ChessPiece chessPiece) {
        int validRow = row;
        int validCol = col;
        if (isFlipped) {
            validRow = 7 - row;
            validCol = 7 - col;
        }
        chessGrid[validRow][validCol].setChessPiece(chessPiece);
    }
}
