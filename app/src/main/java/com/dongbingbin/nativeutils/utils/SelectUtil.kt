package com.dongbingbin.nativeutils.utils

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
    return "123"
}