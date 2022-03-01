package com.example.systemprogramm.controllermodels.file;

import com.example.systemprogramm.controllermodels.file.record.Record;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface MyFile {
    /**
     * Метод для получения записи по индексу
     * @param recordPos индекс записи
     */
    Record getRecord(int recordPos);

    /**
     * Метод для получения всех записей
     * @return Все записи из recordModel
     */
    List<Record> getRecords();

    /**
     * Метод для замены уже существующей записи
     * @param newRecord новая запись
     * @param recordPos индекс для замены
     */
    void setRecord(Record newRecord, int recordPos);

    /**
     * Метод для добавления записи в recordModel
     * @param newRecord Запись для добавления
     */
    void addRecord(Record... newRecord);

    /**
     * Метод для удаления записи из recordModel по указанному индексу
     * @param recordPos идекс элемента для удаления
     */
    void deleteRecord(int recordPos);

    /**
     * Метод для сохранения всех записей из recordModel в файл с указанным расширением
     * @param saveFile путь к файлу
     * @param fileType расширение файла
     */
    void save(File saveFile, FileType fileType);

    /**
     * Метод для загрузки данных из файла в переменную recordModel
     * @param loadFile путь к файлу
     * @param fileType расширение файла
     */
    void load(File loadFile, FileType fileType);
}
