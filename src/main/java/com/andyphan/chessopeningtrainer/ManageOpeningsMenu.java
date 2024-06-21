package com.andyphan.chessopeningtrainer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageOpeningsMenu extends Scene {
    private DatabaseManager database = DatabaseManager.getInstance();
    private AllOpeningsMenu allOpeningsMenu;
    private ListView<ChessOpening> listView;

    public ManageOpeningsMenu(User user) {
        super(new VBox(10), 800, 600);

        Label mainLabel = new Label("List of Openings in Practice");
        mainLabel.setFont(new Font(20));
        HBox mainLabelContainer = new HBox();
        mainLabelContainer.setAlignment(Pos.CENTER);
        mainLabelContainer.getChildren().add(mainLabel);
        HBox.setHgrow(mainLabel, Priority.ALWAYS);

        Label allOpeningsLabel = new Label("Click here to show all openings: ");
        allOpeningsLabel.setFont(new Font(15));
        Button allOpeningsButton = new Button("All Openings");
        allOpeningsButton.setFont(new Font(12));
        HBox allOpeningsContainer = new HBox(5, allOpeningsLabel, allOpeningsButton);

        allOpeningsButton.setOnAction(e -> {
            Stage allOpeningsStage = new Stage();
            allOpeningsStage.initModality(Modality.WINDOW_MODAL);
            allOpeningsStage.initOwner(SceneManager.getPrimaryStage());
            try {
                allOpeningsStage.setScene(new AllOpeningsMenu());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            allOpeningsMenu = (AllOpeningsMenu) allOpeningsStage.getScene();
            allOpeningsStage.show();
        });

        listView = new ListView<>();
        ObservableList<ChessOpening> chessOpenings = FXCollections.observableArrayList();
        chessOpenings.addAll(database.getOpeningsInPractice());
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(ChessOpening item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                 else setText(item.getName());
            }
        });
        listView.setItems(chessOpenings);

        VBox layout = (VBox) getRoot();
        layout.getChildren().addAll(mainLabelContainer, listView, allOpeningsContainer);
    }
    public AllOpeningsMenu getAllOpeningsMenu() {
        return allOpeningsMenu;
    }
    public ListView<ChessOpening> getListView() {
        return listView;
    }
}
