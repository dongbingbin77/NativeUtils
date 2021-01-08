package com.dongbingbin.nativeutils

import android.os.Bundle
import android.util.TimeUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dongbingbin.nativeutils.utils.DateUtils
import kotlinx.android.synthetic.main.activity_demo_live_data_layout.*

class DemoLiveDataActivity : AppCompatActivity() {

    var timeString : MutableLiveData<String> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_live_data_layout)

        timeString.observe(this, Observer{
            test1.text = timeString.value
        })

        test2.setOnClickListener {
            timeString.value = DateUtils.getCurrentDateTime()
        }
    }

}