package com.dongbingbin.nativeutils.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import static java.lang.Math.abs;
import static java.lang.Math.floor;


public class ScrollingImageView extends View {
    private  int speed;
    private  Bitmap bitmap;
    private  boolean delayEnd;
    private  int firstFrameDraw = 0;

    private Rect clipBounds = new Rect();
    private int offset = 0;
 
    private boolean isStarted;
 
    public ScrollingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(bitmap != null){
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), bitmap.getHeight());
        }else {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        }

    }




    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas == null) {
            return;
        }



        if(bitmap == null){
            return;
        }
        canvas.getClipBounds(clipBounds);
 
        int normalizedOffset = offset;
        int layerWidth = bitmap.getWidth();
        if (offset < -layerWidth) {
            offset += (int) (floor(abs(normalizedOffset) / (float) layerWidth) * layerWidth);
        }

        if(abs(normalizedOffset)+clipBounds.width() >= bitmap.getWidth()){
            stop();
        }

        int left = offset;
        while (left < clipBounds.width()) {
            canvas.drawBitmap(bitmap, getBitmapLeft(layerWidth, left), 0, null);
            left += layerWidth;
            System.out.println("dongbingbin:"+left);
        }


        if (isStarted) {

            if(firstFrameDraw > 1 && !delayEnd){

                System.out.println("ScrollingImageView onDraw return");
                postInvalidateOnAnimation();
                return;
            }

            offset -= speed;
            postInvalidateOnAnimation();
            System.out.println("ScrollingImageView onDraw");
            firstFrameDraw ++;
        }



    }
 
    private float getBitmapLeft(int layerWidth, int left) {
        float bitmapLeft = left;
        if (speed < 0) {
            bitmapLeft = clipBounds.width() - layerWidth - left;
        }
        return bitmapLeft;
    }
 
    /**
     * Start the animation
     */
    public void start() {
        if (!isStarted) {
            isStarted = true;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    delayEnd = true;
                }
            },800);
            postInvalidateOnAnimation();
        }
    }
 
    /**
     * Stop the animation
     */
    public void stop() {
        if (isStarted) {
            isStarted = false;
            firstFrameDraw = 0;
            invalidate();
        }
    }

    public void setBitMap(Bitmap bitmap, int i) {
        this.bitmap = bitmap;
        this.speed = i;

    }
}