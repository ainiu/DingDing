package com.baidu.dingding.view.MyView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2015/12/15.
 */
public class CustomViewPager extends ViewPager {
    private boolean isCanScroll = false;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }


    @Override
    public void scrollTo(int x, int y) {
        if (isCanScroll) {
            super.scrollTo(x, y);
        }
    }
    @Override
    public void setCurrentItem(int item) {
// TODO Auto-generated method stub
        super.setCurrentItem(item);
    }
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
// TODO Auto-generated method stub
        if (isCanScroll) {
            return false;
        } else {
            return super.onTouchEvent(arg0);
        }
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
// TODO Auto-generated method stub
        if (isCanScroll) {
            return false;
        } else {
            return super.onInterceptTouchEvent(arg0);
        }
    }
}
