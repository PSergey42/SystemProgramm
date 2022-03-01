package com.example.systemprogramm.controllermodels.lowlevelfunction;

/**
 *Класс для работы с функциями на ассемблере
 */
public class LowLevelFunction {

    static{
        System.loadLibrary("LowLevelFunction");
    }

    /**
     * Метод для деления дву целых положительных чисел
     * @param x целое число
     * @param y целое число
     * @return результат деления
     */
    public native static int div(int x, int y);

    /**
     * Метод сложения дву целых положительных чисел
     * В случае переполнения выдает 0
     * @param x целое число
     * @param y целое число
     * @return результат сложения
     */
    public native static int addWithOverflowCheck(int x, int y);

}
