package com.example.systemprogramm;

import com.example.systemprogramm.controllermodels.ControllerModel;
import com.example.systemprogramm.controllermodels.analyzer.Analyzer;
import com.example.systemprogramm.controllermodels.file.*;
import com.example.systemprogramm.viewmodels.View;
import com.example.systemprogramm.viewmodels.WindowView;
import au.com.bytecode.opencsv.CSVWriter;

import javax.xml.bind.*;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class Main {
    public static void main(String[] args) throws JAXBException, IOException {
        try {
            //System.out.println(Analyzer.analyze("int s =14;double whilea = 2.2; int a = s; bool af3 = true ;while(2==2){}"));
            System.out.println(Analyzer.analyze2("int s =14;double ifa = 2.2; int a = s; bool af3 = true; if(true){    } else { s++;}"));

        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        /*View viewer = WindowView.getView();
        viewer.setController(ControllerModel.getInstance(viewer));
        viewer.run();*/
    }
}
