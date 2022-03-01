package com.example.systemprogramm.controllermodels.lowlevelfunction;

/**
 *Класс для работы с функциями на ассемблере
 */
public class LowLevelFunction {
    static{
        System.loadLibrary("LowLevelFunction");
    }
    public native static int div(int x, int y);

    public native static int addWithOverflowCheck(int x, int y);

}
