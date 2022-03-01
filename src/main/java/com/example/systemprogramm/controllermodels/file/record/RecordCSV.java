package com.example.systemprogramm.controllermodels.file.record;

import javax.persistence.*;
import java.sql.Date;

/**
 * Класс реализует интерфейс Record
 * Представляет данные в формате CSV
 * @see Record
 */
@Entity
@Table (name = "mycsv")
public class RecordCSV implements Record {

    @Id
    private String address;
    @Column(name = "accessmode")
    private String accessMode;
    @Column(name = "mydate")
    private Date accessDate;

    public RecordCSV(){
    }

    public RecordCSV(String address, String accessMode, Date accessDate){
        this.address= address;
        this.accessMode = accessMode;
        this.accessDate = accessDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(String accessMode) {
        this.accessMode = accessMode;
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    @Override
    public String toString() {
        return '"' + address + '"' + "," + '"' + accessMode+ '"' + "," + accessDate;
    }

    @Override
    public Record clone() {
        return new RecordCSV(this.address, this.accessMode, (Date) this.accessDate.clone());
    }
}
