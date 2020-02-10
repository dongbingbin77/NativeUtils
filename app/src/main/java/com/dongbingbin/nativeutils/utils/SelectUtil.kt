package com.dongbingbin.nativeutils.utils

import com.dongbingbin.nativeutils.model.Person
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.function.Consumer

fun test4(){
    var name:String?="123"

    name?.let{
        println(it)
    }
}

fun test5(){
    val list = listOf<String>("1","2","3")
    list.forEach leabel@{
        if(it=="2"){
            return@leabel
        }
    }
    println("test 5 end")
}


fun selectType(content:String,phone:(x:String,y:String)->Unit,email:()->Unit){
    when(content){
        !in "a".."z"-> "12"
    }
}

fun String.print(){
    println(this);

    Person(null)?.apply{}.name?.apply{}?.let {  }
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


    var j = {
        t:String->{

        }
    }

    j("123")

        Observable.just("123").map {
            println(it)
            false
        }.compose(RxUtils.applySchedulersCompute())
                .subscribe{

                }
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

