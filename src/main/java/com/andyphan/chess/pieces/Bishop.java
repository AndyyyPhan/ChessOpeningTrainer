package com.andyphan.chess.pieces;

import com.andyphan.chess.ChessBoard;
import com.andyphan.chess.ChessPiece;
import com.andyphan.chess.Tile;

import java.util.Objects;

import static com.andyphan.chess.ChessBoard.BOARD_SIZE;
import static java.lang.Math.abs;

public class Bishop extends ChessPiece {
    public Bishop(ChessBoard chessBoard, String imagePath) {
        super(chessBoard, imagePath);
    }

    @Override
    public boolean isValidMove(Tile targetTile) {
        if (targetTile.getChessPiece() != null) {
            if (targetTile.getChessPiece().getClass() == King.class ||
                    (targetTile.getChessPiece().getAlliance() == getAlliance())) return false;
        }
        if (abs(getRow() - targetTile.getRow()) != abs(getCol() - targetTile.getCol())) return false;
        Tile currentTile = getTile();
        if (targetTile.getRow() < getRow()) {
            if (targetTile.getCol() > getCol()) {
                while (!Objects.equals(currentTile.getTileName(), chessBoard.getChessGridTileByName(targetTile.getRow()+1, targetTile.getCol()-1).getTileName()) && currentTile.getRow() - 1 > 0 && currentTile.getCol() + 1 < BOARD_SIZE) {
                    currentTile = chessBoard.getChessGridTileByName(currentTile.getRow() - 1, currentTile.getCol() + 1);
                    if (currentTile.getChessPiece() != null) return false;
                }
                return true;
            }
            else if (targetTile.getCol() < getCol()) {
                while (!Objects.equals(currentTile.getTileName(), chessBoard.getChessGridTileByName(targetTile.getRow()+1, targetTile.getCol()+1).getTileName()) && currentTile.getRow() - 1 > 0 && currentTile.getCol() - 1 > 0) {
                    currentTile = chessBoard.getChessGridTileByName(currentTile.getRow() - 1, currentTile.getCol() - 1);
                    if (currentTile.getChessPiece() != null) return false;
                }
                return true;
            }
        }
        else if (targetTile.getRow() > getRow()) {
            if (targetTile.getCol() > getCol()) {
                while (!Objects.equals(currentTile.getTileName(), chessBoard.getChessGridTileByName(targetTile.getRow()-1, targetTile.getCol()-1).getTileName()) && currentTile.getRow() + 1 < BOARD_SIZE && currentTile.getCol() + 1 < BOARD_SIZE) {
                    currentTile = chessBoard.getChessGridTileByName(currentTile.getRow() + 1, currentTile.getCol() + 1);
                    if (currentTile.getChessPiece() != null) return false;
                }
                return true;
            }
            else if (targetTile.getCol() < getCol()) {
                while (!Objects.equals(currentTile.getTileName(), chessBoard.getChessGridTileByName(targetTile.getRow()-1, targetTile.getCol()+1).getTileName()) && currentTile.getRow() + 1 < BOARD_SIZE && currentTile.getCol() - 1 > 0) {
                    currentTile = chessBoard.getChessGridTileByName(currentTile.getRow() + 1, currentTile.getCol() - 1);
                    if (currentTile.getChessPiece() != null) return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCheck() {
        boolean isTopLeft = false;
        boolean isTopRight = false;
        boolean isBotLeft = false;
        boolean isBotRight = false;

        // Top-Left Check Tile
        Tile possibleCheckTile = chessBoard.getChessGridTileByName(getRow(), getCol());
        while (possibleCheckTile.getRow() - 1 >= 0 && possibleCheckTile.getCol() - 1 >= 0) {
            possibleCheckTile = chessBoard.getChessGridTileByName(possibleCheckTile.getRow() - 1, possibleCheckTile.getCol() - 1);
            if (possibleCheckTile.getChessPiece() != null && possibleCheckTile.getChessPiece().getClass() != King.class) return false;
            else if (possibleCheckTile.getChessPiece() != null && possibleCheckTile.getChessPiece().getClass() == King.class &&
            possibleCheckTile.getChessPiece().getAlliance() != getAlliance()) isTopLeft = true;
        }
        // Top-Right Check Tile
        possibleCheckTile = chessBoard.getChessGridTileByName(getRow(), getCol());
        while (possibleCheckTile.getRow() - 1 >= 0 && possibleCheckTile.getCol() + 1 < BOARD_SIZE) {
            possibleCheckTile = chessBoard.getChessGridTileByName(possibleCheckTile.getRow() - 1, possibleCheckTile.getCol() + 1);
            if (possibleCheckTile.getChessPiece() != null && possibleCheckTile.getChessPiece().getClass() != King.class) return false;
            if (possibleCheckTile.getChessPiece() != null && possibleCheckTile.getChessPiece().getClass() == King.class &&
                    possibleCheckTile.getChessPiece().getAlliance() != getAlliance()) isTopRight = true;
        }
        // Bottom-Left Check Tile
        possibleCheckTile = chessBoard.getChessGridTileByName(getRow(), getCol());
        while (possibleCheckTile.getRow() + 1 < BOARD_SIZE && possibleCheckTile.getCol() - 1 >= 0) {
            possibleCheckTile = chessBoard.getChessGridTileByName(possibleCheckTile.getRow() + 1, possibleCheckTile.getCol() - 1);
            if (possibleCheckTile.getChessPiece() != null && possibleCheckTile.getChessPiece().getClass() != King.class) return false;
            if (possibleCheckTile.getChessPiece() != null && possibleCheckTile.getChessPiece().getClass() == King.class &&
                    possibleCheckTile.getChessPiece().getAlliance() != getAlliance()) isBotLeft = true;
        }
        // Bottom-Right Check Tile
        possibleCheckTile = chessBoard.getChessGridTileByName(getRow(), getCol());
        while (possibleCheckTile.getRow() + 1 < BOARD_SIZE && possibleCheckTile.getCol() + 1 < BOARD_SIZE) {
            possibleCheckTile = chessBoard.getChessGridTileByName(possibleCheckTile.getRow() + 1, possibleCheckTile.getCol() + 1);
            if (possibleCheckTile.getChessPiece() != null && possibleCheckTile.getChessPiece().getClass() != King.class) return false;
            if (possibleCheckTile.getChessPiece() != null && possibleCheckTile.getChessPiece().getClass() == King.class &&
                    possibleCheckTile.getChessPiece().getAlliance() != getAlliance()) isBotRight = true;
        }
        return isTopLeft || isTopRight || isBotLeft || isBotRight;
    }
}
