package com.example.systemprogramm.controllermodels.lowlevelfunction;

public class LowLevelFunction {
    public native static int div(int x, int y);    // импортируемая функция sum

    public native static int addWithOverflowCheck(int x, int y);

    public static void main(String[] args) {
        System.loadLibrary("LowLevelFunction");        // загружаем библиотеку mydll.dll
        System.out.println(div(2, 3));      // вызываем функцию
    }
}
