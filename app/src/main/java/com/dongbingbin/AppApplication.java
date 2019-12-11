package com.dongbingbin;

import android.app.Activity;
import android.app.Application;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Window;

import com.didichuxing.doraemonkit.DoraemonKit;
import com.dongbingbin.nativeutils.model.Person;
import com.dongbingbin.nativeutils.utils.NetWorkSpeedUtils;
import com.dongbingbin.nativeutils.utils.RxUtils;
import com.fm.openinstall.OpenInstall;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class AppApplication extends Application {

    public List<Activity> list = new ArrayList();

    private static AppApplication appApplication;

    public static AppApplication getAppApplication() {
        return appApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        test();
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
        Map<String, Object> myMap = new Gson().fromJson(json, type);

        List<Person> persons = Arrays.asList(new Person("1")
                ,new Person("1")
            ,new Person("2")
                ,new Person("3")
                ,new Person("4")
    );

        Observable.fromIterable(persons).flatMap(new Function<Object, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Object o) throws Exception {

                return Observable.just(Arrays.asList(o)).delay(1,TimeUnit.SECONDS);
            }
        }).compose(RxUtils.applySchedulersCompute())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                });
    }
}
