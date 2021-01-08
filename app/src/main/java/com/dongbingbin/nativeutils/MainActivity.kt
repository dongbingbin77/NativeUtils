package com.dongbingbin.nativeutils

import android.Manifest
import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dongbingbin.nativeutils.databinding.DataBindingDemoActivity
import com.dongbingbin.nativeutils.model.Part
import com.dongbingbin.nativeutils.model.Person
import com.dongbingbin.nativeutils.model.PersonK
import com.dongbingbin.nativeutils.model.User
import com.dongbingbin.nativeutils.utils.*
import com.dongbingbin.nativeutils.utils.DelegateArrayList
import com.dongbingbin.nativeutils.utils.DisplayUtils
import com.dongbingbin.nativeutils.utils.NetWorkSpeedUtils
import com.dongbingbin.nativeutils.utils.print
import com.dongbingbin.sonic.SonicJavaScriptInterface
import com.dongbingbin.sonic.SonicRuntimeImpl
import com.dongbingbin.widget.MyDialog
import com.dongbingbin.widget.MyService
import com.gyf.immersionbar.ImmersionBar
import com.tencent.sonic.sdk.SonicConfig
import com.tencent.sonic.sdk.SonicEngine
import com.tencent.sonic.sdk.SonicSessionConfig
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.rx2.await
import java.io.File
import java.math.BigInteger
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CoroutineScope {
    lateinit var job: Job
    @Inject
    lateinit var personK: PersonK
    @Inject
    lateinit var user: User
    var url = "https://zhidao.baidu.com/daily/view?id=186027";

    companion object {
        var mainActivity: MainActivity? = null

        val MODE_DEFAULT = 0

        val MODE_SONIC = 1

        val MODE_SONIC_WITH_OFFLINE_CACHE = 2

        val PERMISSION_REQUEST_CODE_STORAGE = 1
    }


    var myService: MyService? = null

    var conn: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder: MyService.MyServiceBinder = service as MyService.MyServiceBinder
            myService = binder.getService()
            Toast.makeText(applicationContext, "${myService?.result}", Toast.LENGTH_LONG).show()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            println("dongbingbin myservice onServieDisconnected")
        }
    }

    fun fun1(): Int {
        try {
            Thread.sleep(5000)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 666
    }
    private fun setUserInfo(userInfo: String) {
        Log.e("dongbingbin 协", userInfo)
    }

    inline fun <reified T> Bundle.plus(key: String, value: T) {
        when(value) {
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Char -> putChar(key, value)
            is Int-> putInt(key, value)
        }
    }
    fun move() {
        println("animal move")
    }


//    private lateinit var myViewModel: MyViewModel
    private fun fetchUserFromServer(): Single<User1> {
//        Observable.create<User> {
//
//        }
        var bundle = Bundle()
        bundle.plus("123","123")

//        myViewModel = getSelfViewModel {
//            liveData1.observe(this@MainActivity, Observer {
//                doSomething1(it)
//            })
//            liveData2.observe(this@MainActivity, Observer {
//                doSomething2(it)
//            })
//
//        }


        return Single.create<User1> {
            Log.d("demo", "(1) fetchUserFromServer start, ${Thread.currentThread()}")
            Thread.sleep(3_000)
//            it.onSuccess(User1())
//            try {
                it.onError(Exception("123"))
//            }catch (ex:Exception){
//                ex.printStackTrace()
//            }

            Log.d("demo", "(2) fetchUserFromServer onSuccess, ${Thread.currentThread()}")
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        //return Observable.just(User()).subscribeOn(Schedulers.io()).



    }
//
//            Single.create<User> {
//                Log.d("demo", "(1) fetchUserFromServer start, ${Thread.currentThread()}")
//                Thread.sleep(3_000)
//                it.onSuccess(User())
//                Log.d("demo", "(2) fetchUserFromServer onSuccess, ${Thread.currentThread()}")
//            }.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())

    private fun updateUser(user: User1) {
        Log.d("demo", "(3) updateUser, ${Thread.currentThread()}")
    }



    interface Callback {
        fun onSuccess(user: User1)
        fun onFailure(exception: Exception)
    }

    class User1{
        var name:String?="";
        var age:Int?=0
    }

    var job2:Job?=null
    private suspend fun getToken(): String {
        delay(2000)
        return "token"
    }

    private suspend fun getUserInfo(token: String): String {
        delay(2000)
        return "$token - userInfo"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_go_live_data.setOnClickListener {
            startActivity(Intent(this,DemoLiveDataActivity::class.java))
        }
        println("dongbingbin @Inject personk:${personK.name} ")
        personK.name = "jjjjj"
        Glide.with(this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1608575271441&di=dbc8a464b1df01a0b1c8ab670252054b&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201305%2F30%2F220025pxfkhykvkgkvuktq.jpg")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(iv_test)
//        (k44.layoutParams as ConstraintLayout.LayoutParams)
        var valueAnimator = ValueAnimator.ofFloat(0F,360F)
        valueAnimator.addUpdateListener {
           val vla = (it.animatedValue as Float)
            val params = (k44.layoutParams as ConstraintLayout.LayoutParams)
            params.circleAngle = vla
            k44.layoutParams = params
        }
        valueAnimator.repeatMode = INFINITE
        valueAnimator.repeatCount = -1
        valueAnimator.startDelay = 3000
        valueAnimator.duration = 10000
        valueAnimator.start()
        app_btn_test_shbank_activity.setOnClickListener {
            var intent = Intent(this,SHBankHomeActivity::class.java)
            startActivity(intent)
        }
        GlobalScope.launch (Dispatchers.Main) {
            val token = getToken()
            val userInfo = getUserInfo(token)
            setUserInfo(userInfo)
            val result =  withTimeout(900) {
                repeat(3) {
                    println("hello: $it")
                    delay(400)
                }
                "hello world"
            }
            println("dongbingbin with time $result")
        }

        val job1 = GlobalScope.launch(Dispatchers.Default) {

            println("Current Thread : ${Thread.currentThread().name}")

        }

        var job = GlobalScope.launch {
            delay(6000)
            Log.e("1", "协程执行结束 -- 线程id：${Thread.currentThread().id}")
        }
        Log.e("2", "主线程执行结束")


        repeat(8){
            Log.e("dongbingbin 协","主线程执行$it ${Thread.currentThread().name}")
        }

        test_ttl1.cname = "test1";
        test_ttl2.cname = "test2";
        app_btn_databinding_activity.setOnClickListener {
            var intent = Intent(this,DataBindingDemoActivity::class.java)
            startActivity(intent)
        }
        mainActivity = this;
        initSonic()
        var mar = mapOf("" to "")

        to_lottie.setOnClickListener {

            var intent = Intent(this,Main4Activity::class.java)
            startActivity(intent)

        }

        test_like.setOnClickListener {
            test_like.start()
        }

        to_gif.setOnClickListener {

            var intent = Intent(this,MainGifActivity::class.java)
            startActivity(intent)

        }





        var job2 = GlobalScope.launch(Dispatchers.Main) {
            println("dongbingbin Thread job1 before name ${Thread.currentThread().name}")
            repeat(3) lev1@ {i->
                if(i>1) {
                    return@lev1
                }
            }
            delay(30000)
            println("dongbingbin Thread job1 after name ${Thread.currentThread().name}")
            GlobalScope.launch {
                println("dongbingbin Thread job2 name ${Thread.currentThread().name}")
            }

            GlobalScope.launch {
                println("dongbingbin Thread job3 name ${Thread.currentThread().name}")
            }
        }




        var job1 = GlobalScope.launch {
            var result = async {
                fun1()
            }

            try {
                var user = fetchUserFromServer().await()

                delay(3000)

                print(" ${result.await()} dongbingbin")
            }catch (ex:Exception){
                ex.printStackTrace()
            }


        }

        //job1.cancel()

        initUI()
        println("123321")
        println("123321")
        println("123321")
        println("123321")
        println("123321")
        var delegateArray = DelegateArrayList<String>(innerArray=ArrayList<String>())


        btn_start_service.setOnClickListener {
            startService(Intent(this@MainActivity, MyService::class.java))
        }

        btn_bind_service.setOnClickListener {
            job2.start()
            println("dongbingbin job2 isComplete:${job2.isCompleted}")
            bindService(Intent(this@MainActivity, MyService::class.java),conn, Context.BIND_AUTO_CREATE)
        }

        btn_unbind_service.setOnClickListener {
            try{
                unbindService(conn)
            }catch ( ex1:Exception){
                ex1.printStackTrace();
            }
        }

        app_btn_skip_other_app.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW);
            intent?.setClassName("com.bestwehotel.app.bigshanghai.china.inner","com.bestwehotel.app.whlogin.NewLoginBySMSActivity")
            intent?.putExtra("kkk",123);
            //intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivityForResult(intent,3344)
        }

        btn_stop_service.setOnClickListener {
            stopService(Intent(this@MainActivity, MyService::class.java))
        }

        val part: Part<out Person> = Part()
//        part.setVal(Man("123"))
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

//        swipe_btn.setColorSchemeColors(resources.getColor(R.color.color_18)
//                )
        swipe_btn.setColorSchemeColors(resources.getColor(R.color.color_18)
                ,resources.getColor(R.color.color_17)
                ,resources.getColor(R.color.color_16)
                ,resources.getColor(R.color.color_15)
                ,resources.getColor(R.color.color_14))

        swipe_btn.setOnRefreshListener {
            Handler().postDelayed({
                swipe_btn.isRefreshing=false
            },15000)
        }



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
//        launch {
//            println("dongbingbin 1:" + Thread.currentThread().name)
//            val ioData = async(Dispatchers.IO) {
//                // <- launch scope 的扩展函数，指定了 IO dispatcher，所以在 IO 线程运行
//                // 在这里执行阻塞的 I/O 耗时操作
//                println("dongbingbin 2:" + Thread.currentThread().name)
//            }
//
//            // 和上面的并非 I/O 同时执行的其他操作
//            val data = ioData.await() // 等待阻塞 I/O 操作的返回结果
//            println("dongbingbin 3:" + Thread.currentThread().name)
//            //draw(data) // 在 UI 线程显示执行的结果
//        }
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


        btn_litho.setOnClickListener {
            val intent = Intent(this@MainActivity,LithoActivity::class.java)
            startActivity(intent)
        }
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

        app_btn_calendar_write.setOnClickListener {
            try{
                CalendarReminderUtils.addCalendarEvent(this,"测试日历","这是一条测试提醒",System.currentTimeMillis()+2*60*1000,1);
            }catch (ex1:Exception){
                ex1.printStackTrace();
            }

        }

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

        btn_test_rx.setOnClickListener {
            testRX()
        }


        btn_test_rx_test.setOnClickListener {


            var observable = Observable.create<BigInteger> {
                var i = BigInteger.ZERO
                while(true){
                    it.onNext(i)
                    i = i.add(BigInteger.ONE)
                    Thread.sleep(1000)
                }
            }

            Observable.just(1).map {
                2
            }.map {
                3
            }.subscribe {
                4
            }

            observable.compose(RxUtils.applySchedulersIO()).subscribe {
                println("dongbingbin ${it}")
            }

            var consumer=io.reactivex.functions.Consumer<Int>{
                println("dongbingbin switch 44 "+Thread.currentThread().name)
                println(it)
            }

            var consumer2 = object:Observer<Int>{
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Int) {
                    println("dongbingbin cache ${t}")
                }

                override fun onError(e: Throwable) {
                }
            }

            var testObservable1 = Observable.just(1)

            var testObservable2 = testObservable1.map {
                2
            }

            var testObservable3 = testObservable2.filter {
                true
            }

            var testObservable4 = testObservable3.map {
                try {
                    Thread.sleep(1000)
                }catch(ex:Exception){

                }
                println("dongbingbin cache 110")
                Random(System.currentTimeMillis()).nextInt()
            }.cache().compose(RxUtils.applySchedulersIO())


            testObservable4.subscribe(consumer2)
            testObservable4.subscribe(consumer2)
            testObservable4.subscribe(consumer2)
//            var testObservable1 = Observable.just(1)
//            var testObservable2 = testObservable1.flatMap {
//                println("dongbingbin switch before 22 "+Thread.currentThread().name)
//                Observable.just(2).flatMap {
//                    println("dongbingbin switch 22"+Thread.currentThread().name)
//                    Observable.just(22)
//                }
//                        .compose(RxUtils.applySchedulersIO())
//            }
//
//            var testObservable3 = testObservable2.flatMap {
//                println("dongbingbin switch before 33 "+Thread.currentThread().name)
//                Observable.just(3).flatMap {
//                    println("dongbingbin switch 33"+Thread.currentThread().name)
//                    Observable.just(33)
//                }.compose(RxUtils.applySchedulersCompute())
//            }.compose(RxUtils.applySchedulersIO()).subscribe(consumer)
//
//            testObservable3
        }
    }


    private fun testRX(){
        val persons = Arrays.asList(Person("1"), Person("1"), Person("2"), Person("3"), Person("4")
        )

        Observable.fromIterable(persons).filter{
            it.age>1
        }.compose(RxUtils.applySchedulersIO()).subscribe {
            println("dongbingbin ${it.name}")
        }

        Observable.fromIterable(persons).filter{
            it.age>1
        }.toList().subscribe { t1, t2 ->
            println("dongbingbin 11 ${Thread.currentThread().name}")

        }
        println("dongbingbin 22")


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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                // 暗黑模式已开启
                println("dongbingbin 暗黑模式已开启")
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                // 暗黑模式已关闭
                println("dongbingbin 暗黑模式已关闭")
            }
        }
    }


    fun getDarkModeStatus(context: Context): Boolean {
        val mode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return mode == Configuration.UI_MODE_NIGHT_YES
    }

    /**
     * Dispatch incoming result to the correct fragment.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //
        if(requestCode==3344&&data!=null){
            Toast.makeText(application,data?.getStringExtra("333"),Toast.LENGTH_LONG).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}