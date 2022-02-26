package com.example.systemprogramm.controllermodels.file;

import com.example.systemprogramm.controllermodels.file.record.Record;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface MyFile {
    List<Record> getRecord(File myFileName, FileType type) throws IOException;
    void setRecord(File myFileName, int recordPos, Record newRecord, FileType type);
    void addRecord(File myFileName, FileType type, Record ... newRecord);
    void deleteRecord(File myFileName, int recordPos, FileType type);
}
