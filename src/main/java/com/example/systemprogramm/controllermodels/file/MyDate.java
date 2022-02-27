package com.example.systemprogramm.controllermodels.file;

import com.example.systemprogramm.controllermodels.file.record.Record;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.StringTokenizer;

public class MyDate implements Serializable, Cloneable {
    private int day;
    private int month;
    private int year;

    public MyDate(){
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        month = Calendar.getInstance().get(Calendar.MONTH);
        year = Calendar.getInstance().get(Calendar.YEAR);
    }

    public MyDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @XmlAttribute
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @XmlAttribute
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @XmlAttribute
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public static MyDate parse(String s) throws ParseException {
        MyDate date;
        if (s.matches("[0-3]?\\d\\.0?\\d\\.\\d{1,4}") || s.matches("[0-3]?\\d\\.1[0-2]\\.\\d{1,4}")) {
            StringTokenizer st = new StringTokenizer(s, ".");
            date = new MyDate(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        } else {
            throw new ParseException("dsa", 0);
        }
        return date;
    }

    @Override
    public String toString() {
        return day + "." + month + "." + year;
    }

    @Override
    public MyDate clone() throws CloneNotSupportedException {
        return (MyDate) super.clone();
    }
}
