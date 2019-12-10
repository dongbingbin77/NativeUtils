package com.dongbingbin.nativeutils.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun selectType(content:String,phone:(x:String,y:String)->Unit,email:()->Unit){
    when(content){
        !in "a".."z"-> "12"
    }
}

fun String.print(){
    println(this);
}

var String.aa:Int
    get() {return this.length}
    set(value) {this.plus(value)}

var test2=fun ():String {
    return ""
}

fun test(): () -> Unit {
    var count = 0;
    return {
        count++;
        println(count)
    }
}

fun test3():String{

    GlobalScope.launch(Dispatchers.Main) {
        for (i in 1..10) {
            println("dongbingbin:" + Thread.currentThread().name)
            kotlinx.coroutines.delay(3000)
            println("dongbingbin:" + Thread.currentThread().name)
            withContext(Dispatchers.IO) {
                Thread.sleep(3000)
                println("dongbingbin:" + Thread.currentThread().name)
            }
            var value = withContext(Dispatchers.IO) {
                "123"
            };

            println("dongbingbin: async value $value")
        }

    }

    return "123"
}

object PersonCompare:Comparator<String>{
    override fun compare(o1: String?, o2: String?): Int {
        return 1;
    }
}

