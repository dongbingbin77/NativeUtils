package com.dongbingbin.nativeutils.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.dongbingbin.nativeutils.model.Person
import com.google.android.youtube.player.YouTubeApiServiceUtil
import com.google.android.youtube.player.YouTubeInitializationResult
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


fun test4(){
    var name:String?="123"

    name?.let{
        println(it)
    }
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

fun netPing(url:String):Boolean{

    var result = false
    try{

        val url = URL(url).openConnection()
        url.connectTimeout = 500
        url.connect()
        result = true
    }catch (ex1:Exception){
        ex1.printStackTrace()
        println("dongbingbin timeout $url ")
        result = false
    }
    return result;

}

fun canAssecssYoutube(context: Context) {
    Observable.just("https://www.youtube.com").map {
        val cdl: CountDownLatch = CountDownLatch(1)
        var result = false
        //result = isAvailableByPing(it)
        Thread(Runnable {
            result = netPing(it)
            cdl.countDown()
        }).start()
        cdl.await(3000, TimeUnit.MILLISECONDS)
        if(!result){
            println("dongbingbin 超时")
        }

        if (result) {
            val isAvi = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(context)
            result = (isAvi == YouTubeInitializationResult.SUCCESS)
        }else{
            println("dongbingbin youtube 不可用")
        }



        if(result) {
            result = CheckAppInstalledUtils.isAppInstalled(context, "com.google.android.gms")
        }else{
            println("dongbingbin 没有安装 com.google.android.gms")
        }


        result
    }.compose(RxUtils.applySchedulersCompute())
            .subscribe {
                if(it){
                    Toast.makeText(context,"dongbingbin 可以使用youtube",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,"dongbingbin 不可以使用youtube",Toast.LENGTH_LONG).show()
                    //println("dongbingbin 不可以使用youtube")
                }
            }

    fun isAvailableByPing(ip: String?): Boolean {
        var ip = ip
        if (ip == null || ip.length <= 0) {
            ip = "223.5.5.5" // 阿里巴巴公共ip
        }
        val runtime = Runtime.getRuntime()
        var ipProcess: Process? = null
        try { //-c 后边跟随的是重复的次数，-w后边跟随的是超时的时间，单位是秒，不是毫秒，要不然也不会anr了
            ipProcess = runtime.exec("ping -c 3 -w 3 $ip")
            val exitValue = ipProcess.waitFor()
            Log.i("Avalible", "Process:$exitValue")
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally { //在结束的时候应该对资源进行回收
            ipProcess?.destroy()
            runtime.gc()
        }
        return false
    }
}