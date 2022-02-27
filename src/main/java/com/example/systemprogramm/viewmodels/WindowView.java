package com.example.systemprogramm.viewmodels;

import com.example.systemprogramm.controllermodels.Controller;
import com.example.systemprogramm.controllermodels.analyzer.AnalyzeException;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    @FXML
    private TextArea logArea;


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
        stage.setResizable(false);
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    void initialize() {
        instance = this;
        fileMenuButton.setDisable(true);
        fileMenuButton.getItems().get(1).setVisible(false);
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

        textFieldFirstValue.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("0?([1-9]\\d*)?")) {
                textFieldFirstValue.setText(oldValue);
            }
        }));
        textFieldSecondValue.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("0?([1-9]\\d*)?")) {
                textFieldSecondValue.setText(oldValue);
            }
        }));
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
            fileMenuButton.setDisable(false);
            fileMenuButton.getItems().get(1).setVisible(false);
            fileMenuButton.getItems().get(3).setVisible(false);
            fileMenuButton.getItems().get(4).setVisible(false);
        }
    }

    @FXML
    private void editRecord(TableColumn.CellEditEvent<Record, String> cellEditEvent) {
        try {
            String data = cellEditEvent.getNewValue();
            Record record = cellEditEvent.getRowValue();
            Record oldValue = record.clone();
            if (fileColumn1 == cellEditEvent.getTableColumn()) {
                switch (whichVariantSelected()) {
                    case JSON -> ((RecordJSON) record).setFilePath(data);
                    case CSV -> ((RecordCSV) record).setAddress(data);
                    case XML -> ((RecordXML) record).setFilePath(data);
                }
            } else if (fileColumn2 == cellEditEvent.getTableColumn()) {

                switch (whichVariantSelected()) {
                    case JSON -> ((RecordJSON) record).setKByteSize(Integer.parseInt(data));
                    case CSV -> {
                        if (data.equals("Свободный") || data.equals("Закрытый"))
                            ((RecordCSV) record).setAccessMode(data);
                        else throw new AnalyzeException("Значения могут быть только 'Свободный' или 'Закрытый'");
                    }
                    case XML -> ((RecordXML) record).setMByteFileSize(Double.parseDouble(data));
                }
            } else if (fileColumn3 == cellEditEvent.getTableColumn()) {
                switch (whichVariantSelected()) {
                    case JSON -> ((RecordJSON) record).setDateOfCreate(Record.parse(data));
                    case CSV -> ((RecordCSV) record).setAccessDate(Record.parse(data));
                    case XML -> ((RecordXML) record).setLastEditing(Record.parse(data));
                }
            }
            controller.editRecord(record, cellEditEvent.getTablePosition().getRow());
            printLog("Запись " + oldValue + " успешно изменена на " + record);
        } catch (RuntimeException e) {
            printLog("Error: " + e.getMessage());
            AlertWindow.showAlert(e.getMessage());
        }
        update();
    }

    @FXML
    private void addRecord() {
        try {
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
                            new Date(calendar.getTimeInMillis()))
                    );
                    printLog("Запись " + controller.getRecords().get(controller.getRecords().size() - 1) + " успешно добавлена");
                    break;
                case CSV:
                    AddRecordWindow arw = new AddRecordWindow(controller, logArea);
                    arw.start(newWindow("add-record-window.fxml", "Добавить запись"));
                    break;
                case XML:
                    file = fileChooser.showOpenDialog(new Stage());
                    if (file == null) return;
                    calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(file.lastModified());
                    controller.addRecord(new RecordXML(file.getPath(),
                            (double) file.length() / 1000000,
                            new Date(calendar.getTimeInMillis())));
                    printLog("Запись " + controller.getRecords().get(controller.getRecords().size() - 1) + " успешно добавлена");
                    break;
            }
            update();
        } catch (Exception e) {
            printLog("Error: " + e.getMessage());
            AlertWindow.showAlert(e.getMessage());
        }
    }

    @FXML
    private void openFile() {
        try {
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
            printLog("Файл " + currentFile.getAbsolutePath() + " успешно открыт");
            update();
        } catch (Throwable e) {
            printLog("Error: " + e.getMessage());
            AlertWindow.showAlert(e.getMessage());
        }
    }

    @FXML
    private void save() {
        switch (whichVariantSelected()) {
            case JSON -> controller.save(currentFile, FileType.JSON);
            case CSV -> controller.save(currentFile, FileType.CSV);
            case XML -> controller.save(currentFile, FileType.XML);
        }
        printLog("Файл " + currentFile.getName() + " сохранён");
    }

    @FXML
    private void saveFileAs() {
        try {
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
            printLog("Файл сохранён по пути: " + currentFile.getAbsolutePath());
        } catch (Throwable e) {
            printLog("Error: " + e.getMessage());
            AlertWindow.showAlert(e.getMessage());
        }
    }

    @FXML
    private void createNewFile() {
        try {
            switch (whichVariantSelected()) {
                case JSON -> {
                    currentFile = new File("test.json");
                    if (currentFile.exists()) {
                        currentFile.delete();
                        currentFile.createNewFile();
                    }
                    controller.load(currentFile, FileType.JSON);
                }
                case CSV -> {
                    currentFile = new File("test1.csv");
                    if (currentFile.exists()) {
                        currentFile.delete();
                        currentFile.createNewFile();
                    }
                    controller.load(currentFile, FileType.CSV);
                }
                case XML -> {
                    currentFile = new File("test2.xml");
                    if (currentFile.exists()) {
                        currentFile.delete();
                        currentFile.createNewFile();
                    }
                    controller.load(currentFile, FileType.XML);
                }
            }
            printLog("Создан новый файл: " + currentFile.getAbsolutePath());
            fileMenuButton.getItems().get(1).setVisible(true);
            fileMenuButton.getItems().get(3).setVisible(true);
            fileMenuButton.getItems().get(4).setVisible(true);
            tableReocrds.getItems().clear();
        } catch (IOException e) {
            printLog("Error: " + e.getMessage());
            AlertWindow.showAlert(e.getMessage());
        }
    }

    @FXML
    private void analyze(ActionEvent event) {
        try {
            switch (((Button) event.getSource()).getText()) {
                case "if" -> {
                    String result = controller.analyzeIf(textAreaAnalyzer.getText());
                    if (result.equals("null")) result = "Не выполнилось ни одно из условий";
                    resultLabel.setText("Результат: выполнилось условие " + result);
                    printLog("Анализ выполнился с результатом: " + result);
                }
                case "while" -> {
                    if (!controller.analyzeWhile(textAreaAnalyzer.getText())) {
                        resultLabel.setText("Результат: цикл ни разу не выполнится");
                        printLog("Анализ выполнился с результатом: цикл ни разу не выполнится");
                    } else {
                        resultLabel.setText("Результат: цикл выполнится хотя бы раз");
                        printLog("Анализ выполнился с результатом: цикл выполнится хотя бы раз");
                    }
                }
            }
        } catch (Throwable e) {
            printLog("Error: " + e.getMessage());
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
            int result;
            switch (((SplitMenuButton) event.getSource()).getText()) {
                case "+" -> {
                    result = LowLevelFunction.addWithOverflowCheck(Integer.parseInt(textFieldFirstValue.getText()), Integer.parseInt(textFieldSecondValue.getText()));
                    if (result == 0 && !textFieldFirstValue.getText().equals("0"))
                        throw new ArithmeticException("Слишком большое число!");
                    resultLowLevelFunction.setText(result + "");
                    printLog("Результатом сложения является: " + result);
                }
                case "/" -> {
                    if (textFieldSecondValue.getText().equals("0")) throw new ArithmeticException("Деление на 0");
                    else {
                        result = LowLevelFunction.div(Integer.parseInt(textFieldFirstValue.getText()), Integer.parseInt(textFieldSecondValue.getText()));
                        resultLowLevelFunction.setText("" + result);
                        printLog("Результатом деления является: " + result);
                    }
                }
            }
        } catch (RuntimeException e) {
            printLog("Error: " + e.getMessage());
            AlertWindow.showAlert(e.getMessage());
        }
    }

    private void printLog(String message) {
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        SimpleDateFormat currentDate = new SimpleDateFormat("'['hh:mm:ss']: '");
        logArea.appendText(currentDate.format(date) + message + "\n");
    }
}
