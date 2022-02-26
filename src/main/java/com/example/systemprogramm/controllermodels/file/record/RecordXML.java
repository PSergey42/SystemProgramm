package com.example.systemprogramm.controllermodels.file.record;
import com.example.systemprogramm.controllermodels.file.record.Record;

import java.util.Date;

public class RecordXML implements Record {
    private String filePath;
    private double mByteFileSize;
    private Date lastEditing;

    private RecordXML(){}

    public RecordXML(String filePath, double mByteFileSize, Date  lastEditing){
        this.filePath = filePath;
        this.mByteFileSize = mByteFileSize;
        this.lastEditing = lastEditing;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public double getMByteFileSize() {
        return mByteFileSize;
    }

    public void setMByteFileSize(double mByteFileSize) {
        this.mByteFileSize = mByteFileSize;;
    }

    public Date getLastEditing() {
        return lastEditing;
    }

    public void setLastEditing(Date lastEditing) {
        this.lastEditing = lastEditing;
    }

    @Override
    public String toString(){
        return String.format("{\"filePath\" : \"%s\", \"mByteFileSize\" : \"%f:\", \"lastEditing\" : \"%s\"}",filePath, mByteFileSize, lastEditing);
    }
}
