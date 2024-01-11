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
        this.tileName = tileName;
        translateToNumbers(tileName);
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
        Character letter = tileName.charAt(0);
        col = columns.get(letter);
        row = 8 - tileName.charAt(1);
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
}
