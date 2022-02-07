package com.worwafi;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public AnchorPane pozadie;
    @FXML
    private JFXButton buttonLetiskovyVypravca;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonLetiskovyVypravca.setOnAction(event -> {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/letiskovy_vypravca.fxml"));
           Stage stageLetiskovyVypravca = (Stage) buttonLetiskovyVypravca.getScene().getWindow();
           try {
               stageLetiskovyVypravca.setScene(
                       new Scene(loader.load())
               );
           } catch (IOException e) {
               e.printStackTrace();
           }
           stageLetiskovyVypravca.show();
       });
    }
}
