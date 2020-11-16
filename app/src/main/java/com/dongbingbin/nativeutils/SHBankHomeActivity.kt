package com.dongbingbin.nativeutils

import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class SHBankHomeActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sh_bank_home)
    }

    fun stopSearialNumberJob(view: View){
        getPaySerialNumberJob?.cancel()
    }

    fun startSearialNumberJob(view: View){
        launchPaySerialNumberJob()
    }

    fun stopPayCodeJob(view: View){
        getPayCodeJob?.cancel()
    }

    fun startPayCodeJob(view: View){
        launchPayCodeJob()
    }


    fun stopPayResultJob(view: View){
        getPayResultJob?.cancel()
    }

    fun startPayResultJob(view: View){
        launchPayResultJob()
    }

    private fun test(){
        launchPaySerialNumberJob()
    }

    private fun launchPaySerialNumberJob() {
        getPaySerialNumberJob = GlobalScope.launch {
            while (true) ddd@ {
                //重置支付码
                var paySearialNumber = withTimeoutOrNull(3000) {
                    getPaySerialNumber()
                }
                if (paySearialNumber == null) {
                    println("dongbingbin shbank 流水号 超时")
                    return@ddd
                } else {
                    //轮询支付码
                    launchPayCodeJob()
                    delay(15000)
                }
            }
        }
    }

    private fun launchPayCodeJob() {
        if (getPayCodeJob != null) {
            try {
                getPayCodeJob?.cancel()
            } catch (exp: Exception) {
                exp.printStackTrace()
            }

        }
        getPayCodeJob = GlobalScope.launch {
            while (true) paycodeLoop@ {
                var payCode = withTimeoutOrNull(3000) {
                    getPayCode()
                }
                if (payCode == null) {
                    delay(1000)
                } else {
                    //轮询支付结果
                    launchPayResultJob()
                    return@paycodeLoop
                }
            }
        }
    }

    private fun launchPayResultJob() {
        getPayCodeJob?.cancel()
        if (getPayResultJob != null) {
            getPayResultJob?.cancel()
        }
        getPayResultJob = GlobalScope.launch {
            while (true) {
                var payResult = withTimeoutOrNull(3000) {
                    getPayResult()
                }
                if (payResult == null) {
                    delay(2000)
                } else {
                    //处理支付结果
                }
            }
        }
    }

    var getPaySerialNumberJob:Job?=null

    var getPayCodeJob:Job?=null

    var getPayResultJob:Job?=null

    suspend fun getPaySerialNumber():String{
        delay(1000)
        println("dongbingbin shbank 获取流水号")
        return "流水号"
    }

    suspend fun getPayCode():String{
        delay(1000)
        println("dongbingbin shbank 支付码")
        return "支付码"
    }

    suspend fun getPayResult():String{
        delay(2000)
        println("dongbingbin shbank 获取支付结果")
        return "支付结果"
    }

}