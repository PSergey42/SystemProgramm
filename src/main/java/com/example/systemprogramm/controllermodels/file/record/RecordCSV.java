package com.example.systemprogramm.controllermodels.file.record;

import com.example.systemprogramm.controllermodels.file.MyDate;

public class RecordCSV implements Record {
    private String address;
    private String accessMode;
    private MyDate accessDate;

    public RecordCSV(String address, String accessMode, MyDate accessDate){
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

    public MyDate getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(MyDate accessDate) {
        this.accessDate = accessDate;
    }

    @Override
    public String toString() {
        return '"' + address + '"' + "," + '"' + accessMode+ '"' + "," + accessDate;
    }

    @Override
    public Record clone() throws CloneNotSupportedException {
        return new RecordCSV(this.address, this.accessMode, this.accessDate.clone());
    }
}
