package com.example.systemprogramm.controllermodels.file;
import java.util.Calendar;

import java.util.Date;

public class RecordXML implements Record {
    private String filePath;
    private int mByteFileSize;
    private Calendar lastEditing;

    public RecordXML(String filePath, int mByteFileSize, Calendar  lastEditing){
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

    public int getFileSize() {
        return mByteFileSize;
    }

    public void setFileSize(int mByteFileSize) {
        this.mByteFileSize = mByteFileSize;
    }

    public Calendar getLastEditing() {
        return lastEditing;
    }

    public void setLastEditing(Calendar lastEditing) {
        this.lastEditing = lastEditing;
    }
}
