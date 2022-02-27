package com.example.systemprogramm.controllermodels.file.record;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;
import java.util.StringTokenizer;

public interface Record extends Serializable, Cloneable {
    Record clone();

    static Date parse(String s) {
        Date date = null;
        if(s.matches("\\d{1,4}-0?\\d-[0-3]?\\d") || s.matches("\\d{1,4}-1[0-2]-[0-3]?\\d")){
            StringTokenizer st = new StringTokenizer(s,"-");
            date = new Date(Integer.parseInt(st.nextToken()) - 1900, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()));
            return date;
        }
        throw new RuntimeException("Строка "+ s + " не соответствует дате");
    }
}
