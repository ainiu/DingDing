package com.baidu.dingding.adapter;

import android.widget.AbsListView;

/**
 * Created by Administrator on 2015/11/28. 滚动回调监听
 */
public interface OnAdapterScrollListener extends AbsListView.OnScrollListener {
    void onTopWhenScrollIdle(AbsListView view);

    void onBottomWhenScrollIdle(AbsListView view);
}
