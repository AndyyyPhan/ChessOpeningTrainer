package com.andyphan.chess.pieces;

import com.andyphan.chess.ChessBoard;
import com.andyphan.chess.ChessPiece;
import com.andyphan.chess.Tile;

public class Knight extends ChessPiece {
    public Knight(ChessBoard chessBoard, String imagePath) {
        super(chessBoard, imagePath);
    }
    @Override
    public boolean isValidMove(Tile targetTile) {
        if (targetTile.getChessPiece() != null) {
            if (targetTile.getChessPiece().getClass() == King.class ||
                    (targetTile.getChessPiece().getAlliance() == getAlliance())) return false;
        }
        return (((targetTile.getCol() == getCol() - 1 || targetTile.getCol() == getCol() + 1) &&
                (targetTile.getRow() == getRow() - 2 || targetTile.getRow() == getRow() + 2)) ||
                ((targetTile.getCol() == getCol() - 2 || targetTile.getCol() == getCol() + 2) &&
                        (targetTile.getRow() == getRow() - 1 || targetTile.getRow() == getRow() + 1)));
    }

    @Override
    public boolean isCheck() {
        boolean isTopLeft = false;
        boolean isTopRight = false;
        boolean isRightUp = false;
        boolean isRightDown = false;
        boolean isBotRight = false;
        boolean isBotLeft = false;
        boolean isLeftDown = false;
        boolean isLeftUp = false;
        Tile possibleTopLeftCheckTile = chessBoard.getChessGridTileByName(getRow() - 2, getCol() - 1);
        Tile possibleTopRightCheckTile = chessBoard.getChessGridTileByName(getRow() - 2, getCol() + 1);
        Tile possibleRightUpCheckTile = chessBoard.getChessGridTileByName(getRow() - 1, getCol() + 2);
        Tile possibleRightDownCheckTile = chessBoard.getChessGridTileByName(getRow() + 1, getCol() + 2);
        Tile possibleBotRightCheckTile = chessBoard.getChessGridTileByName(getRow() + 2, getCol() + 1);
        Tile possibleBotLeftCheckTile = chessBoard.getChessGridTileByName(getRow() + 2, getCol() - 1);
        Tile possibleLeftDownCheckTile = chessBoard.getChessGridTileByName(getRow() + 1, getCol() - 2);
        Tile possibleLeftUpCheckTile = chessBoard.getChessGridTileByName(getRow() - 1, getCol() - 2);
        if (possibleTopLeftCheckTile != null && possibleTopLeftCheckTile.getChessPiece() != null &&
                possibleTopLeftCheckTile.getChessPiece().getClass() == King.class &&
                possibleTopLeftCheckTile.getChessPiece().getAlliance() != getAlliance()) isTopLeft = true;
        else if (possibleTopRightCheckTile != null && possibleTopRightCheckTile.getChessPiece() != null &&
                possibleTopRightCheckTile.getChessPiece().getClass() == King.class &&
                possibleTopRightCheckTile.getChessPiece().getAlliance() != getAlliance()) isTopRight = true;
        else if (possibleRightUpCheckTile != null && possibleRightUpCheckTile.getChessPiece() != null &&
                possibleRightUpCheckTile.getChessPiece().getClass() == King.class &&
                possibleRightUpCheckTile.getChessPiece().getAlliance() != getAlliance()) isRightUp = true;
        else if (possibleRightDownCheckTile != null && possibleRightDownCheckTile.getChessPiece() != null &&
                possibleRightDownCheckTile.getChessPiece().getClass() == King.class &&
                possibleRightDownCheckTile.getChessPiece().getAlliance() != getAlliance()) isRightDown = true;
        else if (possibleBotRightCheckTile != null && possibleBotRightCheckTile.getChessPiece() != null &&
                possibleBotRightCheckTile.getChessPiece().getClass() == King.class &&
                possibleBotRightCheckTile.getChessPiece().getAlliance() != getAlliance()) isBotRight = true;
        else if (possibleBotLeftCheckTile != null && possibleBotLeftCheckTile.getChessPiece() != null &&
                possibleBotLeftCheckTile.getChessPiece().getClass() == King.class &&
                possibleBotLeftCheckTile.getChessPiece().getAlliance() != getAlliance()) isBotLeft = true;
        else if (possibleLeftDownCheckTile != null && possibleLeftDownCheckTile.getChessPiece() != null &&
                possibleLeftDownCheckTile.getChessPiece().getClass() == King.class &&
                possibleLeftDownCheckTile.getChessPiece().getAlliance() != getAlliance()) isLeftDown = true;
        else if (possibleLeftUpCheckTile != null && possibleLeftUpCheckTile.getChessPiece() != null &&
                possibleLeftUpCheckTile.getChessPiece().getClass() == King.class &&
                possibleLeftUpCheckTile.getChessPiece().getAlliance() != getAlliance()) isLeftUp = true;

        return isTopLeft || isTopRight || isRightUp || isRightDown || isBotRight || isBotLeft || isLeftDown || isLeftUp;
    }
}
