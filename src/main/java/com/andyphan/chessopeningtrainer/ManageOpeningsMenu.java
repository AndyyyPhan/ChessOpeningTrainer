package com.andyphan.chessopeningtrainer;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ManageOpeningsMenu extends Scene {
    private DatabaseManager database;
    public void initialize() {
        database = new DatabaseManager();
    }

    public ManageOpeningsMenu(Player player) {
        super(new VBox(10), 800, 600);
        initialize();
        Label mainLabel = new Label("List of Openings in Practice");
        mainLabel.setFont(new Font(20));
        mainLabel.setAlignment(Pos.CENTER);
    }
}
