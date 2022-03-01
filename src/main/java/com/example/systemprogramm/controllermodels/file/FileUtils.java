package com.example.systemprogramm.controllermodels.file;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import au.com.bytecode.opencsv.CSVReader;
import com.example.systemprogramm.controllermodels.file.record.*;
import com.example.systemprogramm.controllermodels.file.record.Record;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Класс для работы с файлами
 */
public class FileUtils implements MyFile {
    /**
     *
     */
    private RecordModel recordModel;
    private final Type itemsListType = new TypeToken<List<RecordJSON>>() {}.getType();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public FileUtils(){
        recordModel = new RecordModel();
    }

    /**
     * Метод для получения записи по индексу
     * @param recordPos индекс записи
     */
    @Override
    public Record getRecord(int recordPos) {
        return recordModel.getRecord(recordPos);
    }

    /**
     * Метод для получения всех записей
     * @return Все записи из recordModel
     */
    @Override
    public List<Record> getRecords() {
        return recordModel.getRecords();
    }

    /**
     * Метод для замены уже существующей записи
     * @param newRecord новая запись
     * @param recordPos индекс для замены
     */
    @Override
    public void setRecord(Record newRecord, int recordPos) {
        recordModel.setRecord(newRecord, recordPos);
    }

    /**
     * Метод для добавления записи в recordModel
     * @param newRecord Запись для добавления
     */
    @Override
    public void addRecord(Record... newRecord) {
        for (Record record : newRecord) {
            recordModel.addRecord(record);
        }
    }

    /**
     * Метод для удаления записи из recordModel по указанному индексу
     * @param recordPos идекс элемента для удаления
     */
    @Override
    public void deleteRecord(int recordPos) {
        recordModel.deleteRecord(recordPos);
    }

    /**
     * Метод для сохранения всех записей из recordModel в файл с указанным расширением
     * @param saveFile путь к файлу
     * @param fileType расширение файла
     */
    @Override
    public void save(File saveFile, FileType fileType){
        try {
            switch (fileType) {
                case CSV -> {
                    List<Record> record = recordModel.getRecords();
                    FileWriter fileWriter = new FileWriter(saveFile);
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    if (record == null) record = new ArrayList<>();
                    for (int i = 0; i < record.size(); i++) {
                        printWriter.println(record.get(i));
                    }
                    printWriter.close();
                }
                case JSON -> {
                    List<Record> record = recordModel.getRecords();
                    PrintWriter printWriter = new PrintWriter(saveFile);
                    printWriter.println(gson.toJson(record));
                    printWriter.close();
                }
                case XML -> {
                    JAXBContext jaxbContext = JAXBContext.newInstance(RecordModel.class);
                    Marshaller marshaller = jaxbContext.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    marshaller.marshal(recordModel, saveFile);
                }
            }

        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для загрузки данных из файла в переменную recordModel
     * @param loadFile путь к файлу
     * @param fileType расширение файла
     */
    @Override
    public void load(File loadFile, FileType fileType) {
        try (BufferedReader br = new BufferedReader(new FileReader(loadFile))) {
            List<Record> recordList = new ArrayList<>();
            switch (fileType) {
                case CSV -> {
                    CSVReader csvReader = new CSVReader(new FileReader(loadFile), ',');
                    String[] records;
                    while ((records = csvReader.readNext()) != null) {
                        Date myDate = new Date(Integer.parseInt(records[2].substring(0,records[2].indexOf("-"))) - 1900,
                                Integer.parseInt(records[2].substring(records[2].indexOf("-") + 1, records[2].lastIndexOf("-"))) - 1,
                                Integer.parseInt(records[2].substring(records[2].lastIndexOf("-")+1)));
                        Record record = new RecordCSV(records[0], records[1], myDate);
                        recordList.add(record);
                    }
                    recordModel.setRecords(recordList);
                }
                case JSON -> {
                    String line;
                    StringBuilder b = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        b.append(line);
                    }
                    recordList = gson.fromJson(b.toString().trim(), itemsListType);
                    if(recordList == null) recordList = new ArrayList<>();
                    recordModel.setRecords(recordList);
                }
                case XML -> {
                    if(loadFile.length() == 0){
                        PrintWriter pw = new PrintWriter(loadFile);
                        pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
                        pw.println("<records xmlns:ns2=\"XML\">");
                        pw.println("</records>");
                        pw.close();
                    }
                    JAXBContext jaxbContext = JAXBContext.newInstance(RecordModel.class);
                    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                    recordModel = (RecordModel) unmarshaller.unmarshal(loadFile);
                }
            }

        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }
}
