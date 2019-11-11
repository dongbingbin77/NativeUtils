package com.dongbingbin;

import android.app.Application;

import com.fm.openinstall.OpenInstall;

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OpenInstall.init(this);
    }
}
