package com.dongbingbin.nativeutils

import android.graphics.drawable.Drawable
import android.media.AudioRecord.MetricsConstants.SOURCE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.Nullable
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dongbingbin.nativeutils.model.Person
import kotlinx.android.synthetic.main.activity_main4.*
import kotlinx.android.synthetic.main.activity_main_gif.*
import kotlinx.android.synthetic.main.activity_main4.refreshLayout as refreshLayout1

//import pl.droidsonroids.gif.GifDrawable

class MainGifActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_main_gif)

        val gifDrawable= pl.droidsonroids.gif.GifDrawable(assets,"test.gif");
        gif_view.background = gifDrawable
        gifDrawable.stop()
        //gifDrawable.start()
//        for(i in 1..20) {
//            var lav = ImageView(this)
//            lav.layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT,600)
//
//            Glide.with(this).load(R.raw.test).listener(object:RequestListener<Drawable> {
//                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
//                    return false
//                }
//
//                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                    if (resource is GifDrawable) {
//                        //加载一次
//                        (resource as GifDrawable).setLoopCount(-1);
//                    }
//                    return false
//                }
//            }).into(lav);
//
//            lottie_list.addView(lav)
//        }


        refreshLayout.setOnTouchListener(object:View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if(ACTION_DOWN==event?.action) {
                    last_move_y = event?.y
                    return true
                }

                var result = event?.y?.minus(last_move_y)
                println("dongbingbin:${result}")
                //animationView.progress = result?.toFloat()!!/2000
                gifDrawable.seekTo(result?.toInt()!!)
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
}
