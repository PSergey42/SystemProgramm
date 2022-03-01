package com.example.systemprogramm;

import com.example.systemprogramm.controllermodels.ControllerModel;
import com.example.systemprogramm.controllermodels.DataBaseController;
import com.example.systemprogramm.controllermodels.database.DAO;
import com.example.systemprogramm.controllermodels.file.FileType;
import com.example.systemprogramm.controllermodels.file.record.RecordJSON;
import com.example.systemprogramm.viewmodels.View;
import com.example.systemprogramm.viewmodels.WindowView;

import java.sql.Date;
import java.util.Calendar;

public class Main {

    public static void main(String[] args) {
        View viewer = WindowView.getView();
        viewer.setController(ControllerModel.getInstance(viewer));
        ((WindowView) viewer).setDataBaseController(new DataBaseController());
        viewer.run();
    }
}
