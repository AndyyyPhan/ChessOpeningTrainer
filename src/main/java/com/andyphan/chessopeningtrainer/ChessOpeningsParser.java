package com.andyphan.chessopeningtrainer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChessOpeningsParser {
    public String getJSONText() throws IOException{
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
    public ArrayList<String> getAllChessOpeningNames() throws IOException {
        JSONArray jsonArray = new JSONArray(getJSONText());
        ArrayList<String> allChestOpeningNames = new ArrayList<>();
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject jsonChessOpening = jsonArray.getJSONObject(i);
            allChestOpeningNames.add(jsonChessOpening.getString("name"));
        }
        return allChestOpeningNames;
    }
}
