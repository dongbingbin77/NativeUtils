package com.dongbingbin.nativeutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dongbingbin.nativeutils.model.Person

class Main2Activity : AppCompatActivity() {

    companion object {
        var test = {
            p1:String, p2:String ->{
                println(p1+p2)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        Main2Activity.test("12","34");
        var p = Person("123")
        var p1 = Person("123")

    }
}
