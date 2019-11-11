package com.dongbingbin.nativeutils

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.dongbingbin.nativeutils.utils.NetWorkSpeedUtils
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    lateinit var job: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var tvSpeed = findViewById<TextView>(R.id.button1)
        NetWorkSpeedUtils(this, Handler{
            tvSpeed.text = it.obj.toString()
            true
        }).startShowNetSpeed()

        findViewById<TextView>(R.id.button1).setOnClickListener { view ->
            print("${view.id}")
        }

        //test1
//        var intent = Intent(this,MainActivity.class);
//        startActivity(intent);
        job = Job()
//        var begin2=System.currentTimeMillis();
//        for (i in 1..100) {
//            launch {
//                println("dongbingbin 1:" + Thread.currentThread().name)
//                val ioData = async(Dispatchers.IO) {
//                    // <- launch scope 的扩展函数，指定了 IO dispatcher，所以在 IO 线程运行
//                    // 在这里执行阻塞的 I/O 耗时操作
//                    println("dongbingbin 2:" + Thread.currentThread().name)
//                }
//
//                // 和上面的并非 I/O 同时执行的其他操作
//                val data = ioData.await() // 等待阻塞 I/O 操作的返回结果
//                println("dongbingbin 3:" + Thread.currentThread().name)
//                //draw(data) // 在 UI 线程显示执行的结果
//            }
//        }

            // 和上面的并非 I/O 同时执行的其他操作
            //val data = ioData.await() // 等待阻塞 I/O 操作的返回结果
            println("dongbingbin 3:"+Thread.currentThread().name)
            //draw(data) // 在 UI 线程显示执行的结果
        }

        val items = listOf(1, 2, 3, 4, 5)

// Lambdas 表达式是花括号括起来的代码块。
//        items.fold(0, {
//            // 如果一个 lambda 表达式有参数，前面是参数，后跟“->”
//            acc: Int, i: Int ->
//            print("acc = $acc, i = $i, ")
//            val result = acc + i
//            println("result = $result")
//            // lambda 表达式中的最后一个表达式是返回值：
//            result
//        })

// lambda 表达式的参数类型是可选的，如果能够推断出来的话：
        val joinedToString = items.fold("Elements:", { acc, i -> acc + " " + i })

// 函数引用也可以用于高阶函数调用：
        val product = items.fold(1, Int::times)

        //items.filter { it>0 }

        val sum = { x:Int, y:Int -> x + y }
//        sum(1,2)
    }

    fun test():()->Unit {
        var count = 0;
        return {
            count++;
            println(count)
        }
    }


       // println("dongbingbin 1:执行完毕 耗时:"+((System.currentTimeMillis()-begin2)))

//        var begin1 = System.currentTimeMillis()
//        for(i in 1..10000){
//            Thread{
//                println("dongbingbin 4:" + Thread.currentThread().name)
//            }.start();
//        }
//
//        println("dongbingbin 4:执行完毕 耗时:"+((System.currentTimeMillis()-begin1)))



//    override val coroutineContext: CoroutineContext
//        get() = Dispatchers.Main + job//To change initializer of created properties use File | Settings | File Templates.

