package com.example.systemprogramm.controllermodels;

import com.example.systemprogramm.controllermodels.file.FileType;
import com.example.systemprogramm.controllermodels.file.record.Record;

import java.io.File;
import java.util.List;

public interface Controller {
    void addRecord(Record record);
    void deleteRecord(int index);
    void editRecord(Record newRecord, int index);
    List<Record> getRecords();
    void save(File saveFile, FileType fileType);
    void load(File loadFile, FileType fileType);
    String analyzeIf(String s);
    boolean analyzeWhile(String s);
}
