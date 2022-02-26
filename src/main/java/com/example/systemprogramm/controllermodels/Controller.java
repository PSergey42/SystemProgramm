package com.example.systemprogramm.controllermodels;

import com.example.systemprogramm.controllermodels.file.record.Record;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.List;

public interface Controller {
    void addRecord(Record record);
    void deleteRecord(int index);
    void editRecord(Record newRecord, int index);
    List<Record> getRecords();
    void saveJSONAs(File saveFile) throws JAXBException;
    void saveCSVAs(File saveFile) throws JAXBException;
    void saveXMLAs(File saveFile) throws JAXBException;
    void loadJSON(File loadFile) throws JAXBException;
    void loadCSV(File loadFile) throws JAXBException;
    void loadXML(File loadFile) throws JAXBException;
}
