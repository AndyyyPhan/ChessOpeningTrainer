package com.andyphan.chess;
import java.util.HashMap;

public class Tile {
    private ChessPiece chessPiece;
    private boolean occupied;
    private String tileName;
    private int row;
    private int col;
    public Tile(String tileName) {
        setTileName(tileName);
    }
    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
        translateToTile(row, col);
    }

    public void setTileName(String tileName) {
        translateToNumbers(tileName);
        this.tileName = tileName;
    }

    public void setTileName(String tileName, boolean isFlipped) {
        if (isFlipped) {
            col = 7 - (tileName.charAt(0) - 'a');
            row = 7 - (8 - Character.getNumericValue(tileName.charAt(1)));
            translateToTile(row, col);
        }
        else setTileName(tileName);
    }

    private void translateToNumbers(String tileName) {
        if (tileName.length() == 2) {
            this.col = tileName.charAt(0) - 'a';
            this.row = 8 - Character.getNumericValue(tileName.charAt(1));
        }
        else if (tileName.length() == 3) {
            this.col = tileName.charAt(1) - 'a';
            this.row = 8 - Character.getNumericValue(tileName.charAt(2));
        }
    }

    public int translateColToNumber(Character letter) {
        HashMap<Character, Integer> columns = new HashMap<>();
        columns.put('a', 0);
        columns.put('b', 1);
        columns.put('c', 2);
        columns.put('d', 3);
        columns.put('e', 4);
        columns.put('f', 5);
        columns.put('g', 6);
        columns.put('h', 7);
        return columns.get(letter);
    }

    private void translateToTile(int row, int col) {
        tileName = String.valueOf((char) ('a' + col)) + (8 - row);
    }

    public String getTileName() {
        return tileName;
    }

    public String getTileName(boolean isFlipped) {
        if (!isFlipped) return tileName;

        int flippedRow = 1 + row;
        char flippedCol = (char) ('a' + (7 - col));
        return String.valueOf(flippedCol) + flippedRow;
    }

    public int flipRow() {
        return 7 - this.row;
    }

    public int flipCol() {
        return 7 - this.col;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
        setOccupied(chessPiece != null);
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    public int getRow() {
        return this.row;
    }
    public int getCol() {
        return col;
    }
    public ChessPiece getChessPiece() {
        return chessPiece;
    }
    public void setRowAndCol(int row, int col) {
        this.row = row;
        this.col = col;
        translateToTile(row, col);
    }

    public void resetTile() {
        this.chessPiece = null;
        this.occupied = false;
        this.row = -1;
        this.col = -1;
        this.tileName = null;
    }
}
