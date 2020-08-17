package com.dongbingbin.nativeutils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import com.dongbingbin.widget.MyService;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import com.facebook.litho.widget.Text;
import com.tencent.mmkv.MMKV;

public class LithoActivity extends AppCompatActivity {


    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("dongbingbin myservice LithoActivity onConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ComponentContext c = new ComponentContext(this);

        final Component component = Text.create(c)
                .text("Hello World")
                .textSizeDip(50)
                .build();



        long a = System.currentTimeMillis();

        long b  = System.currentTimeMillis();

        long cc = b-a;

        setContentView(LithoView.create(c, component));
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        String i = "dongbingbin";
        System.out.println(i);
        bindService(new Intent(this, MyService.class),conn, Context.BIND_AUTO_CREATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                unbindService(conn);
            }
        },2000);
    }
}
