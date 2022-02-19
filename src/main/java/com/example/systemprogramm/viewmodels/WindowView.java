package com.example.systemprogramm.viewmodels;

import com.example.systemprogramm.controllermodels.Controller;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowView extends Application implements View{
    private static Controller controller;
    private static WindowView instance;

    @Override
    public void run() {
        launch();
    }

    @Override
    public void update() {

    }

    @Override
    public void setController(Controller controller) {
        WindowView.controller = controller;
    }

    public static View getView(){
        if (instance == null) {
            return (instance = new WindowView());
        } else {
            return instance;
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("window-view.fxml"));
        stage.setTitle("Системное программирование");
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void initialize(){
        instance = this;
    }

}
