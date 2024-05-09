package com.andyphan.chessopeningtrainer;

import com.andyphan.chess.ChessScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class AllOpeningsMenu extends Scene {
    private static int ITEMS_PER_PAGE = 300;
    private List<ChessOpening> allChessOpenings;
    private ObservableList<ChessOpening> chessOpenings = FXCollections.observableArrayList();
    private FilteredList<ChessOpening> filteredOpenings;
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
        chessOpenings.addAll(allChessOpenings);

        Pagination pagination = new Pagination(getPageCount(), 0);
        pagination.setPageFactory(this::createPage);

        Label filterLabel = new Label("Search Chess Opening:");
        filterLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        TextField filterField = new TextField();
        filterField.setMinWidth(500);

        filteredOpenings = new FilteredList<>(chessOpenings, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredOpenings.setPredicate(
                    chessOpening -> newValue == null || newValue.isEmpty() || chessOpening.getName().toLowerCase()
                            .contains(newValue.toLowerCase())
            );
        pagination.setPageFactory(this::createPage);
        pagination.setPageCount(getNewPageCount());
        System.out.println("Filtered Openings Size: " + filteredOpenings.size());
        System.out.println(getNewPageCount());
        });

        VBox layout = (VBox) getRoot();
        layout.getChildren().addAll(mainLabelContainer, new HBox (5, filterLabel, filterField), pagination);
    }

    private int getPageCount() {
        return (int) Math.ceil((double) allChessOpenings.size() / ITEMS_PER_PAGE);
    }

    private int getNewPageCount() {
        if (filteredOpenings.isEmpty()) return 1;
        return (int) Math.ceil((double) filteredOpenings.size() / ITEMS_PER_PAGE);
    }

    private ListView<ChessOpening> createPage(int pageIndex) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, filteredOpenings.size());

        ObservableList<ChessOpening> displayedChessOpenings = FXCollections.observableArrayList(
                filteredOpenings.subList(fromIndex, toIndex));
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

        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Stage chessStage = new Stage();
                chessStage.initModality(Modality.WINDOW_MODAL);
                chessStage.initOwner(SceneManager.getPrimaryStage());
                chessStage.setScene(new ChessScene(listView.getSelectionModel().getSelectedItem()));
                chessStage.show();
            }
        });

        return listView;
    }
}
