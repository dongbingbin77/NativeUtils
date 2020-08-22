package com.dongbingbin.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.dongbingbin.nativeutils.R;
import com.scwang.smart.refresh.layout.api.RefreshComponent;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.simple.SimpleComponent;

public class HeaderRefresView extends SimpleComponent {
    View refreshView;
    LottieAnimationView lottieAnimationView;
    float current=0F;
    protected HeaderRefresView(@NonNull View wrapped) {
        super(wrapped);
    }

    protected HeaderRefresView(@NonNull View wrappedView, @Nullable RefreshComponent wrappedInternal) {
        super(wrappedView, wrappedInternal);
        initUI(getContext());
    }
    public HeaderRefresView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initUI(getContext());
    }
    protected HeaderRefresView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(getContext());
    }

    private void initUI(Context context){
        refreshView = LayoutInflater.from(context).inflate(R.layout.header_logo_refresh,null);
        lottieAnimationView = refreshView.findViewById(R.id.animationView);
        lottieAnimationView.setProgress(0F);
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
        System.out.println("dongbingbin onMoving:"+percent);
        if(percent/ 3<0.35F&&isDragging) {
            current = percent / 3;
            lottieAnimationView.setProgress(current);
        }
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        System.out.println("dongbingbin onFinish");
        lottieAnimationView.pauseAnimation();
//        lottieAnimationView.clearAnimation();
        lottieAnimationView.setProgress(0F);
        lottieAnimationView.setSpeed(1);
        lottieAnimationView.setMinAndMaxProgress(0F,1F);
        current=0F;
        return super.onFinish(refreshLayout, success);

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        super.onReleased(refreshLayout, height, maxDragHeight);
        System.out.println("dongbingbin onReleased");

//        lottieAnimationView.clearAnimation();
//        lottieAnimationView.setProgress(current);
        lottieAnimationView.setMinAndMaxProgress(lottieAnimationView.getProgress(),1.0F);
        lottieAnimationView.setRepeatCount(-1);
        lottieAnimationView.setSpeed(3);
        lottieAnimationView.playAnimation();
    }
}
