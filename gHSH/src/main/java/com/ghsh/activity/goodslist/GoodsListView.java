package com.ghsh.activity.goodslist;

import android.app.Activity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ghsh.adapter.GoodsListViewAdapter;
import com.ghsh.code.bean.TGoods;
import com.ghsh.view.pullrefresh.DListView;
import com.ghsh.view.pullrefresh.base.DOnRefreshListener;
import com.ghsh.R;

public class GoodsListView extends GoodsListViewAbstract{
	private DListView refreshListView;
	private ListView listView;
	public GoodsListView(Activity activity,GoodsListViewAdapter adapter){
		super(activity, adapter);
		this.initView();
		this.initListener();
	}
	
	private void initView(){
		adapter.setLayout(R.layout.activity_goodslist_listview_item);
		refreshListView= (DListView) activity.findViewById(R.id.goods_refresh_listView);
		refreshListView.setPullDownEnabled(false);
		refreshListView.setPullUpEnabled(true);
		refreshListView.setVisibility(View.VISIBLE);
		listView=refreshListView.getRefreshableView();
		listView.setDivider(activity.getResources().getDrawable(R.drawable.line_dotted));
		listView.setDividerHeight(2);
		listView.setAdapter(adapter);
		
	}
	private void initListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(listener!=null){
					TGoods goods=adapter.getItem(position);
					listener.onGoodsListViewClickItem(goods);
				}
			}
		});
		listView.setOnScrollListener(new OnScrollListener() {
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
		refreshListView.setOnRefreshListener(new DOnRefreshListener() {
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
		if (!listView.isStackFromBottom()) {
			listView.setStackFromBottom(true);
		}
		listView.setStackFromBottom(false);
	}
	@Override
	public void onStopUpRefresh(boolean isMoreData){
		refreshListView.onStopUpRefresh(isMoreData);
	}
}
