module com.example.systemprogramm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;
    requires opencsv;
    requires java.naming;
    requires java.xml.bind;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.persistence;


    opens com.example.systemprogramm.viewmodels;
    exports com.example.systemprogramm.viewmodels;
    opens com.example.systemprogramm to javafx.fxml;
    exports com.example.systemprogramm;

    exports com.example.systemprogramm.controllermodels.file;
    exports com.example.systemprogramm.controllermodels;
    opens com.example.systemprogramm.controllermodels.file to com.google.gson, java.xml.bind;
    exports com.example.systemprogramm.controllermodels.file.record;
    opens com.example.systemprogramm.controllermodels.file.record to com.google.gson, java.xml.bind, org.hibernate.orm.core;
    exports com.example.systemprogramm.viewmodels.windows;
    opens com.example.systemprogramm.viewmodels.windows;

}