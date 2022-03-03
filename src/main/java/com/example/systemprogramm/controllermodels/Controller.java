package com.example.systemprogramm.controllermodels;

import file.record.Record;
import com.example.systemprogramm.viewmodels.View;
import java.util.List;

public interface Controller {
    /**
     * Метод для добавления новой записи
     * @param record новая запись
     */
    void addRecord(Record record);

    /**
     * Метод для удаления записи
     * @param deleteRecord запись,которую надо удалить
     */
    void deleteRecord(Record deleteRecord);

    /**
     * Метод для удаления записи
     * @param index идекс записи, которую надо изменить
     * @param newRecord измененная запись
     */
    void editRecord(Record newRecord, int index);

    /**
     * Метод для получения списка записей
     * @param fileType расширение файла
     */
    List<Record> getRecords(file.FileType fileType);

    /**
     * Метод для получения записи по индексу
     * @param index индекс записи
     * @param fileType расширение файла
     */
    Record getRecord(int index, file.FileType fileType);

    /**
     * Метод, задающий смотрителя для контроллера
     * @param view смотритель, с которым контроллер будет взаимодействовать
     */
    void setView(View view);
}
