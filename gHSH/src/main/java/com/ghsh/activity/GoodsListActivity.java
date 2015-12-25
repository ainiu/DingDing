package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.activity.goodslist.GoodsGridView;
import com.ghsh.activity.goodslist.GoodsListListener;
import com.ghsh.activity.goodslist.GoodsListViewAbstract;
import com.ghsh.activity.view.TabChildView;
import com.ghsh.adapter.GoodsListViewAdapter;
import com.ghsh.code.bean.TGoods;
import com.ghsh.code.query.BaseQuery;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.GoodsModel;
import com.ghsh.dialog.DProgressDialog;

/**
 * 商品列表页
 * */
public class GoodsListActivity extends BaseActivity implements OnClickListener,GoodsListListener,GoodsListViewAdapter.Listener{
	private TextView titleView;
	private ImageView topButton;//置顶按钮
	private TabChildView tabChildView1, tabChildView2, tabChildView3;
	private LinearLayout toolBarLayout;
	private GoodsListViewAdapter adapter=new GoodsListViewAdapter(this, new ArrayList<TGoods>());
	//布局
	private GoodsListViewAbstract goodsListViewAbstract;
	private GoodsModel goodsModel;
	
	private DProgressDialog progressDialog;
	private int currentPage=1;//当前页
	private String sortName=BaseQuery.goods_list_sort_sales,sort=BaseQuery.DESC;//排序 默认时间
	private List<TabChildView> toolBarCellHolderList=new ArrayList<TabChildView>();
	//查询条件
	private String categoryID,keyword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goodslist);
		this.initView();
		this.initListener();
		goodsModel=new GoodsModel(this);
		goodsModel.addResponseListener(new GoodsResponse(this));
		Intent intent=getIntent();
		categoryID=intent.getStringExtra("categoryID");
		keyword=intent.getStringExtra("keyword");
		this.doClickToolBarItem(tabChildView1);
	}
	
	private void resetData(){
		progressDialog.show();
		currentPage=1;
		adapter.clearAllList();
		goodsModel.findGoodsList(categoryID, keyword, currentPage,sortName,sort);
	}
	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.goods_list_title);
		topButton=(ImageView)this.findViewById(R.id.good_list_top_button);
		
		toolBarLayout=(LinearLayout)this.findViewById(R.id.goods_list_toolbar_layout);
		LinearLayout.LayoutParams toolberParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
		toolberParams.weight=1;
		toolberParams.gravity=Gravity.CENTER;
		tabChildView1=new TabChildView(this);
		tabChildView2=new TabChildView(this);
		tabChildView3=new TabChildView(this);
		tabChildView1.setTextView(R.string.goods_list_top_1);
		tabChildView2.setTextView(R.string.goods_list_top_2);
		tabChildView3.setTextView(R.string.goods_list_top_3);
		tabChildView2.getImageView().setVisibility(View.VISIBLE);
		tabChildView3.getImageView().setVisibility(View.VISIBLE);
		tabChildView2.setImageView(R.drawable.goods_list_toolbarr_down_arrow);
		tabChildView3.setImageView(R.drawable.goods_list_toolbarr_down_arrow);
		toolBarLayout.addView(tabChildView1,toolberParams);
		toolBarLayout.addView(tabChildView2,toolberParams);
		toolBarLayout.addView(tabChildView3,toolberParams);
		
		toolBarCellHolderList.add(tabChildView1);
		toolBarCellHolderList.add(tabChildView2);
		toolBarCellHolderList.add(tabChildView3);
		
		//网格布局
		goodsListViewAbstract=new GoodsGridView(this, adapter);
		goodsListViewAbstract.addListener(this);
		
		//列表布局
//		goodsListViewAbstract=new GoodsListView(this, adapter);
//		goodsListViewAbstract.addListener(this);
	}

	private void initListener() {
		topButton.setOnClickListener(this);
		adapter.addListener(this);
		tabChildView1.setOnClickListener(this);
		tabChildView2.setOnClickListener(this);
		tabChildView3.setOnClickListener(this);
	}

	private void doClickToolBarItem(TabChildView holder){
		for(TabChildView temp:toolBarCellHolderList){
			if(holder==temp){
				temp.setSelected(true);
				if(temp.getTag()==null){
					holder.setTag(BaseQuery.ASC);
				}
				if(temp.getTag().equals(BaseQuery.ASC)){
					//降序
					holder.setImageView(R.drawable.goods_list_toolbarr_down_arrow);
					holder.setTag(BaseQuery.DESC);
				}else{
					//升序
					holder.setImageView(R.drawable.goods_list_toolbarr_up_arrow);
					holder.setTag(BaseQuery.ASC);
				}
				if(holder.getImageView().getVisibility()==View.GONE){
					//如果是隐藏--降序
					holder.setTag(BaseQuery.DESC);
				}
			}else{
				temp.setSelected(false);
				temp.setImageView(R.drawable.goods_list_toolbarr_down_arrow);
			}
		}
		sort=holder.getTag()+"";
		this.resetData();
	}
	@Override
	public void onClick(View v) {
		if (v == tabChildView1) {
			sortName=BaseQuery.goods_list_sort_sales;
			this.doClickToolBarItem(tabChildView1);
		} else if (v == tabChildView2) {
			sortName=BaseQuery.goods_list_sort_price;
			this.doClickToolBarItem(tabChildView2);
		} else if (v == tabChildView3) {
			sortName=BaseQuery.goods_list_sort_add_time;
			this.doClickToolBarItem(tabChildView3);
		}else if(v==topButton){
			//返回顶部
			goodsListViewAbstract.onScrollViewTop();
		}
	}
	
	class GoodsResponse extends DResponseAbstract{
		public GoodsResponse(Activity activity) {
			super(activity);
		}
		@Override
		public void onResponseStart() {
			progressDialog.dismiss();
		}
		@Override
		protected void onResponseEnd(boolean isMoreData) {
			goodsListViewAbstract.onStopUpRefresh(isMoreData);
		}
		@Override
		protected void onResponseSuccess(ReturnBean bean, String message) {
			if(bean.getType()==DVolleyConstans.METHOD_QUERYALL){
				//商品列表查询结果
				List<TGoods> list=(List<TGoods>) bean.getObject();
				if(list.size()==0&&adapter.getList().size()!=0){
					this.setMoreData(false);
					return;
				}
				adapter.addAllList(list);
				currentPage++;
			}
		}
		@Override
		protected boolean isShowEmptyPageView() {
			return adapter.getList().size()==0;
		}
		@Override
		protected void onRefresh() {
			GoodsListActivity.this.resetData();
		}
	}
	
	@Override
	public void onGoodsListViewScroll(int firstVisibleItem,int visibleItemCount, int totalItemCount) {
		if (firstVisibleItem >= 1) {
			topButton.setVisibility(View.VISIBLE);
		} else {
			topButton.setVisibility(View.GONE);
		}
	}
	@Override
	public void onGoodsListViewPullUpToRefresh() {
		goodsModel.findGoodsList(categoryID, keyword, currentPage,sortName,sort);
	}
	@Override
	public void onGoodsListViewClickItem(TGoods goods) {
		Intent intent=new Intent(this,GoodsDetailsActivity.class);
		intent.putExtra("goodsID", goods.getGoodsID());
		this.startActivity(intent);
	}
	@Override
	public void addCart(TGoods goods) {
		
	}
	
}
