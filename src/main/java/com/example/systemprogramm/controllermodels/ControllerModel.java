package com.example.systemprogramm.controllermodels;

import com.example.systemprogramm.controllermodels.file.record.Record;
import com.example.systemprogramm.controllermodels.file.record.RecordModel;
import com.example.systemprogramm.viewmodels.View;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class ControllerModel implements Controller {
    private RecordModel recordModel;
    private View view;
    private static ControllerModel instance;

    private ControllerModel() {
    }

    public static ControllerModel getInstance(View view) {
        if (instance != null) return instance;
        instance = new ControllerModel();
        instance.recordModel = new RecordModel();
        instance.view = view;
        view.setController(instance);
        return instance;
    }

    public static ControllerModel getInstance(){
        return instance;
    }

    public void addRecord(Record record){
        recordModel.addRecord(record);
    }

    public void deleteRecord(int index){
        recordModel.deleteRecord(index);
    }

    public void editRecord(Record newRecord, int index){
        recordModel.setRecord(newRecord, index);
    }

    @Override
    public List<Record> getRecords() {
        return recordModel.getRecords();
    }

    public void saveJSONAs(File saveFile) {

    }

    public void saveCSVAs(File saveFile) {

    }

    public void saveXMLAs(File saveFile) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(RecordModel.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(recordModel, saveFile);
    }

    public void loadJSON(File loadFile) {

    }

    public void loadCSV(File loadFile) {

    }

    public void loadXML(File loadFile) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(RecordModel.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        recordModel = (RecordModel) unmarshaller.unmarshal(loadFile);
    }
}
