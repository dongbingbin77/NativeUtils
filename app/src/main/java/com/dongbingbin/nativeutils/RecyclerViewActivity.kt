package com.dongbingbin.nativeutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dongbingbin.nativeutils.model.Person

class RecyclerViewActivity : AppCompatActivity() {

    companion object {
        var test = {
            p1:String, p2:String ->{
                println(p1+p2)
            }
        }
    }

    private val name:String by lazy{
        "123"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recylerview)



        var p = Person("123")
        var p1 = Person("123")

    }
}
