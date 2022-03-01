package com.example.systemprogramm.controllermodels;

import com.example.systemprogramm.controllermodels.file.FileType;
import com.example.systemprogramm.controllermodels.file.record.Record;

import java.io.File;
import java.util.List;

public interface Controller {
    void addRecord(Record record);
    void deleteRecord(Record record);
    void editRecord(Record newRecord, int index);
    List<Record> getRecords();
    Record getRecord(int index, FileType fileType);
}
