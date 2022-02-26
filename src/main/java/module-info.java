module com.example.systemprogramm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;
    requires java.xml.bind;
    requires opencsv;

    exports com.example.systemprogramm.viewmodels;
    exports com.example.systemprogramm.controllermodels.file;
    exports com.example.systemprogramm.controllermodels;
    opens com.example.systemprogramm.controllermodels.file to com.google.gson, java.xml.bind;
    exports com.example.systemprogramm.controllermodels.file.record;
    opens com.example.systemprogramm.controllermodels.file.record to com.google.gson, java.xml.bind;
    opens com.example.systemprogramm.viewmodels;
}