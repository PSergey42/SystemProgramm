package com.example.systemprogramm.controllermodels.file.record;

import javax.persistence.*;
import java.sql.Date;

/**
 * Класс реализует интерфейс Record
 * Представляет данные в формате JSON
 * @see Record
 */
@Entity
@Table(name = "json")
public class RecordJSON implements Record {
    @Id
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "k_byte_size")
    private int kByteSize;
    @Column(name = "mydate")
    private Date dateOfCreate;

    public RecordJSON() {

    }

    public RecordJSON(String filePath, int kByteSize, Date dateOfCreate) {
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

    @Override
    public String toString() {
        return String.format("filePath = %s, kByteSize = %s, dateOfCreate = %s", filePath, kByteSize, dateOfCreate);
    }

    @Override
    public Record clone() {
        return new RecordJSON(this.filePath, this.kByteSize, (Date) this.dateOfCreate.clone());
    }
}
