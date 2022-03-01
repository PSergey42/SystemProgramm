package com.example.systemprogramm.viewmodels.windows;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

/**
 * Класс для вывода ошибок пользователю
 */
public class AlertWindow extends Window {

    private static String message;

    @FXML
    private Label label;

    @FXML
    private ImageView image;

    /**
     * Метод, закрывающий окно
     * @param event событие, через которое закрываем окно
     */
    @FXML
    void clickOk(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    /**
     * Действия при запуске окна
     */
    @FXML
    void initialize() {
        image.setImage(new Image(new File("WARNING.png").toURI().toString()));
        label.setFont(new Font(16));
        label.setText(message);
    }

    /**
     * Показ окна пользователю
     * @param primaryStage Окно, в котором будет показано сообщение
     */
    public static void start(Stage primaryStage) {
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();

    }

    /**
     * Метод, который загружает окно из fxml-файла и открывает окно
     * @param message Сообщение для пользователя
     */
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
