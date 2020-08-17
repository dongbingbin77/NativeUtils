package com.dongbingbin.nativeutils

import android.animation.ValueAnimator
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.media.MediaCodec
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.TextureView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.dongbingbin.nativeutils.utils.ScrollingImageView
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar

import kotlinx.android.synthetic.main.activity_splash1.*



class SplashActivity : AppCompatActivity() {

    companion object {
        var test = {
            p1:String, p2:String ->{
                println(p1+p2)
            }
        }
    }


    private var svStart: SurfaceView? = null
    private var player: MediaPlayer? = null
    private var holder: SurfaceHolder? = null
    //定义一个缓冲区句柄（由屏幕合成程序管理）
    private var surface: Surface? = null
    //定义一个媒体播发对象
    private var mMediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash1)

        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).init()

        var intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        //textureview.surfaceTextureListener = surfaceTextureListener

//        var valueAnimator = ValueAnimator.ofFloat(0F,1F)
//        valueAnimator.addUpdateListener {
//            textureview.alpha = (it.animatedValue as Float)
//        }
//        valueAnimator.duration = 5000
//        valueAnimator.start()




        Glide.with(this).asBitmap().load(R.drawable.kuantudemo).into(object:SimpleTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                whhomepage_startup_img_scroll.setBitMap(resource,2);
                whhomepage_startup_img_scroll.start()
            }
        })


        //svStart = findViewById(R.id.sv_start);
//        holder = svStart?.getHolder();
//
//        holder?.addCallback(object :SurfaceHolder.Callback{
//            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
//
//            }
//
//            override fun surfaceDestroyed(holder: SurfaceHolder?) {
//                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun surfaceCreated(holder: SurfaceHolder?) {
//
//            }
//        });
//        holder?.setKeepScreenOn(true);
//        player = MediaPlayer();
//        player?.setDisplay(holder);
//        player?.setOnPreparedListener(object: MediaPlayer.OnPreparedListener {
//            @Override
//            public
//            override fun onPrepared(mp: MediaPlayer?) {
//                //svStart.setLayoutParams(lp);
//                if (!player!!.isPlaying()) {
//                    player!!.start()
//                }
//            }
//        });
//
//        try{
//            val file: AssetFileDescriptor = resources.openRawResourceFd(R.raw.kk3)
//            player?.setDataSource(file.fileDescriptor, file.startOffset,
//                    file.length)
//            //player?.setDataSource(this,Uri.parse("android.resource://$packageName/"+R.raw.kk3));
//            //player?.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
//            player?.setLooping(true)
//            player?.prepare()
//            //player?.start()
//        }catch ( ex1:Exception){
//            ex1.printStackTrace();
//        }

//        video_view.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/raw/kk"))
//        video_view.start()
    }




    /**
     * 流媒体播放结束时回调类
     */
    private val onCompletionListener = MediaPlayer.OnCompletionListener {
        Handler().postDelayed({
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        },3000)
    }
    /**
     * 定义TextureView监听类SurfaceTextureListener
     * 重写4个方法
     */
    private val surfaceTextureListener = object : TextureView.SurfaceTextureListener {

        /**
         * 初始化好SurfaceTexture后调用
         * @param surfaceTexture
         * @param i
         * @param i1
         */
        override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, i: Int, i1: Int) {
            surface = Surface(surfaceTexture)
            //开启一个线程去播放视频
            //PlayerVideoThread().start()
            //mMediaPlayer = MediaPlayer.create(this@SplashActivity,R.raw.kk3)
            mMediaPlayer = MediaPlayer()
            mMediaPlayer?.setDataSource(applicationContext,Uri.parse("android.resource://"+packageName+"/" + R.raw.kk3))

            //把res/raw的资源转化为Uri形式访问(android.resource://)
            //val uri = Uri.parse("android.resource://com.github.davidji80.videoplayer/" + R.raw.ansen)
            //设置播放资源(可以是应用的资源文件／url／sdcard路径)
            //mMediaPlayer.setDataSource(this@SplashActivity, uri)
            //设置渲染画板
            mMediaPlayer?.setSurface(surface)
            mMediaPlayer?.setVideoScalingMode(MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
            //设置播放类型
            //mMediaPlayer?.setAudioStreamType(AudioManager.)
            //播放完成监听
            mMediaPlayer?.setOnCompletionListener(onCompletionListener)
            //预加载监听
//            mMediaPlayer?.setOnPreparedListener{
//                it.start()
//            }
            //设置是否保持屏幕常亮
            mMediaPlayer?.setScreenOnWhilePlaying(true)
            //同步的方式装载流媒体文件
            mMediaPlayer?.prepare()
            mMediaPlayer?.start()

        }

        /**
         * 视频尺寸改变后调用
         * @param surfaceTexture
         * @param i
         * @param i1
         */
        override fun onSurfaceTextureSizeChanged(surfaceTexture: SurfaceTexture, i: Int, i1: Int) {

        }

        /**
         * SurfaceTexture即将被销毁时调用
         * @param surfaceTexture
         * @return
         */
        override fun onSurfaceTextureDestroyed(surfaceTexture: SurfaceTexture): Boolean {
            surface = null
            mMediaPlayer?.stop()
            mMediaPlayer?.release()
            mMediaPlayer = null
            return true
        }

        /**
         * 通过SurfaceTexture.updateteximage()更新指定的SurfaceTexture时调用
         * @param surfaceTexture
         */
        override fun onSurfaceTextureUpdated(surfaceTexture: SurfaceTexture) {

        }
    }
}
