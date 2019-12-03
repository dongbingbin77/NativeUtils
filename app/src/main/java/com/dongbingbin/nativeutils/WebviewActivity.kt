package com.dongbingbin.nativeutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.webkit.WebView
import com.dongbingbin.nativeutils.utils.WebPools
import kotlinx.android.synthetic.main.activity_webview.*


class WebviewActivity : AppCompatActivity() {

    lateinit var webview:WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        webview = WebPools.getInstance().acquireWebView(this@WebviewActivity);
        webviewcontainer.addView(webview)
        webview.loadUrl("https://zhidao.baidu.com/")

    }

    override fun onDestroy() {
        WebPools.getInstance().recycle(webview)
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_in,R.anim.anim_out)
    }
}
