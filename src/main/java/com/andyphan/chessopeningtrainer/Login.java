package com.andyphan.chessopeningtrainer;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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
    }
}
