package com.dongbingbin.nativeutils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        GlobalScope.launch {
            kotlinx.coroutines.delay(2000)
            //startActivity(Intent(this@DialogActivity,DialogActivity::class.java))
            //finish()
        }
    }
}
