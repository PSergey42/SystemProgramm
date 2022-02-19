package com.example.systemprogramm.controllermodels.file;

import java.util.Date;

public class RecordXML implements Record {
    private String filePath;
    private int mByteFileSize;
    private Date lastEditing;

    public RecordXML(String filePath, int mByteFileSize, Date  lastEditing){
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

    public Date getLastEditing() {
        return lastEditing;
    }

    public void setLastEditing(Date lastEditing) {
        this.lastEditing = lastEditing;
    }
}
