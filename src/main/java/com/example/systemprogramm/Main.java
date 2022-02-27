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

        /*Calendar re = Calendar.getInstance();
        re.set(1,1,1);
        FileUtils f = new FileUtils();
        RecordJSON[] r = {new RecordJSON("a",228, re),new RecordJSON("b",229, re)};
        f.addRecord(new File("test.json"), FileType.JSON,r);
        RecordCSV[] csv = {new RecordCSV("a","228", new MyData(1,12,2001)),new RecordCSV("b","229", new MyData(1,12,2022))};
        f.addRecord(new File("test1.csv"),FileType.CSV,  csv);
        f.deleteRecord(new File("test1.csv"),1, FileType.CSV);
        f.setRecord(new File("test1.csv"),2, new RecordCSV("a","228", new MyData(1,10,2771)) , FileType.CSV);
        f.deleteRecord(new File("test.json"),1 ,FileType.JSON);
        f.setRecord(new File("test.json"),2, new RecordJSON("babulya",1111111, re) , FileType.JSON);*/

        /*List<Record> records = new ArrayList<>();
        records.add(new RecordXML("32131", 1245, new MyDate(1,2,3)));
        records.add(new RecordXML("32131", 1245, new MyDate(1,2,3)));
        records.add(new RecordXML("32131", 1245, new MyDate(1,2,3)));
        RecordModel recordModel = new RecordModel(records);

        JAXBContext jaxbContext = JAXBContext.newInstance(RecordModel.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(recordModel, new File("test2.xml"));
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        System.out.println(((RecordModel)unmarshaller.unmarshal(new File("test2.xml"))).getRecords());*/
        try {
            System.out.println(Analyzer.analyze(""));
            //System.out.println(Analyzer.analyze2("int s =14;double ifa = 2.2; int a = s; bool af3 = true; if(true){ } else { s++;}"));

        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }


       /* View viewer = WindowView.getView();
        viewer.setController(ControllerModel.getInstance(viewer));
        viewer.run();*/
    }
}
