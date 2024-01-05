package com.andyphan.chessopeningtrainer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class AllOpeningsMenu extends Scene {
    private static int ITEMS_PER_PAGE = 300;
    private List<ChessOpening> allChessOpenings;
    public AllOpeningsMenu() throws IOException {
        super(new VBox(10), 800, 600);
        Label mainLabel = new Label("List of All Openings");
        mainLabel.setFont(new Font(20));
        HBox mainLabelContainer = new HBox();
        mainLabelContainer.setAlignment(Pos.CENTER);
        mainLabelContainer.getChildren().add(mainLabel);
        HBox.setHgrow(mainLabel, Priority.ALWAYS);

        ChessOpeningsParser chessOpeningsParser = new ChessOpeningsParser();
        allChessOpenings = chessOpeningsParser.getAllChessOpenings();

        Pagination pagination = new Pagination(getPageCount(), 0);
        pagination.setPageFactory(this::createPage);


        VBox layout = (VBox) getRoot();
        layout.getChildren().addAll(mainLabelContainer, pagination);
    }

    private int getPageCount() {
        return (int) Math.ceil((double) allChessOpenings.size() / ITEMS_PER_PAGE);
    }

    private ListView<ChessOpening> createPage(int pageIndex) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, allChessOpenings.size());
        ObservableList<ChessOpening> displayedChessOpenings = FXCollections.observableArrayList(
                allChessOpenings.subList(fromIndex, toIndex));
        ListView<ChessOpening> listView = new ListView<>(displayedChessOpenings);
        listView.setCellFactory(new Callback<ListView<ChessOpening>, ListCell<ChessOpening>>() {
            @Override
            public ListCell<ChessOpening> call(ListView<ChessOpening> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(ChessOpening item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        }
                        else {
                            setText(item.getName());
                        }
                    }
                };
            }
        });
        return listView;
    }
}
