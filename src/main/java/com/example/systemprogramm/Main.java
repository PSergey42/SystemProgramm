package com.example.systemprogramm;

import com.example.systemprogramm.viewmodels.View;
import com.example.systemprogramm.viewmodels.WindowView;

public class Main {
    public static void main(String[] args) {
        View view  = WindowView.getView();
        view.run();
    }
}
