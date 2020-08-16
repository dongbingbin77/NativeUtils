package com.dongbingbin.widget;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.dongbingbin.nativeutils.utils.RxUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class MyService extends Service {
    public MyService() {
    }

    

    @Override
    public IBinder onBind(Intent intent) {
        return new MyServiceBinder();
    }

    public class MyServiceBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .compose(RxUtils.applySchedulersCompute())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        count++;
                    }
                });
        System.out.println("dongbingbin myservice onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("dongbingbin myservice onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        System.out.println("dongbingbin myservice onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("dongbingbin myservice onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("dongbingbin myservice onDestroy");
    }

    private int count = 0;

    public int getResult(){
        return count;
    }

    public void addResult(){
        count++;
    }
}
