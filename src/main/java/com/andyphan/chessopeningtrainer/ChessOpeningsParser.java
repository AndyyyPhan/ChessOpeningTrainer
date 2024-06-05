package com.andyphan.chessopeningtrainer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ChessOpeningsParser {
    public static String getJSONText() throws IOException{
        String filename = "src/main/resources/eco.json";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            stringBuilder.append(line).append('\n');
            line = bufferedReader.readLine();
        }
        return stringBuilder.toString();
    }
    public static ArrayList<ChessOpening> getAllChessOpenings() throws IOException {
        JSONArray jsonArray = new JSONArray(getJSONText());
        ArrayList<ChessOpening> allChessOpenings = new ArrayList<>();
        for (int i=0; i<jsonArray.length(); i++) {
            ChessOpening chessOpening = new ChessOpening();
            JSONObject jsonChessOpening = jsonArray.getJSONObject(i);
            chessOpening.setName(jsonChessOpening.getString("name"));
            chessOpening.setFen(jsonChessOpening.getString("fen"));
            chessOpening.setMoves(jsonChessOpening.getString("moves"));
            allChessOpenings.add(chessOpening);
        }
        return allChessOpenings;
    }
    public ArrayList<String> getAllChessOpeningNames() throws IOException {
        ArrayList<String> allChessOpeningNames = new ArrayList<>();
        for (ChessOpening chessOpening : getAllChessOpenings()) {
            allChessOpeningNames.add(chessOpening.getName());
        }
        return allChessOpeningNames;
    }

    private static ArrayList<String> getAllCreatedFens() throws IOException {
        ArrayList<String> allCreatedFens = new ArrayList<>();
        for (ChessOpening chessOpening : getAllChessOpenings()) {
            if (chessOpening.getFen().contains("CREATED")) {
                allCreatedFens.add(chessOpening.getFen());
            }
        }
        return allCreatedFens;
    }

    public static String getNextCreatedOpeningFen() throws IOException {
        return "CREATED_" + getAllCreatedFens().size();
    }
}
