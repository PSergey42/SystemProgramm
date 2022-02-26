package com.example.systemprogramm.controllermodels.file.record;

import com.example.systemprogramm.controllermodels.file.MyData;
import com.example.systemprogramm.controllermodels.file.record.Record;

public class RecordCSV implements Record {
    private String address;
    private String accessMode;
    private MyData accessDate;

    public RecordCSV(String address, String accessMode, MyData accessDate){
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

    public MyData getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(MyData accessDate) {
        this.accessDate = accessDate;
    }

    @Override
    public String toString() {
        return '"' + address + '"' + "," + '"' + accessMode+ '"' + "," + accessDate;
    }
}
