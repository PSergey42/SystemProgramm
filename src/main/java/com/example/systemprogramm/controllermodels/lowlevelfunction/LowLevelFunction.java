package com.example.systemprogramm.controllermodels.lowlevelfunction;

public class LowLevelFunction {
    static{
        System.loadLibrary("LowLevelFunction");
    }
    public native static int div(int x, int y);

    public native static int addWithOverflowCheck(int x, int y);

}
