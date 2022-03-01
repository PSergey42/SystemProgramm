package com.example.systemprogramm.controllermodels.file.record;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс модели для взаимодействия с классами, реализующими интерфейс Record
 * Реализует модель в паттерне MVC
 * @see Record
 */
@XmlRootElement(name = "records")
public class RecordModel {
    private List<Record> records;

    public RecordModel() {
        records = new ArrayList<>();
    }

    public RecordModel(List<Record> recordList) {
        records = recordList;
    }

    @XmlElement(name = "record", type = RecordXML.class, namespace = "XML")
    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public void addRecord(Record record) {
        records.add(record);
    }

    public void deleteRecord(int index) {
        records.remove(index);
    }

    public Record getRecord(int index) {
        return records.get(index);
    }

    public void setRecord(Record newRecord, int index) {
        records.set(index, newRecord);
    }
}
