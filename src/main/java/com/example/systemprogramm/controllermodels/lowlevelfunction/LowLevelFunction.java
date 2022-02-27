package com.example.systemprogramm.controllermodels.lowlevelfunction;

public class LowLevelFunction {
    static{
        System.loadLibrary("LowLevelFunction");
    }
    public native static int div(int x, int y);    // импортируемая функция sum

    public native static int addWithOverflowCheck(int x, int y);

    public static void main(String[] args) {
        System.out.println(div(100, 3));      // вызываем функцию
        System.out.println(addWithOverflowCheck(2147483645,3));
    }
}
