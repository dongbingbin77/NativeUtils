package com.dongbingbin.widget

import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.dongbingbin.nativeutils.R

class MyDialog :Dialog {


    private var rootView: View? =null;

    private var valueAnimator:ValueAnimator = ValueAnimator.ofFloat(500F,0F);

    constructor(context: Context) : super(context){
        init(context)
    }
    constructor(context: Context, themeResId: Int) : super(context, themeResId){
        init(context)
    }
    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener){
        init(context)
    }



    fun init(context: Context) {
        var temp:View = LayoutInflater.from(context).inflate(R.layout.activity_dialog, null);
        rootView = temp;
        setContentView(temp);
    }

    override fun show() {
        super.show()

        //window.enterTransition
        rootView?.rootView?.translationX=500F

        valueAnimator.addUpdateListener {
            rootView?.rootView?.translationX = it.animatedValue as Float;
        }
        valueAnimator.duration=5800;
        valueAnimator.start()
    }

    override fun hide() {
        super.hide()
    }
}