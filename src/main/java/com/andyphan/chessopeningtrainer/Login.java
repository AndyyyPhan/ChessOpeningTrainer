package com.andyphan.chessopeningtrainer;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Scene {
    private DatabaseManager database;
    public void initialize() {
        database = new DatabaseManager();
    }
    public Login() {
        super(new VBox(10), 400, 300);
        initialize();

        VBox loginLayout = (VBox) getRoot();
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button createButton = new Button("Create New User");
        Button closeButton = new Button("Close");

        loginLayout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, createButton, closeButton);

        loginButton.setOnAction(e -> {
            // Check username and password
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (database.isValidPlayer(username, password)) {
                // Successful login, transition to course search screen
                Player player = new Player();
                player.setUsername(username);
                player.setPassword(password);
                PlayerManager.setCurrentPlayer(player);
                goToMainMenu(player);
            } else {
                showAlert("Invalid credentials! Please try again.");
            }
        });

        // Set create button action
        createButton.setOnAction(e -> {
            String newUsername = usernameField.getText();
            String newPassword = passwordField.getText();
            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                showAlert("Please enter both username and password.");
            } else if (database.isPlayerExisting(newUsername)) {
                showAlert("Username already exists. Please enter correct password or create a different user.");
            } else if (!database.validPasswordLength(newPassword)) {
                showAlert("Please enter a password with at least 8 characters.");
            } else {
                database.addPlayer(newUsername, newPassword);
                showSuccess("Successfully created account!");
            }
        });
        closeButton.setOnAction(e -> {
            Stage stage = (Stage) loginLayout.getScene().getWindow();
            stage.close();
        });
    }

    private void goToMainMenu(Player player) {
        SceneManager.setScene(new MainMenu(player));
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
