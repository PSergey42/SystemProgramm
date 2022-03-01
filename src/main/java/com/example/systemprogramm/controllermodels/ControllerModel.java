package com.example.systemprogramm.controllermodels;

import com.example.systemprogramm.controllermodels.analyzer.Analyzer;
import com.example.systemprogramm.controllermodels.file.FileType;
import com.example.systemprogramm.controllermodels.file.FileUtils;
import com.example.systemprogramm.controllermodels.file.record.Record;
import com.example.systemprogramm.viewmodels.View;

import java.io.File;
import java.util.List;

/**
 * Класс-контроллер для взаимодействия с моделями, хранящимися в файле
 * Также
 * Реализует контроллер в паттерне MVC
 * @see Controller
 */
public class ControllerModel implements Controller {
    private FileUtils fileUtils;
    private View view;
    private static ControllerModel instance;

    private ControllerModel() {}

    public static ControllerModel getInstance(View view) {
        if (instance != null) return instance;
        instance = new ControllerModel();
        instance.fileUtils = new FileUtils();
        instance.view = view;
        view.setController(instance);
        return instance;
    }

    public void setView(View view){
        this.view = view;
    }

    @Override
    public void addRecord(Record record) {
        fileUtils.addRecord(record);
        view.update();
    }

    @Override
    public void deleteRecord(Record deleteRecord) {
        int index = 0;
        while(!fileUtils.getRecord(index).equals(deleteRecord)){
            index++;
        }
        fileUtils.deleteRecord(index);
        view.update();
    }

    @Override
    public void editRecord(Record newRecord, int index) {
        fileUtils.setRecord(newRecord, index);
        view.update();
    }

    @Override
    public List<Record> getRecords(FileType fileType) {
        return fileUtils.getRecords();
    }

    @Override
    public Record getRecord(int index, FileType fileType) {
        return fileUtils.getRecord(index);
    }

    public void save(File saveFile, FileType fileType) {
        fileUtils.save(saveFile, fileType);
    }

    /**
     * Метод для загрузки файла
     * @param loadFile файл, который надо загрузить
     * @param fileType тип файла
     */
    public void load(File loadFile, FileType fileType) {
        fileUtils.load(loadFile, fileType);
        view.update();
    }

    public String analyzeIf(String s) {
        return Analyzer.analyze2(s);
    }

    public boolean analyzeWhile(String s) {
        return Analyzer.analyze(s);
    }
}
