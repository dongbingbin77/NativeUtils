package com.dongbingbin;

import android.app.Activity;
import android.app.Application;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.widget.Toast;

import com.didichuxing.doraemonkit.DoraemonKit;
import com.dongbingbin.nativeutils.model.Person;
import com.dongbingbin.nativeutils.utils.NetWorkSpeedUtils;
import com.dongbingbin.nativeutils.utils.RxUtils;
import com.dongbingbin.widget.TestObservable;
import com.dongbingbin.nativeutils.utils.SocketServer;
import com.dongbingbin.widget.TestOriginObservable;
import com.facebook.soloader.SoLoader;
import com.facebook.stetho.Stetho;
import com.fm.openinstall.OpenInstall;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static com.dongbingbin.nativeutils.utils.SelectUtilKt.test5;

public class AppApplication extends Application {

    public List<Activity> list = new ArrayList();

    private static AppApplication appApplication;

    public static AppApplication getAppApplication() {
        return appApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, false);

        Stetho.initializeWithDefaults(this);

        test();
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (code == ConnectionResult.SUCCESS) {
            Toast.makeText(this,"谷歌服务可用",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"谷歌服务不可用",Toast.LENGTH_LONG).show();
        }
        appApplication = this;
        OpenInstall.init(this);
        String str1 = "1234";
        String str2 = new String("1234");
        String s1 = "Monday";
        String s2 = new String("Monday");
        NetWorkSpeedUtils obj1 = new NetWorkSpeedUtils(this,null);
        NetWorkSpeedUtils obj2 = new NetWorkSpeedUtils(this,null);

        DoraemonKit.install(this);
        //DoraemonKit.hide();
        new SocketServer().startActionAsync();

        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//                Log.v("tag_2","onActivityCreated" + activity.getClass().getName());
                list.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
//                Log.v("tag_2","onActivityDestroyed" + activity.getClass().getName());
                list.remove(activity);
            }
        });
    }

    private void test(){


        Gson gson = new GsonBuilder()
                //.excludeFieldsWithoutExposeAnnotation() //不对没有用@Expose注解的属性进行操作
                .enableComplexMapKeySerialization() //当Map的key为复杂对象时,需要开启该方法
                .serializeNulls() //当字段值为空或null时，依然对该字段进行转换
                .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS") //时间转化为特定格式
                .setPrettyPrinting() //对结果进行格式化，增加换行
                .disableHtmlEscaping() //防止特殊字符出现乱码
               // .registerTypeAdapter(User.class,new UserAdapter()) //为某特定对象设置固定的序列或反序列方式，自定义Adapter需实现JsonSerializer或者JsonDeserializer接口
                .create();

        Person person = new Person(null);

        person.setAge(10);

        gson.toJson(person);



        String json = "{ \n" +
                "    \"header\" : { \n" +
                "        \"alerts\" : [ \n" +
                "            {\n" +
                "                \"AlertID\" : \"2\",\n" +
                "                \"TSExpires\" : null,\n" +
                "                \"Target\" : \"1\",\n" +
                "                \"Text\" : \"woot\",\n" +
                "                \"Type\" : \"1\"\n" +
                "            },\n" +
                "            { \n" +
                "                \"AlertID\" : \"3\",\n" +
                "                \"TSExpires\" : null,\n" +
                "                \"Target\" : \"1\",\n" +
                "                \"Text\" : \"woot\",\n" +
                "                \"Type\" : \"1\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"session\" : \"0bc8d0835f93ac3ebbf11560b2c5be9a\"\n" +
                "    },\n" +
                "    \"result\" : \"4be26bc400d3c\"\n" +
                "}";

        //JSONObject.pa(json);

        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Object myMap = new Gson().fromJson(json, Object.class);

        final List<Person> persons = Arrays.asList(new Person("1")
                ,new Person("1")
            ,new Person("2")
                ,new Person("3")
                ,new Person("4")
    );

        Observable.fromIterable(persons).flatMap(new Function<Object, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Object o) throws Exception {

                final CountDownLatch cdl = new CountDownLatch(1);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cdl.countDown();
                    }
                },6000);
                cdl.await(10,TimeUnit.SECONDS);


                return Observable.just(Arrays.asList(o)).delay(5,TimeUnit.SECONDS);
            }
        }).compose(RxUtils.applySchedulersCompute())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                });

//
//        Observable.fromIterable(persons).flatMap(new Function<Object, ObservableSource<?>>() {
//            @Override
//            public ObservableSource<?> apply(Object o) throws Exception {

//                final CountDownLatch cdl = new CountDownLatch(1);
//                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        cdl.countDown();
//                    }
//                },6000);
//                cdl.await(10,TimeUnit.SECONDS);


//                return new TestObservable<List<Object>>(Observable.just(Arrays.asList(o)).delay(1,TimeUnit.SECONDS));
//            }
//        }).compose(RxUtils.applySchedulersCompute())
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//                        System.out.println(o.toString());
//                    }
//                });

        new TestObservable<List<Person>>(new TestOriginObservable<List<Person>>(persons))
                .onErrorReturn(new Function<Throwable, List<Person>>() {
            @Override
            public List<Person> apply(Throwable throwable) throws Exception {
                return persons;
            }
        })
                .flatMap(new Function<List<Person>, ObservableSource<List<Person>>>() {
                    @Override
                    public ObservableSource<List<Person>> apply(List<Person> people) throws Exception {
                        return new TestObservable<List<Person>>(new TestOriginObservable<List<Person>>(people));
                    }
                }).onErrorReturn(new Function<Throwable, List<Person>>() {
            @Override
            public List<Person> apply(Throwable throwable) throws Exception {
                return persons;
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread()).subscribe(new Consumer<List<Person>>() {
            @Override
            public void accept(List<Person> person) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("dongbingbin rx error "+throwable.getMessage());
            }
        });

        test5();
    }
}
