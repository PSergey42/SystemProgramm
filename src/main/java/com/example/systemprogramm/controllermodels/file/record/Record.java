package com.example.systemprogramm.controllermodels.file.record;

import java.io.Serializable;
import java.sql.Date;
import java.util.StringTokenizer;

/**
 * Интерфейс записи, который должны реализовать все записи
 * @see RecordCSV
 * @see RecordJSON
 * @see RecordXML
 */
public interface Record extends Serializable, Cloneable {
    Record clone();

    /**
     * Метод, преобразующий дату в виде строки yyyy-MM-dd в java.sql.Date
     * @see java.sql.Date
     * @param s Дата в виде строки yyyy-MM-dd
     * @return java.sql.Date
     */
    static Date parse(String s) {
        Date date;
        if(s.matches("\\d{1,4}-0?\\d-[0-3]?\\d") || s.matches("\\d{1,4}-1[0-2]-[0-3]?\\d")){
            StringTokenizer st = new StringTokenizer(s,"-");
            date = new Date(Integer.parseInt(st.nextToken()) - 1900, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()));
            return date;
        }
        throw new RuntimeException("Строка "+ s + " не соответствует дате");
    }
}
