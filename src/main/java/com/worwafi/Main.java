package com.worwafi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Min Client");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_screen.fxml"));
        primaryStage.setScene(
                new Scene(loader.load())
        );
        primaryStage.show();
    }
}
