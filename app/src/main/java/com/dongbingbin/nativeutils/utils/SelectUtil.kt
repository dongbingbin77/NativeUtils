package com.dongbingbin.nativeutils.utils

import android.annotation.SuppressLint
import com.dongbingbin.nativeutils.model.Person
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.function.Consumer

@SuppressLint("CheckResult")
fun test4(){
    var name:String?="123"

    name?.let{
        println(it)
    }

    var kCat: KCat? = null

    if(kCat?.isMan == true){

    }

    var count = 0
    Observable.just(1).map {
        Thread.sleep(3000)
        //count++
        Observable.just(3).map {
            println("dongbingbin ** "+it)
        }.subscribe {
            count++
        }
    }.repeatUntil {
        count>5
    }.compose(RxUtils.applySchedulersIO()).subscribe({
        println("dongbingbin !! "+count)
    })

    var list = mutableListOf<Int>(1,2,3,4)
    var list1 = mutableListOf<Int>(5,6,7,8)

    Observable.just(list,list1).flatMap {
        println("dongbingbin 分离合并 第一步 "+it)
        Observable.fromIterable(it)
    }.flatMap {
        println("dongbingbin 分离合并 第二步 "+it)
        Observable.just(it)
    }.toList().flatMapObservable {
        println("dongbingbin 分离合并 第三步 "+it)
        Observable.just(it)
    }.subscribe {
        println("dongbingbin 分离合并 第四步 "+it)
    }

    Observable.create(ObservableOnSubscribe<String> { emitter ->
        emitter.onNext("")
        emitter.onComplete()
    })





    var consumer=io.reactivex.functions.Consumer<Int>{
        println("dongbingbin switch 44 "+Thread.currentThread().name)
        println(it)
    }

    var testObservable = Observable.just(1)
    testObservable.flatMap {
        println("dongbingbin switch before 22 "+Thread.currentThread().name)
        Observable.just(2).flatMap {
            println("dongbingbin switch 22"+Thread.currentThread().name)
            Observable.just(22)
        }
                .compose(RxUtils.applySchedulersIO())
    }.flatMap {
        println("dongbingbin switch before 33 "+Thread.currentThread().name)
        Observable.just(3).flatMap {
            println("dongbingbin switch 33"+Thread.currentThread().name)
            Observable.just(33)
        }.compose(RxUtils.applySchedulersCompute())
    }.compose(RxUtils.applySchedulersIO()).subscribe(consumer)




    Observable.just(1).flatMap {
        //compose1 sub thread
        println("dongbingbin switch before 22 "+Thread.currentThread().name)
         Observable.just(2).flatMap {
             //compose2 sub thread
             println("dongbingbin switch 22"+Thread.currentThread().name)
             Observable.just(22)
         }.compose(RxUtils.applySchedulersCompute())//compose2
    }.flatMap {
        //compose2 main thread
        println("dongbingbin switch before 33 "+Thread.currentThread().name)
        Observable.just(3).flatMap {
            //compose3 sub Thread
            println("dongbingbin switch 33"+Thread.currentThread().name)
            Observable.just(33)
        } .compose(RxUtils.applySchedulersCompute())//compose3
    }.compose(RxUtils.applySchedulersIO())//compose1
            .subscribe{
        //compose1 main thread
        println("dongbingbin switch 44 "+Thread.currentThread().name)
        println(it)
    }


//    Observable.just(1).repeat(3).flatMap {
//        Thread.sleep(3000)
//        count++
//        if(it==2){
//            Observable.just(1).repeatUntil {  }
//        }else{
//            Observable.just(1)
//        }
//    }.compose(RxUtils.applySchedulersIO()).subscribe({
//        println("dongbingbin !!")
//    })

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
    var per = object:Person("123"){}
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

