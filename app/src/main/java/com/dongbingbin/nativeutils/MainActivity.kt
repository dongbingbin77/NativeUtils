package com.dongbingbin.nativeutils

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.dongbingbin.nativeutils.utils.DelegateArrayList
import com.dongbingbin.nativeutils.model.Person
import com.dongbingbin.nativeutils.model.PersonK
import com.dongbingbin.nativeutils.utils.DisplayUtils
import com.dongbingbin.nativeutils.utils.NetWorkSpeedUtils
import com.dongbingbin.nativeutils.utils.print
import com.dongbingbin.sonic.SonicJavaScriptInterface
import com.dongbingbin.sonic.SonicRuntimeImpl
import com.dongbingbin.widget.MyDialog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gyf.immersionbar.ImmersionBar
import com.tencent.sonic.sdk.SonicConfig
import com.tencent.sonic.sdk.SonicEngine
import com.tencent.sonic.sdk.SonicSessionConfig
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.File
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    lateinit var job: Job

    var url = "https://zhidao.baidu.com/daily/view?id=186027";
    companion object {
        val MODE_DEFAULT = 0

        val MODE_SONIC = 1

        val MODE_SONIC_WITH_OFFLINE_CACHE = 2

        val PERMISSION_REQUEST_CODE_STORAGE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSonic()


        initUI()

        var delegateArray = DelegateArrayList<String>(innerArray=ArrayList<String>())


        var p = Person("123")
        var p1 = Person("123")

        var pk1 = PersonK("123",123)
        var pk2 = PersonK("1234",1234)

        var mapP = listOf<PersonK>(pk1,pk2)

        var mapN = mapP.filter { it.name!=null }.map {
            val age = it.age
            age
        }.average()

        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this@MainActivity,listOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray(),123)
            //requestPermissions(listOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE).toTypedArray(),111)
        //}

        var p3 = p1::getName

        val name = p3()

        var funct = {x:String->x}


        Handler().postDelayed({
            val rectangle = Rect()
            val window = window
            window.decorView.getWindowVisibleDisplayFrame(rectangle)
            val statusBarHeight = rectangle.top
            val contentViewTop = window.findViewById<View>(Window.ID_ANDROID_CONTENT).top
            val titleBarHeight = contentViewTop - statusBarHeight
            //DisplayUtils.px2dp(applicationContext,titleBarHeight as Float)
            val params = title_view.layoutParams
            var title_height = DisplayUtils.getStatusBarHeight(applicationContext)
            (params as LinearLayout.LayoutParams).topMargin = title_height
            title_view.layoutParams = params
            title_view.requestLayout()
        },1200)

        MyDialog(this).show();
        "123".print()
        button1.setOnClickListener {

            startActivity(Intent(this@MainActivity, Main2Activity::class.java));

        };
        //File("").readBytes().toTypedArray();

        var tvSpeed = findViewById<TextView>(R.id.button1)
        NetWorkSpeedUtils(this, Handler {
            tvSpeed.text = it.obj.toString()
            true
        }).startShowNetSpeed()

        findViewById<TextView>(R.id.button1).setOnClickListener { view ->
            print("${view.id}")
            startActivity(Intent(this@MainActivity, Main2Activity::class.java));
        }

        test_edit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

            }
        });

        test_edit1.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                var intent = Intent(this@MainActivity,WebviewActivity::class.java)
                startActivityForResult(intent,11)
                overridePendingTransition(R.anim.anim_in,R.anim.anim_out)
            }
        })

        test_edit.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        button2.setOnClickListener {
            startActivity(Intent(this@MainActivity, DialogActivity::class.java))
        }


        test_edit.setOnClickListener {


                AlertDialog.Builder(this@MainActivity)
                        .setMessage("123")
                        .create().show();

        }


        //test1
//        var intent = Intent(this,MainActivity.class);
//        startActivity(intent);
        job = Job()

        var testString = "123456789";
        var testString2 = "12345";
        var testString3 = testString.substringAfter(testString2);

//        var begin2=System.currentTimeMillis();
//        for (i in 1..100) {
        launch {
            println("dongbingbin 1:" + Thread.currentThread().name)
            val ioData = async(Dispatchers.IO) {
                // <- launch scope 的扩展函数，指定了 IO dispatcher，所以在 IO 线程运行
                // 在这里执行阻塞的 I/O 耗时操作
                println("dongbingbin 2:" + Thread.currentThread().name)
            }

            // 和上面的并非 I/O 同时执行的其他操作
            val data = ioData.await() // 等待阻塞 I/O 操作的返回结果
            println("dongbingbin 3:" + Thread.currentThread().name)
            //draw(data) // 在 UI 线程显示执行的结果
        }
//        }

        // 和上面的并非 I/O 同时执行的其他操作
        //val data = ioData.await() // 等待阻塞 I/O 操作的返回结果
        println("dongbingbin 3:" + Thread.currentThread().name)
        //draw(data) // 在 UI 线程显示执行的结果


        val items = listOf(1, 2, 3, 4, 5);


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
        val joinedToString = items.fold("Elements:", { acc, i -> "$acc $i" })

        // 函数引用也可以用于高阶函数调用：
        val product = items.fold(1, Int::times)

        //items.filter { it>0 }

        val sum = { x: Int, y: Int -> x + y }
//        sum(1,2)

        val aaa = setOf<String>("1","1","2")

        var e:Int=123

        var c:Int? = e
        var d:Int? =e

        var a:String?="123"
        var b:String?="123"
        println("dongbingbin a.equals(b) ${a.equals(b)}")

        var a1 = fun(x:String) {
            var a11 = "123"
            println(a11+x)
        }
        a1("123")

        var aa = a.let{
             it
        }

        println("dongbingbin a.let ${aa}")

        GlobalScope.launch(Dispatchers.Main) {
            //Thread.sleep(30000)
            for (i in 1..10) {
                println("dongbingbin launch:" + Thread.currentThread().name)
                kotlinx.coroutines.delay(3000)
                //Thread.sleep(3000)
                println("dongbingbin launch:" + Thread.currentThread().name)
                withContext(Dispatchers.IO) {
                    Thread.sleep(3000)
                    println("dongbingbin launch IO:" + Thread.currentThread().name)
                }
                var value = withContext(Dispatchers.IO) {
                    "123"
                };

                println("dongbingbin launch: async value $value ${Thread.currentThread().name}")
            }

        }

        println("dongbingbin launch after ${Thread.currentThread().name}")
        selectType("",phone={
            x,y->{

         }
        },email={

        })

        ImmersionBar.with(this)
                .statusBarColor(R.color.transparent).init()
    }

    fun selectType(content:String,phone:(x:String,y:String)->Unit,email:()->Unit){
        when(content){
            !in "a".."z"-> "12"
        }
    }
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


    // println("dongbingbin 1:执行完毕 耗时:"+((System.currentTimeMillis()-begin2)))

//        var begin1 = System.currentTimeMillis()
//        for(i in 1..10000){
//            Thread{
//                println("dongbingbin 4:" + Thread.currentThread().name)
//            }.start();
//        }
//
//        println("dongbingbin 4:执行完毕 耗时:"+((System.currentTimeMillis()-begin1)))


    override fun onResume() {
        super.onResume()
        println("dongbingbin:onResume")
    }

    override fun onStart() {
        super.onStart()
        println("dongbingbin:onStart")
    }

    override fun onRestart() {
        super.onRestart()
        println("dongbingbin:onRestart")
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job//To change initializer of created properties use File | Settings | File Templates.


    fun copyFolder(srcFolder: String, desFolder: String): Unit {
        if (!TextUtils.isEmpty(srcFolder) && File(srcFolder).exists() && !TextUtils.isEmpty(desFolder)) {
            File(srcFolder).listFiles().forEach {

                var index = it.absolutePath.indexOf(srcFolder);
                var relatPath = it.absolutePath.substringAfter(srcFolder);
                var newfile = File(File(desFolder).absolutePath, relatPath)
                if (it.isFile) {
                    it.mkdirs();
                }
            }
        }
    }

    private fun initSonic() { // init sonic engine
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(SonicRuntimeImpl(application), SonicConfig.Builder().build())
        }
    }

    private fun initUI(){
        // clean up cache btn
        val btnReset = findViewById<View>(R.id.btn_reset) as Button
        btnReset.setOnClickListener { SonicEngine.getInstance().cleanCache() }

        // default btn
        // default btn
        val btnDefault = findViewById<View>(R.id.btn_default_mode) as Button
        btnDefault.setOnClickListener { startBrowserActivity(MODE_DEFAULT) }

        // preload btn
        // preload btn
        val btnSonicPreload = findViewById<View>(R.id.btn_sonic_preload) as Button
        btnSonicPreload.setOnClickListener {
            val sessionConfigBuilder: SonicSessionConfig.Builder = SonicSessionConfig.Builder()
            sessionConfigBuilder.setSupportLocalServer(true)
            // preload session
            val preloadSuccess = SonicEngine.getInstance().preCreateSession(url, sessionConfigBuilder.build())
            Toast.makeText(applicationContext, if (preloadSuccess) "Preload start up success!" else "Preload start up fail!", Toast.LENGTH_LONG).show()
        }

        // sonic mode load btn
        // sonic mode load btn
        val btnSonic = findViewById<View>(R.id.btn_sonic) as Button
        btnSonic.setOnClickListener { startBrowserActivity(MODE_SONIC) }

        // load sonic with offline cache
        // load sonic with offline cache
        val btnSonicWithOfflineCache = findViewById<View>(R.id.btn_sonic_with_offline) as Button
        btnSonicWithOfflineCache.setOnClickListener { startBrowserActivity(MODE_SONIC_WITH_OFFLINE_CACHE) }
    }
    private fun startBrowserActivity(mode: Int) {
        val intent = Intent(this, BrowserActivity::class.java)
        intent.putExtra(BrowserActivity.PARAM_URL, url)
        intent.putExtra(BrowserActivity.PARAM_MODE, mode)
        intent.putExtra(SonicJavaScriptInterface.PARAM_CLICK_TIME, System.currentTimeMillis())
        startActivityForResult(intent, -1)
        overridePendingTransition(R.anim.anim_in,0)
    }
    fun preload(DEMO_URL: String) {
        val sessionConfigBuilder: SonicSessionConfig.Builder = SonicSessionConfig.Builder()
        sessionConfigBuilder.setSupportLocalServer(true)

        // preload session
        // preload session
        val preloadSuccess: Boolean = SonicEngine.getInstance().preCreateSession(DEMO_URL, sessionConfigBuilder.build())
        Toast.makeText(applicationContext, if (preloadSuccess) "Preload start up success!" else "Preload start up fail!", Toast.LENGTH_LONG).show()
    }

    fun preload_click(view:View){
        val sessionConfigBuilder: SonicSessionConfig.Builder = SonicSessionConfig.Builder()
        sessionConfigBuilder.setSupportLocalServer(true)

        // preload session
        // preload session
        val preloadSuccess = SonicEngine.getInstance().preCreateSession(url, sessionConfigBuilder.build())
        Toast.makeText(applicationContext, if (preloadSuccess) "Preload start up success!" else "Preload start up fail!", Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}