package com.dongbingbin.nativeutils

import android.content.Intent
import android.graphics.SurfaceTexture
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Surface
import android.view.TextureView
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

//        video_view.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/raw/kk"))
//        video_view.start()
    }


    /**
     * 定义一个线程，用于播发视频
     */
    private inner class PlayerVideoThread : Thread() {
        override fun run() {
            try {
                mMediaPlayer = MediaPlayer.create(this@SplashActivity,R.raw.kk)
                //把res/raw的资源转化为Uri形式访问(android.resource://)
                //val uri = Uri.parse("android.resource://com.github.davidji80.videoplayer/" + R.raw.ansen)
                //设置播放资源(可以是应用的资源文件／url／sdcard路径)
                //mMediaPlayer.setDataSource(this@SplashActivity, uri)
                //设置渲染画板
                mMediaPlayer?.setSurface(surface)
                //设置播放类型
                //mMediaPlayer?.setAudioStreamType(AudioManager.)
                //播放完成监听
                mMediaPlayer?.setOnCompletionListener(onCompletionListener)
                //预加载监听
                mMediaPlayer?.setOnPreparedListener{
                    it.start()
                }
                //设置是否保持屏幕常亮
                mMediaPlayer?.setScreenOnWhilePlaying(true)
                //同步的方式装载流媒体文件
                mMediaPlayer?.prepare()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
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
            PlayerVideoThread().start()
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
