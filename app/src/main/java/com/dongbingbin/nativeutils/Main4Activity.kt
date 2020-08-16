package com.dongbingbin.nativeutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.View
import com.airbnb.lottie.LottieDrawable
import com.dongbingbin.nativeutils.model.Person
import kotlinx.android.synthetic.main.activity_main4.*

class Main4Activity : AppCompatActivity() {

    companion object {
        var test = {
            p1:String, p2:String ->{
                println(p1+p2)
            }
        }
    }

    var last_move_y=0F;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)


//        refreshLayout.setOnTouchListener(object:View.OnTouchListener{
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//
//                if(ACTION_DOWN==event?.action) {
//                    last_move_y = event?.y
//                    return true
//                }
//
//                var result = event?.y?.minus(last_move_y)
//                println("dongbingbin:${result}")
//                animationView.progress = result?.toFloat()!!/2000
//                return true
//            }
//        })

        animationView.setMinAndMaxProgress(0.2F,1F)
        animationView.playAnimation()
        animationView.repeatCount= LottieDrawable.INFINITE

//        animationView.addAnimatorUpdateListener {
//            println("dongbingbin ${it.animatedValue as Float}")
//        }

    }
}
