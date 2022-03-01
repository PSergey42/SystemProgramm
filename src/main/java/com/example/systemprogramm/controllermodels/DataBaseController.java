package com.example.systemprogramm.controllermodels;

import com.example.systemprogramm.controllermodels.database.DAO;
import com.example.systemprogramm.controllermodels.file.FileType;
import com.example.systemprogramm.controllermodels.file.record.Record;
import com.example.systemprogramm.viewmodels.View;

import java.util.List;

public class DataBaseController implements Controller {

    private View view;
    private static DAO dao;

    public DataBaseController() {
        dao = new DAO();
    }

    @Override
    public void addRecord(Record record) {
        try {
            dao.add(record);
        } catch (RuntimeException e){
            dao.update(record);
        }
    }

    @Override
    public void deleteRecord(Record record) {
        dao.delete(record);
    }

    @Override
    public void editRecord(Record newRecord, int index) {
        dao.update(newRecord);
    }

    @Override
    public List<Record> getRecords() {
        return null;
    }

    @Override
    public Record getRecord(int index, FileType fileType) {
        return getRecords(fileType).get(index);
    }

    public Record getRecord(String id, FileType fileType) {
        return dao.findByAddress(id, fileType);
    }

    public List<Record> getRecords(FileType fileType) {
        return dao.findAll(fileType);
    }
}
