package com.example.systemprogramm.viewmodels;

import com.example.systemprogramm.controllermodels.Controller;
import com.example.systemprogramm.controllermodels.file.Record;
import com.example.systemprogramm.controllermodels.file.*;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.Date;

public class WindowView extends Application implements View {
    private static Controller controller;
    private static WindowView instance;

    @FXML
    public TableView<Record> tableReocrds;
    @FXML
    private Label labelFileVariant;
    @FXML
    private TableColumn<Record, String> fileColumn1;
    @FXML
    private TableColumn<Record, String> fileColumn2;
    @FXML
    private TableColumn<Record, String> fileColumn3;
    @FXML
    private ToggleButton buttonFileVariantOne;
    @FXML
    private ToggleButton buttonFileVariantTwo;
    @FXML
    private ToggleButton buttonFileVariantThree;

    @Override
    public void run() {
        launch();
    }

    @Override
    public void update() {
        tableReocrds.getItems().clear();
        for (Record record : controller.getRecords()) {
            tableReocrds.getItems().add(record);
        }
    }

    @Override
    public void setController(Controller controller) {
        WindowView.controller = controller;
    }

    public static View getView() {
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
    void initialize() {
        instance = this;
        buttonFileVariantOne.setSelected(true);
        ContextMenu cm = new ContextMenu();
        MenuItem deleteRecords = new MenuItem("Удалить запись");
        cm.getItems().add(deleteRecords);
        tableReocrds.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    cm.show(tableReocrds, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                } else {
                    cm.hide();
                }
            }
        });
        deleteRecords.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.deleteRecord(tableReocrds.getSelectionModel().getSelectedIndex());
                update();
            }
        });
        fileColumn1.setCellFactory(TextFieldTableCell.forTableColumn());
        fileColumn2.setCellFactory(TextFieldTableCell.forTableColumn());
        fileColumn3.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @FXML
    private void fileVariantClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            tableReocrds.getItems().clear();
            switch (((ToggleButton) mouseEvent.getSource()).getText()) {
                case "Вариант 1":
                    fileColumn1.setText("Путь к файлу");
                    fileColumn2.setText("Размер файла, КБайт");
                    fileColumn3.setText("Дата создания файла");
                    fileColumn1.setCellValueFactory(new PropertyValueFactory<>("filePath"));
                    fileColumn2.setCellValueFactory(p -> new SimpleStringProperty(((RecordJSON) p.getValue()).getKByteSize() + ""));
                    fileColumn3.setCellValueFactory(p -> new SimpleStringProperty(((RecordJSON) p.getValue()).getDateOfCreate() + ""));

                    buttonFileVariantOne.setSelected(true);
                    buttonFileVariantTwo.setSelected(false);
                    buttonFileVariantThree.setSelected(false);
                    break;
                case "Вариант 2":
                    fileColumn1.setText("Адрес");
                    fileColumn2.setText("Режим доступа");
                    fileColumn3.setText("Дата доступа");
                    fileColumn1.setCellValueFactory(new PropertyValueFactory<>("address"));
                    fileColumn2.setCellValueFactory(new PropertyValueFactory<>("accessMode"));
                    fileColumn3.setCellValueFactory(new PropertyValueFactory<>("accessDate"));
                    buttonFileVariantOne.setSelected(false);
                    buttonFileVariantTwo.setSelected(true);
                    buttonFileVariantThree.setSelected(false);
                    break;
                case "Вариант 3":
                    fileColumn1.setText("Путь к файлу");
                    fileColumn2.setText("Размер файла, МБайт");
                    fileColumn3.setText("Дата последнего редактирования");
                    fileColumn1.setCellValueFactory(p -> new SimpleStringProperty(((RecordXML) p.getValue()).getFilePath()));
                    fileColumn2.setCellValueFactory(p -> new SimpleStringProperty(Double.toString(((RecordXML) p.getValue()).getMByteFileSize())));
                    fileColumn3.setCellValueFactory(p -> new SimpleStringProperty(((RecordXML) p.getValue()).getLastEditing() + ""));
                    buttonFileVariantOne.setSelected(false);
                    buttonFileVariantTwo.setSelected(false);
                    buttonFileVariantThree.setSelected(true);
                    break;
            }
        }
    }

    @FXML
    private void editRecord(TableColumn.CellEditEvent<Record, String> cellEditEvent) {
        String data = cellEditEvent.getNewValue();
        Record record = cellEditEvent.getRowValue();
        if (fileColumn1 == cellEditEvent.getTableColumn()) {
            switch (whichVariantSelected()) {
                case 1 -> ((RecordJSON) record).setFilePath(data);
                case 2 -> ((RecordCSV) record).setAddress(data);
                case 3 -> ((RecordXML) record).setFilePath(data);
            }
        } else if (fileColumn2 == cellEditEvent.getTableColumn()) {

            switch (whichVariantSelected()) {
                case 1 -> ((RecordJSON) record).setKByteSize(Integer.parseInt(data));
                case 2 -> ((RecordCSV) record).setAccessMode(data);
                case 3 -> ((RecordXML) record).setMByteFileSize(Double.parseDouble(data));
            }
        } else if (fileColumn3 == cellEditEvent.getTableColumn()) {
            switch (whichVariantSelected()) {
                //case 1 -> ((RecordJSON) record).setDateOfCreate(data);
                //case 2 -> ((RecordCSV) record).setAccessDate(data);
                case 3 -> ((RecordXML) record).setLastEditing(new Date(data));
            }
        }

        update();
    }

    @FXML
    private void createNewFile() {

    }

    @FXML
    private void openFile() throws JAXBException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть файл с записями");
        switch (whichVariantSelected()) {
            case 1:
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
                File file = fileChooser.showOpenDialog(new Stage());
                if (file == null) return;
                controller.loadJSON(file);
                break;
            case 2:
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
                file = fileChooser.showOpenDialog(new Stage());
                if (file == null) return;
                controller.loadCSV(file);
                break;
            case 3:
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
                file = fileChooser.showOpenDialog(new Stage());
                if (file == null) return;
                controller.loadXML(file);
                break;
        }
        update();
    }

    @FXML
    private void saveFileAs() throws JAXBException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить как");
        switch (whichVariantSelected()) {
            case 1:
                fileChooser.setInitialFileName("Записи.json");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
                File file = fileChooser.showSaveDialog(new Stage());
                if (file == null) return;
                controller.saveJSONAs(file);
                break;
            case 2:
                fileChooser.setInitialFileName("Записи.csv");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
                file = fileChooser.showSaveDialog(new Stage());
                if (file == null) return;
                controller.saveCSVAs(file);
                break;
            case 3:
                fileChooser.setInitialFileName("Записи.xml");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
                file = fileChooser.showSaveDialog(new Stage());
                if (file == null) return;
                controller.saveXMLAs(file);
                break;
        }
    }

    @FXML
    private void addRecord(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Добавить запись о файле");
        switch (whichVariantSelected()) {
            case 1:
                File file = fileChooser.showOpenDialog(new Stage());
                if (file == null) return;
                break;
            case 2:

                break;
            case 3:
                file = fileChooser.showOpenDialog(new Stage());
                if (file == null) return;
                controller.addRecord(new RecordXML(file.getPath(), (double) file.length() / 1000000, new Date(file.lastModified())));
                break;
        }
        update();
    }

    private int whichVariantSelected() {
        if (buttonFileVariantOne.isSelected()) return 1;
        if (buttonFileVariantTwo.isSelected()) return 2;
        if (buttonFileVariantThree.isSelected()) return 3;
        return 0;
    }
}
