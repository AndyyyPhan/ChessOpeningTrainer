package com.andyphan.chessopeningtrainer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ChessOpeningsWriter {
    private final static String filename = "src/main/resources/eco.json";
    public static void addChessOpening(ChessOpening chessOpening) throws IOException {
        List<Map<String, Object>> existingOpenings = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            existingOpenings = mapper.readValue(new File(filename), List.class);
        } catch (FileNotFoundException e) {

        }
        if (chessOpening.getFen().isBlank()) chessOpening.setFen(ChessOpeningsParser.getNextCreatedOpeningFen());
        Map<String, Object> openingData = new LinkedHashMap<>();
        openingData.put("fen", chessOpening.getFen());
        openingData.put("src", "CREATED");
        openingData.put("eco", "CREATED");
        openingData.put("moves", chessOpening.getMoves());
        openingData.put("name", chessOpening.getName());
        existingOpenings.add(openingData);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), existingOpenings);
    }
}
