package com.example.systemprogramm.controllermodels.file;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "records")
public class RecordModel {
    private List<Record> records;

    public RecordModel(){
        records = new ArrayList<>();
    }

    @XmlElements(value = {
            @XmlElement(name = "record", type = RecordXML.class, namespace = "XML"),
            @XmlElement(name = "record", type = RecordCSV.class, namespace = "CSV"),
            @XmlElement(name = "record", type = RecordJSON.class, namespace = "JSON")
    })
    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public void addRecord(Record record){
        records.add(record);
    }

    public void deleteRecord(int index){
        records.remove(index);
    }

    public Record getRecord(int index){
        return records.get(index);
    }

    public void setRecord(Record newRecord, int index){
        records.set(index, newRecord);
    }


}
