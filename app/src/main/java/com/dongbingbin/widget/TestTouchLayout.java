/**
 * FileName: TestTouchLayout
 * Author: dongbingbin
 * Date: 2020/7/15 23:55
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.dongbingbin.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * @ClassName: TestTouchLayout
 * @Description: java类作用描述
 * @Author: dongbingbin
 * @Date: 2020/7/15 23:55
 */
public class TestTouchLayout extends LinearLayout {

    private String cname;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public TestTouchLayout(Context context) {
        super(context);
    }

    public TestTouchLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTouchLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("testtouch onTouchEvent" + cname);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println("testtouch onInterceptTouchEvent " + cname);
        return super.onInterceptTouchEvent(ev);
        //return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("testtouch dispatchTouchEvent " + cname);
        return super.dispatchTouchEvent(ev);
    }
}