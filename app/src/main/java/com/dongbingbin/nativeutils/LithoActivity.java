package com.dongbingbin.nativeutils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;
import com.facebook.litho.widget.Text;

public class LithoActivity extends AppCompatActivity {


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
    }
}
