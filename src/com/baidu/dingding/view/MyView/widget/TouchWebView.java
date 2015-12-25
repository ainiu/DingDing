package com.baidu.dingding.view.MyView.widget;

import android.content.Context;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by Administrator on 2015/12/10.
 */
public class TouchWebView extends WebView {
    boolean mIgnoreTouchCancel;

    public void ignoreTouchCancel (boolean val) {
        mIgnoreTouchCancel = val;
    }

    public TouchWebView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            onScrollChanged(getScrollX(), getScrollY(), getScrollX(), getScrollY());
        }
        // 第一种不完美解决方案
            /*
            boolean ret = super.onTouchEvent(ev);
            if (mPreventParentTouch) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        requestDisallowInterceptTouchEvent(true);
                        ret = true;
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        requestDisallowInterceptTouchEvent(false);
                        mPreventParentTouch = false;
                        break;
                }
            }
            return ret;
             */
        // 第二种完美解决方案
        return action == MotionEvent.ACTION_CANCEL && mIgnoreTouchCancel || super.onTouchEvent(ev);
    }
}
