package com.andyphan.chessopeningtrainer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.List;

public class AllOpeningsMenu extends Scene {
    private static int ITEMS_PER_PAGE = 300;
    private List<String> allChessOpenings;
    public AllOpeningsMenu() throws IOException {
        super(new VBox(10), 800, 600);
        Label mainLabel = new Label("List of All Openings");
        mainLabel.setFont(new Font(20));
        HBox mainLabelContainer = new HBox();
        mainLabelContainer.setAlignment(Pos.CENTER);
        mainLabelContainer.getChildren().add(mainLabel);
        HBox.setHgrow(mainLabel, Priority.ALWAYS);

        ChessOpeningsParser chessOpeningsParser = new ChessOpeningsParser();
        allChessOpenings = chessOpeningsParser.getAllChessOpeningNames();

        Pagination pagination = new Pagination(getPageCount(), 0);
        pagination.setPageFactory(this::createPage);


        VBox layout = (VBox) getRoot();
        layout.getChildren().addAll(mainLabelContainer, pagination);
    }

    private int getPageCount() {
        return (int) Math.ceil((double) allChessOpenings.size() / ITEMS_PER_PAGE);
    }

    private ListView<String> createPage(int pageIndex) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, allChessOpenings.size());
        ObservableList<String> displayedChessOpenings = FXCollections.observableArrayList(
                allChessOpenings.subList(fromIndex, toIndex));
        return new ListView<>(displayedChessOpenings);
    }
}
