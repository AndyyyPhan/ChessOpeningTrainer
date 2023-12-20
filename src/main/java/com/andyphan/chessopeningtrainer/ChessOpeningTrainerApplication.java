package com.andyphan.chessopeningtrainer;

import javafx.application.Application;
import javafx.stage.Stage;

public class ChessOpeningTrainerApplication extends Application {
    public void start(Stage primaryStage) {
        SceneManager.setPrimaryStage(primaryStage);
        SceneManager.setScene(new Login());
        primaryStage.setTitle("Chess Opening Trainer");
        primaryStage.show();
    }
}