package com.example.systemprogramm.viewmodels.windows;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class AlertWindow extends Window {

    private static String message;

    @FXML
    private Label label;

    @FXML
    private ImageView image;

    @FXML
    void clickOk(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void initialize() {
        image.setImage(new Image(new File("WARNING.png").toURI().toString()));
        label.setFont(new Font(16));
        label.setText(message);
    }

    public static void start(Stage primaryStage) {
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();

    }

    public static void showAlert(String message) {
        try {
            AlertWindow.message = message;
            FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("alert-window.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Ошибка");
            stage.setScene(new Scene(fxmlLoader.load()));
            start(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
