package com.example.systemprogramm.controllermodels.file.record;

import com.example.systemprogramm.controllermodels.file.MyDate;
import com.example.systemprogramm.controllermodels.file.record.Record;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "json")
public class RecordJSON implements Record {
    @Id
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "k_byte_size")
    private int kByteSize;
    @Column(name = "mydate")
    private Date hi;
    @Transient
    private MyDate dateOfCreate;

    public RecordJSON() {

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

    @Override
    public String toString() {
        return String.format("filePath = %s, kByteSize = %s, dateOfCreate = %s", filePath, kByteSize, dateOfCreate);
    }

    @Override
    public Record clone() {
        return new RecordJSON(this.filePath, this.kByteSize, (Date) this.dateOfCreate.clone());
    }
}
