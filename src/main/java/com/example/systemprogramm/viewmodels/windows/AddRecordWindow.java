package com.example.systemprogramm.viewmodels.windows;

import com.example.systemprogramm.controllermodels.Controller;
import com.example.systemprogramm.controllermodels.file.MyDate;
import com.example.systemprogramm.controllermodels.file.record.RecordCSV;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.w3c.dom.Text;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

public class AddRecordWindow extends Window {

    private static Controller controller;
    private static TextArea logArea;

    @FXML
    private TextField textFieldAddress;

    @FXML
    private ComboBox<String> comboBoxAccess;

    @FXML
    private TextField textFieldDateAccess;

    public AddRecordWindow() {
    }

    public AddRecordWindow(Controller controller, TextArea logArea) {
        AddRecordWindow.controller = controller;
        AddRecordWindow.logArea = logArea;
    }

    public void start(Stage primaryStage) {
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
    }

    @FXML
    private void initialize() {
        comboBoxAccess.setValue("Свободный");
        comboBoxAccess.getItems().add("Свободный");
        comboBoxAccess.getItems().add("Закрытый");
    }

    @FXML
    private void addRecord(ActionEvent event) throws ParseException {
        if (!textFieldAddress.getText().isEmpty() && !textFieldDateAccess.getText().isEmpty()) {
            controller.addRecord(new RecordCSV(textFieldAddress.getText(),
                    comboBoxAccess.getValue(),
                    MyDate.parse(textFieldDateAccess.getText()))
            );

            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        } else {

        }
    }

}
