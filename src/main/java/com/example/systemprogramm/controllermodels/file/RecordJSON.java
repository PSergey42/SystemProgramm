package com.example.systemprogramm.controllermodels.file;


import java.util.Date;

public class RecordJSON implements Record {
    private String filePath;
    private int kByteSize;
    private Date dateOfCreate;

    public RecordJSON(String filePath, int kByteSize, Date dateOfCreate){
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

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }
}
