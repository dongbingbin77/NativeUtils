package com.yjy.testplugin;

import java.util.ArrayList;
import java.util.Map;

public class TestPluginConfig {
    public ArrayList<Map<String, Object>> registerInfo = new ArrayList<>();

    public ArrayList<Map<String, Object>> getRegisterInfo() {
        return registerInfo;
    }

    public void setRegisterInfo(ArrayList<Map<String, Object>> registerInfo) {
        this.registerInfo = registerInfo;
    }
}
