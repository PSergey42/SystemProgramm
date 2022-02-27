package com.example.systemprogramm.controllermodels;

import com.example.systemprogramm.controllermodels.analyzer.Analyzer;
import com.example.systemprogramm.controllermodels.file.FileType;
import com.example.systemprogramm.controllermodels.file.FileUtils;
import com.example.systemprogramm.controllermodels.file.record.Record;
import com.example.systemprogramm.controllermodels.file.record.RecordModel;
import com.example.systemprogramm.controllermodels.lowlevelfunction.LowLevelFunction;
import com.example.systemprogramm.viewmodels.View;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class ControllerModel implements Controller {
    private FileUtils fileUtils;
    private Analyzer analyzer;
    //private LowLevelFunction lowLevelFunction;
    private View view;
    private static ControllerModel instance;

    private ControllerModel() {
    }

    public static ControllerModel getInstance(View view) {
        if (instance != null) return instance;
        instance = new ControllerModel();
        instance.fileUtils = new FileUtils();
        instance.analyzer = new Analyzer();
        instance.view = view;
        view.setController(instance);
        return instance;
    }

    public static ControllerModel getInstance(){
        return instance;
    }

    public void addRecord(Record record){
        fileUtils.addRecord(record);
    }

    public void deleteRecord(int index){
        fileUtils.deleteRecord(index);
    }

    public void editRecord(Record newRecord, int index){
        fileUtils.setRecord(newRecord, index);
    }

    @Override
    public List<Record> getRecords() {
        return fileUtils.getRecords();
    }

    @Override
    public void save(File saveFile, FileType fileType) {
        fileUtils.save(saveFile, fileType);
    }

    @Override
    public void load(File loadFile, FileType fileType) {
        fileUtils.load(loadFile, fileType);
    }

    @Override
    public String analyzeIf(String s){
        return Analyzer.analyze2(s);
    }
    public boolean analyzeWhile(String s){
        return Analyzer.analyze(s);
    }
}
