package com.example.systemprogramm.viewmodels;

import com.example.systemprogramm.controllermodels.Controller;
import com.example.systemprogramm.controllermodels.analyzer.MyException;
import com.example.systemprogramm.controllermodels.file.FileType;
import com.example.systemprogramm.controllermodels.file.MyDate;
import com.example.systemprogramm.controllermodels.file.record.*;
import com.example.systemprogramm.controllermodels.file.record.Record;
import com.example.systemprogramm.controllermodels.lowlevelfunction.LowLevelFunction;
import com.example.systemprogramm.viewmodels.windows.AddRecordWindow;
import com.example.systemprogramm.viewmodels.windows.AlertWindow;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.util.Calendar;

public class WindowView extends Application implements View {
    private static Controller controller;
    private static WindowView instance;
    private File currentFile = null;

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
    private ToggleButton buttonFileJSON;
    @FXML
    private ToggleButton buttonFileCSV;
    @FXML
    private ToggleButton buttonFileXML;
    @FXML
    private MenuButton fileMenuButton;
    @FXML
    private TextArea textAreaAnalyzer;
    @FXML
    private Label resultLabel;
    @FXML
    private SplitMenuButton actionLowLevelFunction;
    @FXML
    private TextField textFieldFirstValue;
    @FXML
    private TextField textFieldSecondValue;
    @FXML
    private Label resultLowLevelFunction;


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
        return instance == null ? instance = new WindowView() : instance;
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
        //buttonFileVariantOne.setSelected(true);
        fileMenuButton.getItems().get(0).setVisible(false);
        fileMenuButton.getItems().get(1).setVisible(false);
        fileMenuButton.getItems().get(2).setVisible(false);
        fileMenuButton.getItems().get(3).setVisible(false);
        fileMenuButton.getItems().get(4).setVisible(false);
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
                if (tableReocrds.getSelectionModel().getSelectedIndex() > -1) {
                    controller.deleteRecord(tableReocrds.getSelectionModel().getSelectedIndex());
                    update();
                }
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
                case "JSON" -> {
                    fileColumn1.setText("Путь к файлу");
                    fileColumn2.setText("Размер файла, КБайт");
                    fileColumn3.setText("Дата создания файла");
                    fileColumn1.setCellValueFactory(new PropertyValueFactory<>("filePath"));
                    fileColumn2.setCellValueFactory(p -> new SimpleStringProperty(Integer.toString(((RecordJSON) p.getValue()).getKByteSize())));
                    fileColumn3.setCellValueFactory(p -> new SimpleStringProperty(((RecordJSON) p.getValue()).getDateOfCreate().toString()));
                    buttonFileJSON.setSelected(true);
                    buttonFileCSV.setSelected(false);
                    buttonFileXML.setSelected(false);
                    labelFileVariant.setText("Формат файла: JSON.\n\nВводимые данные:\n" +
                            "Запись о файле:\nпуть к файлу, размер файла в килобайтах, дата создания файла.\n\n" +
                            "Для добавления новой записи необходимо указать файл на компьютере.");
                }
                case "CSV" -> {
                    fileColumn1.setText("Адрес");
                    fileColumn2.setText("Режим доступа");
                    fileColumn3.setText("Дата доступа");
                    fileColumn1.setCellValueFactory(new PropertyValueFactory<>("address"));
                    fileColumn2.setCellValueFactory(new PropertyValueFactory<>("accessMode"));
                    fileColumn3.setCellValueFactory(p -> new SimpleStringProperty(((RecordCSV) p.getValue()).getAccessDate().toString()));
                    buttonFileJSON.setSelected(false);
                    buttonFileCSV.setSelected(true);
                    buttonFileXML.setSelected(false);
                    labelFileVariant.setText("Формат файла: CSV.\n\nВводимые данные:\n" +
                            "Запись о ресурсе:\nадрес ресурса в сети Интернет, режим доступа(свободный, закрытый), дата доступа\n\n" +
                            "Для добавления новой записи вывести окно ввода.");
                }
                case "XML" -> {
                    fileColumn1.setText("Путь к файлу");
                    fileColumn2.setText("Размер файла, МБайт");
                    fileColumn3.setText("Дата последнего редактирования");
                    fileColumn1.setCellValueFactory(p -> new SimpleStringProperty(((RecordXML) p.getValue()).getFilePath()));
                    fileColumn2.setCellValueFactory(p -> new SimpleStringProperty(Double.toString(((RecordXML) p.getValue()).getMByteFileSize())));
                    fileColumn3.setCellValueFactory(p -> new SimpleStringProperty(((RecordXML) p.getValue()).getLastEditing().toString()));
                    buttonFileJSON.setSelected(false);
                    buttonFileCSV.setSelected(false);
                    buttonFileXML.setSelected(true);
                    labelFileVariant.setText("Формат файла: JSON.\n\nВводимые данные:\n" +
                            "Запись о файле:\nпуть к файлу, размер файла в мегабайтах, дата последнего редактирования файла.\n\n" +
                            "Для добавления новой записи необходимо указать файл на компьютере.");
                }
            }
            fileMenuButton.getItems().get(0).setVisible(true);
            fileMenuButton.getItems().get(1).setVisible(false);
            fileMenuButton.getItems().get(2).setVisible(true);
            fileMenuButton.getItems().get(3).setVisible(false);
            fileMenuButton.getItems().get(4).setVisible(false);
        }
    }

    @FXML
    private void editRecord(TableColumn.CellEditEvent<Record, String> cellEditEvent) throws ParseException {
        String data = cellEditEvent.getNewValue();
        Record record = cellEditEvent.getRowValue();
        if (fileColumn1 == cellEditEvent.getTableColumn()) {
            switch (whichVariantSelected()) {
                case JSON -> ((RecordJSON) record).setFilePath(data);
                case CSV -> ((RecordCSV) record).setAddress(data);
                case XML -> ((RecordXML) record).setFilePath(data);
            }
        } else if (fileColumn2 == cellEditEvent.getTableColumn()) {

            switch (whichVariantSelected()) {
                case JSON -> ((RecordJSON) record).setKByteSize(Integer.parseInt(data));
                case CSV -> ((RecordCSV) record).setAccessMode(data);
                case XML -> ((RecordXML) record).setMByteFileSize(Double.parseDouble(data));
            }
        } else if (fileColumn3 == cellEditEvent.getTableColumn()) {
            switch (whichVariantSelected()) {
                case JSON -> ((RecordJSON) record).setDateOfCreate(MyDate.parse(data));
                case CSV -> ((RecordCSV) record).setAccessDate(MyDate.parse(data));
                case XML -> ((RecordXML) record).setLastEditing(MyDate.parse(data));
            }
        }
        controller.editRecord(record, cellEditEvent.getTablePosition().getRow());
        update();
    }

    @FXML
    private void addRecord() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Добавить запись о файле");
        switch (whichVariantSelected()) {
            case JSON:
                File file = fileChooser.showOpenDialog(new Stage());
                if (file == null) return;
                BasicFileAttributes attribute = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(attribute.creationTime().toMillis());
                controller.addRecord(new RecordJSON(file.getPath(),
                        (int) file.length() / 1000,
                        new MyDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)))
                );
                break;
            case CSV:
                AddRecordWindow arw = new AddRecordWindow(controller);
                arw.start(newWindow("add-record-window.fxml", "Добавить запись"));
                break;
            case XML:
                file = fileChooser.showOpenDialog(new Stage());
                if (file == null) return;
                calendar = Calendar.getInstance();
                calendar.setTimeInMillis(file.lastModified());
                controller.addRecord(new RecordXML(file.getPath(),
                        (double) file.length() / 1000000,
                        new MyDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR))));
                break;
        }
        update();
    }

    @FXML
    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть файл с записями");
        File file = null;
        switch (whichVariantSelected()) {
            case JSON -> {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
                file = fileChooser.showOpenDialog(new Stage());
                if (file == null) return;
                controller.load(file, FileType.JSON);
            }
            case CSV -> {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
                file = fileChooser.showOpenDialog(new Stage());
                if (file == null) return;
                controller.load(file, FileType.CSV);
            }
            case XML -> {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
                file = fileChooser.showOpenDialog(new Stage());
                if (file == null) return;
                controller.load(file, FileType.XML);
            }
        }
        fileMenuButton.getItems().get(1).setVisible(true);
        fileMenuButton.getItems().get(3).setVisible(true);
        fileMenuButton.getItems().get(4).setVisible(true);
        currentFile = file;
        update();
    }

    @FXML
    private void save() {
        switch (whichVariantSelected()) {
            case JSON -> controller.save(currentFile, FileType.JSON);
            case CSV -> controller.save(currentFile, FileType.CSV);
            case XML -> controller.save(currentFile, FileType.XML);
        }
    }

    @FXML
    private void saveFileAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить как");
        switch (whichVariantSelected()) {
            case JSON -> {
                fileChooser.setInitialFileName("Записи.json");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
                File file = fileChooser.showSaveDialog(new Stage());
                if (file == null) return;
                controller.save(file, FileType.JSON);
            }
            case CSV -> {
                fileChooser.setInitialFileName("Записи.csv");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
                File file = fileChooser.showSaveDialog(new Stage());
                if (file == null) return;
                controller.save(file, FileType.CSV);
            }
            case XML -> {
                fileChooser.setInitialFileName("Записи.xml");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
                File file = fileChooser.showSaveDialog(new Stage());
                if (file == null) return;
                controller.save(file, FileType.XML);
            }
        }
    }

    @FXML
    private void createNewFile() {
        try {
            switch (whichVariantSelected()) {
                case JSON -> {
                    currentFile = File.createTempFile("temp", "json");
                    controller.load(currentFile, FileType.JSON);
                }
                case CSV -> {
                    currentFile = File.createTempFile("temp", "csv");
                    controller.load(currentFile, FileType.CSV);
                }
                case XML -> {
                    currentFile = File.createTempFile("temp", "xml");
                    controller.load(currentFile, FileType.XML);
                }
            }
            fileMenuButton.getItems().get(1).setVisible(true);
            fileMenuButton.getItems().get(3).setVisible(true);
            fileMenuButton.getItems().get(4).setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tableReocrds.getItems().clear();
    }

    @FXML
    private void analyze(ActionEvent event) {
        try {
            switch (((Button) event.getSource()).getText()) {
                case "if" -> resultLabel.setText("Результат: выполнилось условие " + controller.analyzeIf(textAreaAnalyzer.getText()));
                case "while" -> {
                    if (!controller.analyzeWhile(textAreaAnalyzer.getText())) {
                        resultLabel.setText("Результат: цикл ни разу не выполнится");
                    } else {
                        resultLabel.setText("Результат: цикл выполнится хотя бы раз");
                    }
                }
            }
        } catch (MyException e) {
            AlertWindow.showAlert(e.getMessage());
        }
    }

    private FileType whichVariantSelected() {
        if (buttonFileJSON.isSelected()) return FileType.JSON;
        if (buttonFileCSV.isSelected()) return FileType.CSV;
        if (buttonFileXML.isSelected()) return FileType.XML;
        throw new RuntimeException("just chill");
    }

    private Stage newWindow(String fxml, String title) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource(fxml));
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(fxmlLoader.load()));
        return stage;
    }

    @FXML
    private void sumLowLevel() {
        actionLowLevelFunction.setText("+");
    }

    @FXML
    private void divLowLevel() {
        actionLowLevelFunction.setText("/");
    }

    @FXML
    private void doAction(ActionEvent event) {
        try {

            switch (((SplitMenuButton) event.getSource()).getText()) {
                case "+" -> resultLowLevelFunction.setText("Результат: " +
                        LowLevelFunction.addWithOverflowCheck(Integer.parseInt(textFieldFirstValue.getText()), Integer.parseInt(textFieldSecondValue.getText())));
                case "/" -> resultLowLevelFunction.setText("Результат: " +
                        LowLevelFunction.div(Integer.parseInt(textFieldFirstValue.getText()), Integer.parseInt(textFieldSecondValue.getText())));
            }
        } catch (Throwable e) {
            AlertWindow.showAlert(e.getMessage());
        }
    }

}
