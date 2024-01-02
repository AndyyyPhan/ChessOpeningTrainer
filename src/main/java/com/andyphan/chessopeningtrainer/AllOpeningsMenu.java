package com.andyphan.chessopeningtrainer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AllOpeningsMenu extends Scene {
    public AllOpeningsMenu() {
        super(new VBox(10), 800, 600);
        Label mainLabel = new Label("List of All Openings");
        mainLabel.setFont(new Font(20));
        HBox mainLabelContainer = new HBox();
        mainLabelContainer.setAlignment(Pos.CENTER);
        mainLabelContainer.getChildren().add(mainLabel);
        HBox.setHgrow(mainLabel, Priority.ALWAYS);

        ListView<ChessOpening> table = new ListView<>();
        ObservableList<ChessOpening> allChessOpenings = FXCollections.observableArrayList();

        table.setItems(allChessOpenings);

        VBox layout = (VBox) getRoot();
        layout.getChildren().addAll(mainLabelContainer, table);
    }
}
