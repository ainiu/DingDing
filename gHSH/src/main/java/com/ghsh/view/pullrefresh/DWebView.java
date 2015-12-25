package com.ghsh.view.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.webkit.WebView;

import com.ghsh.view.pullrefresh.base.DRefreshBase;

/**
 * 封装了WebView的下拉刷新
 * 
 * @author jason
 */
public class DWebView extends DRefreshBase<WebView> {
    public DWebView(Context context) {
    	super(context);
    }
    public DWebView(Context context, AttributeSet attrs) {
    	super(context, attrs);
    }
    
    public DWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected WebView createRefreshableView(Context context, AttributeSet attrs) {
        WebView webView = new WebView(context);
        return webView;
    }

    /**
     * @see com.nj1s.lib.pullrefresh.PullToRefreshBase#isReadyForPullDown()
     */
    @Override
    protected boolean isReadyForPullDown() {
        return refreshableView.getScrollY() == 0;
    }
    @Override
    protected boolean isReadyForPullUp() {
        float exactContentHeight = FloatMath.floor(refreshableView.getContentHeight() * refreshableView.getScale());
        return refreshableView.getScrollY() >= (exactContentHeight - refreshableView.getHeight());
    }
}
