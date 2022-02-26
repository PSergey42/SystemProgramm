package com.example.systemprogramm.viewmodels;

import com.example.systemprogramm.controllermodels.Controller;

public interface View {
    void run();
    void update();
    void setController(Controller controller);
    static View getView() {
        return null;
    }
}
