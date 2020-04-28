package com.dongbingbin.nativeutils.model;

public class Part<T extends Person> {

    private T val;

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }
}
