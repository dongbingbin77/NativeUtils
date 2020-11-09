package com.dongbingbin.nativeutils.model;

import com.dongbingbin.nativeutils.BR;

import javax.inject.Inject;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class  User extends BaseObservable {
    @Inject
    public User() {
    }

    private String name;

    private String sex;

    @Inject public PersonK personK;

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