package com.example.systemprogramm.controllermodels.file.record;

import com.example.systemprogramm.controllermodels.file.MyDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@Entity
@Table(name = "xml")
@XmlRootElement(name = "record")
@XmlType(propOrder = {"lastEditing", "MByteFileSize", "filePath"})
public class RecordXML implements Record {
    @Id
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "m_byte_file_path")
    private double mByteFileSize;
    @Column(name = "mydate")
    private MyDate lastEditing;

    public RecordXML() {
    }

    public RecordXML(String filePath, double mByteFileSize, MyDate lastEditing) {
        this.filePath = filePath;
        this.mByteFileSize = mByteFileSize;
        this.lastEditing = lastEditing;
    }

    @XmlAttribute
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @XmlAttribute
    public double getMByteFileSize() {
        return mByteFileSize;
    }

    public void setMByteFileSize(double mByteFileSize) {
        this.mByteFileSize = mByteFileSize;
    }

    public MyDate getLastEditing() {
        return lastEditing;
    }

    public void setLastEditing(MyDate lastEditing) {
        this.lastEditing = lastEditing;
    }

    @Override
    public String toString() {
        return String.format("{\"filePath\" : \"%s\", \"mByteFileSize\" : \"%f:\", \"lastEditing\" : \"%s\"}", filePath, mByteFileSize, lastEditing);
    }

    @Override
    public Record clone() throws CloneNotSupportedException {
        return new RecordXML(this.filePath, this.mByteFileSize, this.lastEditing.clone());
    }
}
