package com.example.systemprogramm.controllermodels.file;

import com.example.systemprogramm.controllermodels.file.record.Record;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface MyFile {
    Record getRecord(int recordPos);
    List<Record> getRecords();
    void setRecord(Record newRecord, int recordPos);
    void addRecord(Record... newRecord);
    void deleteRecord(int recordPos);
    void save(File saveFile, FileType fileType);
    void load(File loadFile, FileType fileType);
}
