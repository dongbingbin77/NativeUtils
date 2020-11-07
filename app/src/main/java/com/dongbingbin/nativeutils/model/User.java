package com.dongbingbin.nativeutils.model;

import com.dongbingbin.nativeutils.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class User extends BaseObservable {
    private String name;

    private String sex;
    @Bindable
    public String getName() {
        return name;
    }
    @Bindable
    public String getSex() {
        return sex;
    }
    @Bindable
    public void setName(String name) {
        notifyPropertyChanged(BR.name);
        this.name = name;
    }
    @Bindable
    public void setSex(String sex) {
        this.sex = sex;
    }
}