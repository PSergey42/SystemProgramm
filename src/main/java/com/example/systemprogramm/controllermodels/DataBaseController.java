package com.example.systemprogramm.controllermodels;

import com.example.systemprogramm.controllermodels.database.DAO;
import file.record.Record;
import com.example.systemprogramm.viewmodels.View;

import java.util.List;

/**
 * Класс-контроллер для взаимодействия с базой данных,
 * которая хранит данные, реализующие интерфейс Record
 * @see Controller
 * @see Record
 */
public class DataBaseController implements Controller {

    private View view;
    private static DAO dao;

    public DataBaseController() {
        dao = new DAO();
    }
    public DataBaseController(View view) {
        dao = new DAO();
        this.view = view;
    }

    public void setView(View view){
        this.view = view;
    }

    @Override
    public void addRecord(Record record) {
        try {
            dao.add(record);
            view.update();
        } catch (RuntimeException e) {
            dao.update(record);
            view.update();
        }
    }

    @Override
    public void deleteRecord(Record record) {
        dao.delete(record);
        view.update();
    }

    @Override
    public void editRecord(Record newRecord, int index) {
        dao.update(newRecord);
        view.update();
    }

    @Override
    public List<Record> getRecords(file.FileType fileType) {
        return dao.findAll(fileType);
    }

    @Override
    public Record getRecord(int index, file.FileType fileType) {
        return getRecords(fileType).get(index);
    }

    public Record getRecord(String id, file.FileType fileType) {
        return dao.findByAddress(id, fileType);
    }

}
