package com.dongbingbin.nativeutils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.dongbingbin.nativeutils.model.Person
import kotlinx.android.synthetic.main.activity_main4.*
import kotlinx.android.synthetic.main.activity_main4.refreshLayout
import kotlinx.android.synthetic.main.activity_main_gif.*
import kotlinx.android.synthetic.main.activity_main_gif.lottie_list as lottie_list1

class Main4Activity : AppCompatActivity() {

    companion object {
        var test = {
            p1:String, p2:String ->{
                println(p1+p2)
            }
        }
    }

    var last_move_y=0F



    fun test_click(view:View){
        finish()
        var itent = Intent(this,Main2Activity::class.java)
        startActivity(itent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        //addLottie()
        animationView.speed = 0.3f
        refreshLayout_1.setEnableLoadMore(true)
        refreshLayout_1.setOnLoadMoreListener {
            refreshLayout_1.finishLoadMore()
        }
        refreshLayout.setOnTouchListener(object:View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if(ACTION_DOWN==event?.action) {
                    last_move_y = event?.y
                    return true
                }

                var result = event?.y?.minus(last_move_y)
                var progress = result?.toFloat()!!/2000

                if(MotionEvent.ACTION_UP ==event?.action){
//                    refreshLayout.translationY = 0F
                    playAni(progress)
                    return false
                }


                if(result?.toFloat()!! >300f){
                    refreshLayout.translationY = result?.toFloat()!!
                    playAni(progress)
                    return false
                }

                println("dongbingbin:${result}")

                animationView.progress = progress

                refreshLayout.translationY = result?.toFloat()!!

                return true
            }
        })



//        animationView.setMinAndMaxProgress(0.2F,1F)
//        animationView.playAnimation()
//        animationView.repeatCount= LottieDrawable.INFINITE

//        animationView.addAnimatorUpdateListener {
//            println("dongbingbin ${it.animatedValue as Float}")
//        }

    }

    private fun playAni(progress: Float) {
        animationView.setMinAndMaxProgress(progress, 1F)
        animationView.speed = 3f
        animationView.repeatCount = -1
        animationView.playAnimation()
    }

    fun addLottie(){
        for(i in 1..20) {
            var lav = LottieAnimationView(this)
            lav.layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT,600)
            lav.setAnimation(R.raw.lottielogo)
            //lav.repeatCount = -1
            //lav.playAnimation()
            lav.setMinAndMaxProgress(1f,1f)
            lottie_list.addView(lav)
        }
    }
}
