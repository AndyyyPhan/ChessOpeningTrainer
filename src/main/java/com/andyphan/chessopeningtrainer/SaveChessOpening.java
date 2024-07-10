package com.andyphan.chessopeningtrainer;

import com.andyphan.database.ChessOpening;
import com.andyphan.database.DatabaseManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SaveChessOpening extends Scene {
    private DatabaseManager database = new DatabaseManager();
    public SaveChessOpening(CreateChessOpening createChessOpening) {
        super(new VBox(10), 400, 300);
        VBox layout = (VBox) getRoot();

        Label titleLabel = new Label("Saving ChessOpening...");
        titleLabel.setFont(new Font(20));
        HBox titleLabelContainer = new HBox();
        titleLabelContainer.setAlignment(Pos.CENTER);
        titleLabelContainer.getChildren().add(titleLabel);
        HBox.setHgrow(titleLabel, Priority.ALWAYS);
        Label fenLabel = new Label("Enter fen (optional):");
        fenLabel.setFont(new Font(15));
        TextField fenField = new TextField();
        Label nameLabel = new Label("Enter chess opening name:");
        nameLabel.setFont(new Font(15));
        TextField nameField = new TextField();

        Button confirm = new Button("Confirm");
        confirm.setOnAction(e -> {
            ChessOpening chessOpening = new ChessOpening();
            if (fenField.getText().isBlank()) fenField.setText(database.getNextCreatedOpeningFen());
            chessOpening.setFen(fenField.getText());
            chessOpening.setName(nameField.getText());
            chessOpening.setMoves(createChessOpening.getMoves());
            database.persistChessOpening(chessOpening);
            database.saveChessOpening(chessOpening);
            ManageOpeningsMenu manageOpeningsMenu = (ManageOpeningsMenu) SceneManager.getPrimaryStage().getScene();
            manageOpeningsMenu.getAllOpeningsMenu().addToAllChessOpenings(chessOpening);
            Stage confirmingStage = (Stage) confirm.getScene().getWindow();
            Stage chessOpeningStage = (Stage) createChessOpening.getRoot().getScene().getWindow();
            confirmingStage.close();
            chessOpeningStage.close();
            AllOpeningsMenu.setIsChessSceneOpen(false);
            AllOpeningsMenu.setActiveChessScene(null);
        });
        confirm.setMinSize(30, 30);
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
        });
        cancel.setMinSize(30, 30);

        HBox buttonContainer = new HBox(5);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(confirm, cancel);
        HBox.setHgrow(confirm, Priority.ALWAYS);
        HBox.setHgrow(cancel, Priority.ALWAYS);

        layout.getChildren().addAll(titleLabelContainer, new HBox(5, fenLabel, fenField), new HBox(5, nameLabel, nameField), buttonContainer);
    }
}
