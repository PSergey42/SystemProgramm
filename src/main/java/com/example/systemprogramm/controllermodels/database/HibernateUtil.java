package com.example.systemprogramm.controllermodels.database;

import com.example.systemprogramm.controllermodels.file.record.Record;
import com.example.systemprogramm.controllermodels.file.record.RecordCSV;
import com.example.systemprogramm.controllermodels.file.record.RecordJSON;
import com.example.systemprogramm.controllermodels.file.record.RecordXML;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public final class HibernateUtil {
    private static SessionFactory sessionFactory;

    private HibernateUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(RecordCSV.class);
                configuration.addAnnotatedClass(RecordJSON.class);
                configuration.addAnnotatedClass(RecordXML.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Исключение!" + e);
            }
        }
        System.out.println(sessionFactory == null);
        return sessionFactory;
    }
}
