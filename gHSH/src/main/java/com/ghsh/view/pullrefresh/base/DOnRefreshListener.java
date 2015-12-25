package com.ghsh.view.pullrefresh.base;

import android.view.View;

public interface DOnRefreshListener {
	 /**
     * 下拉松手后会被调用
     * @param refreshView 刷新的View
     */
   public void onPullDownToRefresh();
    
    /**
     * 加载更多时会被调用或上拉时调用
     * @param refreshView 刷新的View
     */
   public void onPullUpToRefresh();
}
