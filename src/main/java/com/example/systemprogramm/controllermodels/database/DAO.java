package com.example.systemprogramm.controllermodels.database;

import file.record.RecordCSV;
import file.record.RecordJSON;
import file.record.RecordXML;
import org.hibernate.Session;
import org.hibernate.Transaction;
import file.record.Record;


import java.util.List;

/**
 * Класс, работающий с базай дынных
 */
public class DAO {
    /**
     * Метод для получения записи по id
     * @param id id записи
     * @param type тип файла
     * @return запись или null в случае провала поиска
     */
    public Record findByAddress(String id, file.FileType type) {
        switch (type) {
            case CSV -> {
                return HibernateUtil.getSessionFactory().openSession().get(RecordCSV.class, id);
            }
            case JSON -> {
                return HibernateUtil.getSessionFactory().openSession().get(RecordJSON.class, id);
            }
            case XML -> {
                return HibernateUtil.getSessionFactory().openSession().get(RecordXML.class, id);
            }
        }
        return null;
    }

    /**
     * Метод для добавления записи в базу данных
     * @param record запись для добавления
     */
    public void add(Record record) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(record);
        tx1.commit();
        session.close();
    }

    /**
     * Метод для замены записи в базе данных
     * @param record Запись для замены
     */
    public void update(Record record) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(record);
        tx1.commit();
        session.close();
    }

    /**
     * Метод для удаления записи
     * @param record запись, которую надо удалить
     */
    public void delete(Record record) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(record);
        tx1.commit();
        session.close();
    }

    /**
     * Метод для получения списка записей
     * @param type тип файла
     * @return список записей
     */
    public List<Record> findAll(file.FileType type) {
        switch (type) {
            case CSV -> {
                return (List<Record>) HibernateUtil.getSessionFactory().openSession().createQuery("From RecordCSV").list();
            }
            case JSON -> {
                return (List<Record>) HibernateUtil.getSessionFactory().openSession().createQuery("From RecordJSON").list();
            }
            case XML -> {
                return (List<Record>) HibernateUtil.getSessionFactory().openSession().createQuery("From RecordXML").list();
            }
        }
        return null;
    }

}
