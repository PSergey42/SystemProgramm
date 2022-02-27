package com.example.systemprogramm.controllermodels.file.record;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    private Date lastEditing;

    public RecordXML() {
    }

    public RecordXML(String filePath, double mByteFileSize, Date lastEditing) {
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

    @XmlAttribute
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getLastEditing() {
        return lastEditing;
    }

    public void setLastEditing(Date lastEditing) {
        this.lastEditing = lastEditing;
    }

    @Override
    public String toString() {
        return String.format("{\"filePath\" : \"%s\", \"mByteFileSize\" : \"%f:\", \"lastEditing\" : \"%s\"}", filePath, mByteFileSize, lastEditing);
    }

    @Override
    public Record clone() {
        return new RecordXML(this.filePath, this.mByteFileSize, (Date) this.lastEditing.clone());
    }

    static class DateAdapter extends XmlAdapter<String, Date>{
        private static final String CUSTOM_FORMAT_STRING = "yyyy-MM-dd";

        @Override
        public String marshal(Date v) {
            return new SimpleDateFormat(CUSTOM_FORMAT_STRING).format(v);
        }

        @Override
        public Date unmarshal(String v) throws ParseException {
            return new Date(new SimpleDateFormat(CUSTOM_FORMAT_STRING).parse(v).getTime());
        }
    }
}
