package com.dongbingbin.nativeutils.utils

class DelegateArrayList<T>(val innerArray:MutableCollection<T> = ArrayList<T>()):MutableCollection<T> by innerArray{
    override fun add(element: T): Boolean {
        return innerArray.add(element);
    }
}