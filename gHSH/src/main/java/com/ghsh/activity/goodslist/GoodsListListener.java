package com.ghsh.activity.goodslist;

import android.widget.AbsListView;

import com.ghsh.code.bean.TGoods;

public interface GoodsListListener {
	/**滚动监听*/
	public void onGoodsListViewScroll(int firstVisibleItem,int visibleItemCount, int totalItemCount);
	/**上拉刷新*/
	public void onGoodsListViewPullUpToRefresh();
	/**单击商品*/
	public void onGoodsListViewClickItem(TGoods goods);
}
