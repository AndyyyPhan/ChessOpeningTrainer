package com.andyphan.chessopeningtrainer;

import com.andyphan.chess.ChessScene;
import com.andyphan.database.ChessOpening;
import com.andyphan.database.DatabaseManager;
import com.andyphan.database.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private static boolean isChessSceneOpen = false;
    private static Stage activeChessScene = null;

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
        Label backLabel = new Label("Click here to go back to Main Menu: ");
        backLabel.setFont(new Font(15));
        Button allOpeningsButton = new Button("All Openings");
        allOpeningsButton.setFont(new Font(12));
        Button backButton = new Button("Back");
        backButton.setFont(new Font(12));
        HBox allOpeningsContainer = new HBox(5, allOpeningsLabel, allOpeningsButton);
        HBox backContainer = new HBox(5, backLabel, backButton);

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

        backButton.setOnAction(e -> SceneManager.setScene(new MainMenu(user)));

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
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && isChessSceneOpen) {
                activeChessScene.toFront();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Chess Scene Active");
                alert.setHeaderText("A chess scene is currently active");
                alert.setContentText("Please close active chess scene before opening a new one.");
                alert.showAndWait();
            }
            else if (event.getClickCount() == 2) {
                Stage chessStage = new Stage();
                isChessSceneOpen = true;
                activeChessScene = chessStage;
                chessStage.initModality(Modality.WINDOW_MODAL);
                chessStage.initOwner(SceneManager.getPrimaryStage());
                chessStage.setScene(new ChessScene(listView.getSelectionModel().getSelectedItem()));
                chessStage.setOnCloseRequest(event1 -> {
                    isChessSceneOpen = false;
                    activeChessScene = null;
                });
                chessStage.show();
            }
        });

        VBox layout = (VBox) getRoot();
        layout.getChildren().addAll(mainLabelContainer, listView, allOpeningsContainer, backContainer);
    }
    public AllOpeningsMenu getAllOpeningsMenu() {
        return allOpeningsMenu;
    }
    public ListView<ChessOpening> getListView() {
        return listView;
    }
}
