package com.dongbingbin.nativeutils

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.dongbingbin.nativeutils.utils.NetWorkSpeedUtils
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(),CoroutineScope {
    lateinit var job: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var tvSpeed = findViewById<TextView>(R.id.button1)
        NetWorkSpeedUtils(this, Handler{
            tvSpeed.text = it.obj.toString()
            true
        }).startShowNetSpeed()
        //test1
//        var intent = Intent(this,MainActivity.class);
//        startActivity(intent);
        job = Job()
        launch {
            println("dongbingbin 1:"+Thread.currentThread().name)
            val ioData = async(Dispatchers.IO) { // <- launch scope 的扩展函数，指定了 IO dispatcher，所以在 IO 线程运行
                // 在这里执行阻塞的 I/O 耗时操作
                println("dongbingbin 2:"+Thread.currentThread().name)
            }

            // 和上面的并非 I/O 同时执行的其他操作
            val data = ioData.await() // 等待阻塞 I/O 操作的返回结果
            println("dongbingbin 3:"+Thread.currentThread().name)
            //draw(data) // 在 UI 线程显示执行的结果
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job//To change initializer of created properties use File | Settings | File Templates.
}
