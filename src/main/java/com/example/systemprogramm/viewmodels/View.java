package com.example.systemprogramm.viewmodels;

import com.example.systemprogramm.controllermodels.Controller;

/**
 * Интерфейс, реализующий смотрителя(view) в паттерне MVC
 */
public interface View {
    /**
     * Метод, запускающий смотрителя(view)
     */
    void run();

    /**
     * Метод, обновляющий данные на актуальные
     */
    void update();

    /**
     * Метод, задающий контроллер для взаимодействия со смотрителем
     * @param controller контроллер, который взаимодействует со смотрителем
     */
    void setController(Controller controller);

    /**
     * Метод, возвращающий смотрителя для контроллера
     * @return возвращает null по умолчанию
     */
    static View getView() {
        return null;
    }
}
