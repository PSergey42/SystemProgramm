package com.example.systemprogramm.controllermodels.file;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.example.systemprogramm.controllermodels.file.record.Record;
import com.example.systemprogramm.controllermodels.file.record.RecordCSV;
import com.example.systemprogramm.controllermodels.file.record.RecordJSON;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class FileUtils implements MyFile {

    private final Type itemsListType = new TypeToken<List<RecordJSON>>() {}.getType();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public ArrayList<Record> getRecord(File myFileName, FileType type) throws IOException {
        ArrayList<Record> recordList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(myFileName));
        String line;
        switch (type) {
            case CSV: {
                CSVReader csvReader = new CSVReader(new FileReader(myFileName), ',');
                String[] records;
                while((records = csvReader.readNext()) != null){
                    MyData myData = new MyData(Integer.parseInt(records[2].substring(0, records[2].indexOf("."))),
                            Integer.parseInt(records[2].substring(records[2].indexOf(".")+1, records[2].lastIndexOf("."))),
                            Integer.parseInt(records[2].substring(records[2].lastIndexOf(".") + 1)));
                    Record record = new RecordCSV(records[0], records[1], myData);
                    recordList.add(record);
                }

                return recordList;
            }
            case JSON: {
                StringBuilder b = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    b.append(line);
                }
                recordList = gson.fromJson(b.toString().trim(), itemsListType);
                return recordList;
            }
            case XML: {
                //Vs
                break;
            }
        }
        return recordList;
    }

    @Override
    public void setRecord(File myFileName, int recordPos, Record newRecord, FileType type) {
        try {
            ArrayList<Record> record = getRecord(myFileName, type);
            switch (type) {
                case CSV: {
                    FileWriter fileWriter = new FileWriter(myFileName);
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    if(record == null) record = new ArrayList<>();
                    record.set(recordPos-1, newRecord);
                    for(int i = 0; i < record.size(); i++){
                        printWriter.println(record.get(i));
                    }
                    printWriter.close();
                    break;
                }
                case JSON: {
                    record.set(recordPos-1,newRecord);
                    PrintWriter printWriter = new PrintWriter(myFileName);
                    printWriter.println(gson.toJson(record));
                    printWriter.close();
                    break;
                }
                case XML: {
                    //Vs
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addRecord(File myFileName, FileType type, Record... newRecord) {
        try {
            switch (type) {
                case CSV: {
                    FileWriter fileWriter = new FileWriter(myFileName, true);
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    ArrayList<Record> record = getRecord(myFileName, type);
                    if(record == null) record = new ArrayList<>();
                    Collections.addAll(record, newRecord);
                    for(int i = 0; i < newRecord.length; i++){
                        printWriter.println(newRecord[i]);
                    }
                    printWriter.close();
                    break;
                }
                case JSON: {
                    ArrayList<Record> record = getRecord(myFileName, type);
                    PrintWriter printWriter = new PrintWriter(myFileName);
                    if(record == null) record = new ArrayList<>();
                    Collections.addAll(record, newRecord);
                    printWriter.println(gson.toJson(record));
                    printWriter.close();
                    break;
                }
                case XML: {
                    //Vs
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRecord(File myFileName, int recordPos, FileType type) {
        try {
            switch (type) {
                case CSV: {
                    ArrayList<Record> record = getRecord(myFileName, type);
                    FileWriter fileWriter = new FileWriter(myFileName);
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    if(record == null) record = new ArrayList<>();
                    record.remove(recordPos-1);
                    for(int i = 0; i < record.size(); i++){
                        printWriter.println(record.get(i));
                    }
                    printWriter.close();
                    break;
                }
                case JSON: {
                    ArrayList<Record> record = getRecord(myFileName, type);
                    record.remove(recordPos-1);
                    PrintWriter printWriter = new PrintWriter(myFileName);
                    printWriter.println(gson.toJson(record));
                    printWriter.close();
                    break;
                }
                case XML: {
                    //Vs
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
