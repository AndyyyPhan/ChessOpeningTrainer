package com.andyphan.chessopeningtrainer;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainMenu extends Scene {
    private DatabaseManager database;
    public void initialize() {
        database = new DatabaseManager();
    }

    public MainMenu(User user) {
        super(new VBox(10), 400, 300);
        initialize();

        Label mainMenuLabel = new Label("Main Menu");
        mainMenuLabel.setFont(new Font(20));
        Button practiceButton = new Button("Practice your openings!");
        Button manageOpeningsButton = new Button("Manage your openings!");
        Button logoutButton = new Button("Logout");
        VBox loginLayout = (VBox) getRoot();
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.getChildren().addAll(mainMenuLabel, practiceButton, manageOpeningsButton, logoutButton);

        manageOpeningsButton.setOnAction(e -> SceneManager.setScene(new ManageOpeningsMenu(database.getUser(user.getUsername()))));

        logoutButton.setOnAction(e -> {
            SceneManager.setScene(new Login());
            UserManager.setCurrentUser(null);
        });
    }
}
