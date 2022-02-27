package com.example.systemprogramm.controllermodels.file.record;

import com.example.systemprogramm.controllermodels.file.MyDate;
import com.example.systemprogramm.controllermodels.file.record.Record;

import java.util.Calendar;

public class RecordJSON implements Record {
    private String filePath;
    private int kByteSize;
    private MyDate dateOfCreate;

    public RecordJSON(){

    }

    public RecordJSON(String filePath, int kByteSize, MyDate dateOfCreate){
        this.filePath = filePath;
        this.kByteSize = kByteSize;
        this.dateOfCreate = dateOfCreate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getKByteSize() {
        return kByteSize;
    }

    public void setKByteSize(int kByteSize) {
        this.kByteSize = kByteSize;
    }

    public MyDate getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(MyDate dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }
}
