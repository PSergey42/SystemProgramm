package com.example.systemprogramm;

import com.example.systemprogramm.controllermodels.ControllerModel;
import com.example.systemprogramm.controllermodels.analyzer.Analyzer;
import com.example.systemprogramm.controllermodels.file.MyDate;
import com.example.systemprogramm.controllermodels.file.record.RecordModel;
import com.example.systemprogramm.controllermodels.file.record.Record;
import com.example.systemprogramm.controllermodels.file.record.RecordXML;
import com.example.systemprogramm.viewmodels.View;
import com.example.systemprogramm.viewmodels.WindowView;

import javax.xml.bind.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws JAXBException, IOException {

        try {
            //System.out.println(Analyzer.analyze(""));
            //System.out.println(Analyzer.analyze2("int s =14;double ifa = 2.2; int a = s; bool af3 = true; if(true){ } else { s++;}"));

        }
        catch (Throwable e){
           // e.printStackTrace();
            System.err.println(e.getMessage());
        }


       /* View viewer = WindowView.getView();
        viewer.setController(ControllerModel.getInstance(viewer));
        viewer.run();*/
    }
}
