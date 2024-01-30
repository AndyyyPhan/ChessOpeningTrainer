package com.andyphan.chess;

import com.andyphan.chess.pieces.*;

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
        this.tileName = tileName;
        if (!tileName.equals("O-O")) translateToNumbers(tileName);
    }

    private void translateToNumbers(String tileName) {
        HashMap<Character, Integer> columns = new HashMap<>();
        columns.put('a', 0);
        columns.put('b', 1);
        columns.put('c', 2);
        columns.put('d', 3);
        columns.put('e', 4);
        columns.put('f', 5);
        columns.put('g', 6);
        columns.put('h', 7);
        if (!tileName.contains("x")) {
            if (tileName.length() > 2) tileName = tileName.substring(1);
            Character letter = tileName.charAt(0);
            col = columns.get(letter);
            row = 8 - Integer.parseInt(String.valueOf(tileName.charAt(1)));
        }
        else {
            Character letter = tileName.charAt(0);
            col = columns.get(letter);
            row = 0;
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
        HashMap<Integer, Character> columns = new HashMap<>();
        columns.put(0, 'a');
        columns.put(1, 'b');
        columns.put(2, 'c');
        columns.put(3, 'd');
        columns.put(4, 'e');
        columns.put(5, 'f');
        columns.put(6, 'g');
        columns.put(7, 'h');
        tileName = String.valueOf(columns.get(col)) +
                (8 - row);
    }

    public String getTilenameWithPiece() {
        if (chessPiece == null) return getTileName();
        if (chessPiece.getClass() == Knight.class) return "N" + getTileName();
        if (chessPiece.getClass() == Bishop.class) return "B" + getTileName();
        if (chessPiece.getClass() == Rook.class) return "R" + getTileName();
        if (chessPiece.getClass() == Queen.class) return "Q" + getTileName();
        if (chessPiece.getClass() == King.class) return "K" + getTileName();
        return getTileName();
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
        setOccupied(true);
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public ChessPiece getChessPiece() {
        return chessPiece;
    }
    public String getTileName() {
        return tileName;
    }
    public void setRowAndCol(int row, int col) {
        this.row = row;
        this.col = col;
        translateToTile(row, col);
    }

    public void setEqualToTile(Tile tile) {
        this.chessPiece = tile.chessPiece;
        this.occupied = tile.occupied;
        this.tileName = tile.tileName;
        this.row = tile.row;
        this.col = tile.col;
    }

    public void resetTile() {
        this.chessPiece = null;
        this.occupied = false;
        this.row = -1;
        this.col = -1;
        this.tileName = null;
    }
}
