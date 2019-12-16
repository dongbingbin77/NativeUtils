package com.dongbingbin.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.dongbingbin.nativeutils.R;


/**
 * Created by wlf on 2018/11/5.
 * 16:32
 */
public class MoveImageView extends AppCompatImageView {

    private boolean isVerticalMove;//是否是竖直运动
    private float mTop;//屏幕顶端
    private float mLeft;//屏幕顶端
    private Drawable mDrawable;
    private int mExpendingHeight;
    private boolean isMoveUp;//是否可以向上移动


    private MoveListener mListener;

    public MoveImageView(Context context) {
        super(context);
        setMove(context, null);

    }

    public MoveImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setMove(context, attrs);
    }

    public void registerMoveListener(MoveListener moveListener){
        this.mListener =moveListener;
    }

    public void stopMove(){
        this.isVerticalMove = false;
    }

    /**
     * 初始化移动参数
     *
     * @param context :上下文
     * @param attrs   :属性
     */
    private void setMove(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MoveImageView);
        String direction = typedArray.getString(R.styleable.MoveImageView_direction);
        if (direction == null) {
            throw new RuntimeException("You don't set direction properties,If you don't want to do that." +
                    "You can use ordinary ImageView instead");
        } else if (direction.equalsIgnoreCase("vertical")) {
            isVerticalMove = true;
        } else if (direction.equalsIgnoreCase("horizontal")) {
            isVerticalMove = false;
        } else {
            throw new RuntimeException("Direction attribute set is not valid,It is only allowed to set to vertical or horizontal");
        }
        mDrawable = getDrawable();
        typedArray.recycle();
        MoveHandler moveHandler=new MoveHandler();
        moveHandler.sendEmptyMessageDelayed(1,1000);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //if(drawCount<=1) {

        if(mDrawable.getBounds().bottom!=mExpendingHeight) {


            mDrawable.setBounds(0, 0, getMeasuredWidth(), mExpendingHeight);


        }

        if (isVerticalMove) {
            canvas.translate(0.0f, mTop);
        } else {
            //canvas.translate(mLeft, 0.0f);
            canvas.translate(0.0f, mTop);
        }
        mDrawable.draw(canvas);
        //System.out.println("dongbingbin mTop:" + mTop);
//        }else{
//            mDrawable.setBounds(0, 0, getMeasuredWidth(), mExpendingHeight);
//            mDrawable.draw(canvas);
//        }


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (isVerticalMove) {
            mExpendingHeight = getMeasuredHeight() + 1000;

        } else {

        }
        mDrawable.setBounds(0, 0, getMeasuredWidth(), mExpendingHeight);
    }


    private class MoveHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isVerticalMove){
                if (isMoveUp){
                    if (mTop<=getMeasuredHeight()-mExpendingHeight){
                        //说明已经移动到了顶端
                        mTop +=2;
                        isMoveUp=false;

                        if(mListener!=null){
                            mListener.moveToBottom();
                        }
                    }else {
                        mTop-=2;
                    }
                }
                else {
                    if (mTop==0){ //说明已经移动到了底端
                        isMoveUp=true;
                        mTop -=2;

                    }else {
                        mTop +=2;
                    }
                }
            }

            //Log.e("fafa",mTop+"");
            if(isVerticalMove) {
                invalidate();
                sendEmptyMessageDelayed(1, 10);
            }
        }
    }

    public interface MoveListener{
        void moveToBottom();
    }
}



