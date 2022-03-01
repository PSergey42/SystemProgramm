package com.example.systemprogramm;

import com.example.systemprogramm.controllermodels.ControllerModel;
import com.example.systemprogramm.controllermodels.DataBaseController;
import com.example.systemprogramm.controllermodels.database.DAO;
import com.example.systemprogramm.controllermodels.file.FileType;
import com.example.systemprogramm.controllermodels.file.record.RecordJSON;
import com.example.systemprogramm.viewmodels.View;
import com.example.systemprogramm.viewmodels.WindowView;

import javax.xml.bind.*;
import java.io.IOException;
import java.sql.Date;

public class Main {

    public static void main(String[] args) throws JAXBException, IOException {

        try {
            //System.out.println(Analyzer.analyze(""));
            //System.out.println(Analyzer.analyze2("int s =14;double ifa = 2.2; int a = s; bool af3 = true; if(true){ } else { s++;}"));
            /*DAO d = new DAO();
            //RecordXML user = new RecordXML("Masha",6,new MyDate(1,1,1));
            RecordJSON user = new RecordJSON("11", 322, new Date(11,1,1));
           // d.save(user);
            d.update(user);*/
        }
        catch (Throwable e){
           // e.printStackTrace();
            System.err.println(e.getMessage());
        }

        View viewer = WindowView.getView();
        viewer.setController(ControllerModel.getInstance(viewer));
        ((WindowView) viewer).setDataBaseController(new DataBaseController());
        viewer.run();
    }
}
