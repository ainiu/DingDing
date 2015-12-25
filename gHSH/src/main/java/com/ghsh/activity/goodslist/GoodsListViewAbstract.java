package com.ghsh.activity.goodslist;

import com.ghsh.adapter.GoodsListViewAdapter;

import android.app.Activity;

public abstract class GoodsListViewAbstract {
	protected Activity activity;
	protected GoodsListViewAdapter adapter;
	protected GoodsListListener listener;
	public GoodsListViewAbstract(Activity activity,GoodsListViewAdapter adapter){
		this.activity=activity;
		this.adapter=adapter;
	}
	
	public void addListener(GoodsListListener listener){
		this.listener=listener;
	}
	
	/**滚动到顶部*/
	public abstract void onScrollViewTop();
	/**取消上拉刷新*/
	public abstract void onStopUpRefresh(boolean isMoreData);
}
