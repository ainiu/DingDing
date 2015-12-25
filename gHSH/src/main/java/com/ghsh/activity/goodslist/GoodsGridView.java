package com.ghsh.activity.goodslist;

import android.app.Activity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.ghsh.adapter.GoodsListViewAdapter;
import com.ghsh.code.bean.TGoods;
import com.ghsh.view.pullrefresh.DGridView;
import com.ghsh.view.pullrefresh.base.DOnRefreshListener;
import com.ghsh.R;

public class GoodsGridView extends GoodsListViewAbstract{
	private DGridView refreshGridView;
	private GridView gridView;
	
	public GoodsGridView(Activity activity,GoodsListViewAdapter adapter){
		super(activity, adapter);
		this.initView();
		this.initListener();
	}
	
	private void initView(){
		adapter.setLayout(R.layout.activity_goodslist_gridview_item);
		refreshGridView= (DGridView) activity.findViewById(R.id.goods_refresh_gridView);
		refreshGridView.setPullDownEnabled(false);
		refreshGridView.setPullUpEnabled(true);
		refreshGridView.setVisibility(View.VISIBLE);
		gridView=refreshGridView.getRefreshableView();
		gridView.setNumColumns(GridView.AUTO_FIT);
		gridView.setHorizontalSpacing(20);
		gridView.setVerticalSpacing(20);
		gridView.setAdapter(adapter);
	}
	private void initListener() {
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(listener!=null){
					TGoods goods=adapter.getItem(position);
					listener.onGoodsListViewClickItem(goods);
				}
			}
		});
		gridView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
				if(listener!=null){
					listener.onGoodsListViewScroll(firstVisibleItem, visibleItemCount, totalItemCount);
				}
			}
		});
		refreshGridView.setOnRefreshListener(new DOnRefreshListener() {
	        @Override
	        public void onPullDownToRefresh() {
	        	
	        }
	        @Override
	        public void onPullUpToRefresh() {
	        	if(listener!=null){
					listener.onGoodsListViewPullUpToRefresh();
				}
	        }
        });
	}
	
	@Override
	public void onScrollViewTop(){
		if (!gridView.isStackFromBottom()) {
			gridView.setStackFromBottom(true);
		}
		gridView.setStackFromBottom(false);
	}
	@Override
	public void onStopUpRefresh(boolean isMoreData){
		refreshGridView.onStopUpRefresh(isMoreData);
	}
	
	
}
