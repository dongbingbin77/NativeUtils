package com.dongbingbin.nativeutils;

import androidx.appcompat.app.AppCompatActivity;

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

        setContentView(LithoView.create(c, component));
    }
}
