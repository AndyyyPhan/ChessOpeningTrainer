package com.andyphan.chessopeningtrainer;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Scene {
    private DatabaseManager database = DatabaseManager.getInstance();
    public Login() {
        super(new VBox(10), 400, 300);

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
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (database.isValidUser(username, password)) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                goToMainMenu(user);
                database.loginUser(username, password);
            } else {
                showAlert("Invalid credentials! Please try again.");
            }
        });

        createButton.setOnAction(e -> {
            String newUsername = usernameField.getText();
            String newPassword = passwordField.getText();
            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                showAlert("Please enter both username and password.");
            } else if (database.isUserExisting(newUsername)) {
                showAlert("Username already exists. Please enter correct password or create a different user.");
            } else if (!database.validPasswordLength(newPassword)) {
                showAlert("Please enter a password with at least 8 characters.");
            } else {
                database.addUser(newUsername, newPassword);
                showSuccess("Successfully created account!");
            }
        });
        closeButton.setOnAction(e -> {
            Stage stage = (Stage) loginLayout.getScene().getWindow();
            stage.close();
        });
    }

    private void goToMainMenu(User user) {
        SceneManager.setScene(new MainMenu(user));
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
