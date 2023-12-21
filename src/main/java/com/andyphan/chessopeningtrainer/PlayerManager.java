package com.andyphan.chessopeningtrainer;

public class PlayerManager {
    private static Player currentPlayer;
    public static Player getCurrentPlayer() {
        return currentPlayer;
    }
    public static void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }
}
