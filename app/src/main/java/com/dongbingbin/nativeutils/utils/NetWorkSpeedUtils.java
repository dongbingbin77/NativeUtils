package com.dongbingbin.nativeutils.utils;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;

import com.fm.openinstall.OpenInstall;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The type Net work speed utils.
 */
public class NetWorkSpeedUtils {
    private Context context;
    private Handler mHandler;
 
    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;

    /**
     * Instantiates a new Net work speed utils.
     *
     * @param context  the context
     * @param mHandler the m handler
     */
    public NetWorkSpeedUtils(Context context, Handler mHandler){
        this.context = context;
        this.mHandler = mHandler;


    }

    /**
     * The Task.
     */
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            showNetSpeed();
        }
    };

    /**
     * Start show net speed.
     */
    public void startShowNetSpeed(){
        lastTotalRxBytes = getTotalRxBytes();
        lastTimeStamp = System.currentTimeMillis();
        new Timer().schedule(task, 1000, 1000); // 1s后启动任务，每2s执行一次
 
    }
 
    private long getTotalRxBytes() {
        return (TrafficStats.getTotalRxBytes()/1024);
        //return TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 :(TrafficStats.getTotalRxBytes()/1024);//转为KB
    }
 
    private void showNetSpeed() {
        long nowTotalRxBytes = getTotalRxBytes();
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换
        long speed2 = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 % (nowTimeStamp - lastTimeStamp));//毫秒转换
 
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
 
        Message msg = mHandler.obtainMessage();
        msg.what = 100;
        msg.obj = String.valueOf(speed) + "." + String.valueOf(speed2) + " kb/s";
        mHandler.sendMessage(msg);//更新界面
    }
}
