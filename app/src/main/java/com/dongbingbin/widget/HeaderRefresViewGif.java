package com.dongbingbin.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.dongbingbin.nativeutils.R;
import com.scwang.smart.refresh.layout.api.RefreshComponent;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.simple.SimpleComponent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class HeaderRefresViewGif extends SimpleComponent {
    View refreshView;
    GifImageView lottieAnimationView;
    int current=0;
    GifDrawable gifDrawable;
    protected HeaderRefresViewGif(@NonNull View wrapped) {
        super(wrapped);
    }

    protected HeaderRefresViewGif(@NonNull View wrappedView, @Nullable RefreshComponent wrappedInternal) {
        super(wrappedView, wrappedInternal);
        initUI(getContext());
    }
    public HeaderRefresViewGif(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initUI(getContext());
    }
    protected HeaderRefresViewGif(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(getContext());
    }

    private void initUI(Context context){
        refreshView = LayoutInflater.from(context).inflate(R.layout.header_logo_refresh_gif,null);
        lottieAnimationView = refreshView.findViewById(R.id.animationView);

        try {
            GifDrawable gifDrawable= new GifDrawable(context.getAssets(),"test.gif");
            lottieAnimationView.setBackground(gifDrawable);
            gifDrawable.stop();
        }catch (Exception ex1){
            ex1.printStackTrace();
        }


        addView(refreshView);
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        super.onStartAnimator(refreshLayout, height, maxDragHeight);
        System.out.println("dongbingbin onStartAnimator");
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        super.onMoving(isDragging, percent, offset, height, maxDragHeight);
        System.out.println("dongbingbin onMoving:"+offset);
        if(isDragging) {
            current = offset;
            gifDrawable.seekTo(current);
        }
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        System.out.println("dongbingbin onFinish");
        gifDrawable.seekTo(0);
        gifDrawable.stop();
        current=0;
        return super.onFinish(refreshLayout, success);

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        super.onReleased(refreshLayout, height, maxDragHeight);
        System.out.println("dongbingbin onReleased");

//        lottieAnimationView.clearAnimation();
//        lottieAnimationView.setProgress(current);
        gifDrawable.start();
    }
}
