package com.example.systemprogramm.controllermodels;

import com.example.systemprogramm.controllermodels.analyzer.Analyzer;
import com.example.systemprogramm.controllermodels.file.FileType;
import com.example.systemprogramm.controllermodels.file.FileUtils;
import com.example.systemprogramm.controllermodels.file.record.Record;
import com.example.systemprogramm.viewmodels.View;

import java.io.File;
import java.util.List;

public class ControllerModel implements Controller {
    private FileUtils fileUtils;
    private View view;
    private static ControllerModel instance;

    private ControllerModel() {
    }

    public static ControllerModel getInstance(View view) {
        if (instance != null) return instance;
        instance = new ControllerModel();
        instance.fileUtils = new FileUtils();
        instance.view = view;
        view.setController(instance);
        return instance;
    }

    public static ControllerModel getInstance() {
        return instance;
    }

    @Override
    public void addRecord(Record record) {
        fileUtils.addRecord(record);
    }

    @Override
    public void deleteRecord(Record deleteRecord) {
        int index = 0;
        while(!fileUtils.getRecord(index).equals(deleteRecord)){
            index++;
        }
        fileUtils.deleteRecord(index);
    }

    @Override
    public void editRecord(Record newRecord, int index) {
        fileUtils.setRecord(newRecord, index);
    }

    @Override
    public List<Record> getRecords() {
        return fileUtils.getRecords();
    }

    @Override
    public Record getRecord(int index, FileType fileType) {
        return fileUtils.getRecord(index);
    }

    public void save(File saveFile, FileType fileType) {
        fileUtils.save(saveFile, fileType);
    }

    public void load(File loadFile, FileType fileType) {
        fileUtils.load(loadFile, fileType);
    }

    public String analyzeIf(String s) {
        return Analyzer.analyze2(s);
    }

    public boolean analyzeWhile(String s) {
        return Analyzer.analyze(s);
    }
}
