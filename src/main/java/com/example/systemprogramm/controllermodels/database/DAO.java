package com.example.systemprogramm.controllermodels.database;

import com.example.systemprogramm.controllermodels.file.FileType;
import com.example.systemprogramm.controllermodels.file.record.RecordCSV;
import com.example.systemprogramm.controllermodels.file.record.RecordJSON;
import com.example.systemprogramm.controllermodels.file.record.RecordXML;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.example.systemprogramm.controllermodels.file.record.Record;


import java.util.List;

public class DAO {

    public Record findByAddress(String id, FileType type) {
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

    public void save(Record record) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(record);
        tx1.commit();
        session.close();
    }

    public void update(Record record) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(record);
        tx1.commit();
        session.close();
    }

    public void delete(Record record) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(record);
        tx1.commit();
        session.close();
    }

    public List<Record> findAll(FileType type) {
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
