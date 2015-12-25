package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseHomeActivity;
import com.ghsh.activity.home.HomeFunsBean;
import com.ghsh.adapter.HomeFunGridViewtAdapter;
import com.ghsh.adapter.HomePromotionGridViewtAdapter;
import com.ghsh.code.bean.TGroup;
import com.ghsh.code.bean.TNews;
import com.ghsh.code.bean.TPic;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.view.banner.BannerImageView;
import com.ghsh.view.pullrefresh.DScrollView;
import com.ghsh.view.pullrefresh.base.DOnRefreshListener;
import com.ghsh.view.util.ViewUtils;

public class HomeActivity extends BaseHomeActivity implements OnClickListener{
	private ImageView logoView;
	private TextView titleView;
	private Button msgButton;
	private EditText searchView;
	private DScrollView pullToRefresh;
	private LinearLayout mainView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		this.initView();
		this.initListener();
	}
	private void initView(){
		super.setBackButtonVisible(View.GONE);
		super.setBackExit(true);
		logoView=(ImageView)this.findViewById(R.id.header_back_view);
		logoView.setImageResource(R.drawable.home_logo);
		logoView.setTag("");//logo  wight 120
		logoView.setPadding(0, 0, 0, 0);
		logoView.setMinimumWidth((int)this.getResources().getDimension(R.dimen.width_home_logo_min));
		logoView.setVisibility(View.VISIBLE);
		titleView=(TextView)this.findViewById(R.id.header_title_view);
		titleView.setVisibility(View.GONE);
		msgButton=(Button)this.findViewById(R.id.header_button_view);
		ViewUtils.setButtonDrawables(this, msgButton, R.drawable.home_notice_icon, 1);
//		msgButton.setVisibility(View.VISIBLE);
		searchView=(EditText)this.findViewById(R.id.header_search_view);
		searchView.setFocusable(false);
		View searchLayout=this.findViewById(R.id.header_search_layout);
		searchLayout.setVisibility(View.VISIBLE);
		
		pullToRefresh=(DScrollView)this.findViewById(R.id.home_refresh_scrollView);
		pullToRefresh.setPullDownEnabled(true);
		ScrollView scrollView=pullToRefresh.getRefreshableView();
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		mainView=new LinearLayout(this);
		mainView.setOrientation(LinearLayout.VERTICAL);
		scrollView.addView(mainView,params);
	}
	private void initListener(){
		msgButton.setOnClickListener(this);
		pullToRefresh.setOnRefreshListener(new DOnRefreshListener() {
            @Override
            public void onPullDownToRefresh() {
            	HomeActivity.this.startLoadHomeData();
            }
            @Override
            public void onPullUpToRefresh() {
            }
        });
		searchView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(HomeActivity.this,SearchActivity.class);
				HomeActivity.this.startActivity(intent);
			}
		});
	}
	@Override
	protected void loadHomeData(JSONArray jsonArray) throws Exception {
		pullToRefresh.onStopDownRefresh(null);
		if(jsonArray==null||jsonArray.length()==0){
			return;
		}
		mainView.removeAllViews();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			String type=JSONUtil.getString(jsonObject, "type");
			JSONArray data=JSONUtil.getJSONArray(jsonObject, "data");
			if(data==null||data.length()==0){
				continue;
			}
			if(type.equals("ad")){
				//广告
				this.createAd(data);
			}else if(type.equals("article_notice_list")){
				//文章--公告
				this.findArticleNoticeList(data);
			}else if(type.equals("navigator")){
				//功能按钮
				this.createFuns(data);
			}else if(type.equals("category_goods_list")){
				//分类商品
				String cat_id=JSONUtil.getString(jsonObject, "cat_id");
				String cat_name=JSONUtil.getString(jsonObject, "cat_name");
				if(data.length()==3){
					//三个商品显示方式
					this.createCategoryGoods3(cat_id,cat_name,data);
				}
			}else if(type.equals("goods_list_best")){
				//精品
			}else if(type.equals("goods_list_new")){
				//新品
			}else if(type.equals("goods_list_hot")){
				//热卖
			}else if(type.equals("goods_list_promotion")){
				//促销
			}else if(type.equals("group_time_promotion_list")){
				//团购--限时促销
				this.findGroupTimePromotionList(data);
			}
		}
	}
	//广告
	private void createAd(JSONArray bannersArray) throws JSONException{
		final List<TPic> picList=new ArrayList<TPic>();
		for(int i=0;i<bannersArray.length();i++){
			JSONObject bannersObject=bannersArray.getJSONObject(i);
			String picID=JSONUtil.getString(bannersObject, "id");
			String imageUrl=JSONUtil.getString(bannersObject, "image_url");
			String isfixed=JSONUtil.getString(bannersObject, "isfixed");
			String href=JSONUtil.getString(bannersObject, "href");
			TPic pic=new TPic();
			pic.setPicID(picID);
			pic.setHref(href);
			pic.setIsfixed(isfixed);
			if(imageUrl!=null&&!imageUrl.equals("")){
				pic.setImageUrl(DVolleyConstans.getServiceHost(imageUrl));
			}
			picList.add(pic);
		}
		if(picList.size()>1){
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,this.getResources().getDimensionPixelSize(R.dimen.home_banner_height));
			BannerImageView bannerImageView=(BannerImageView)this.getView(R.layout.activity_home_bannery);
			bannerImageView.createPicView(picList);
			bannerImageView.addListener(new BannerImageView.Listener() {
				@Override
				public void OnClickPicView(TPic pics) {
					HomeActivity.this.startActivity(pics.getIsfixed(),pics.getHref());
				}
			});
			mainView.addView(bannerImageView,params);
		}else{
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,this.getResources().getDimensionPixelSize(R.dimen.home_image_height));
			params.topMargin=10;
			ImageView imageView=(ImageView)this.getView(R.layout.activity_home_imageview);
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					HomeActivity.this.startActivity(picList.get(0).getIsfixed(),picList.get(0).getHref());
				}
			});
			DVolley.getImage(picList.get(0).getImageUrl(), imageView);
			mainView.addView(imageView,params);
		}
	}
	
	//分类商品--3个商品
	private void createCategoryGoods3(String cateID,String cateName,JSONArray goodsArray) throws JSONException{
		View view=this.getView(R.layout.activity_home_catagory_goods_3_item);
		TextView nameView=(TextView)view.findViewById(R.id.home_category_name);
		TextView moreView=(TextView)view.findViewById(R.id.home_category_more);
		ImageView imageView1=(ImageView)view.findViewById(R.id.home_item_image1);
		ImageView imageView2=(ImageView)view.findViewById(R.id.home_item_image2);
		ImageView imageView3=(ImageView)view.findViewById(R.id.home_item_image3);
		
		moreView.setOnClickListener(new GoodsListOnClickListener(cateID));
		
		nameView.setText(cateName);
		for(int i=0;i<goodsArray.length();i++){
			JSONObject goodsObject=goodsArray.getJSONObject(i);
			String goods_id=JSONUtil.getString(goodsObject, "goods_id");
			String goods_img=JSONUtil.getString(goodsObject, "goods_img");
			if(i==0){
				DVolley.getImage(DVolleyConstans.getServiceHost(goods_img), imageView1);
				imageView1.setOnClickListener(new GoodsOnClickListener(goods_id));
			}else if(i==1){
				DVolley.getImage(DVolleyConstans.getServiceHost(goods_img), imageView2);
				imageView2.setOnClickListener(new GoodsOnClickListener(goods_id));
			}else if(i==2){
				DVolley.getImage(DVolleyConstans.getServiceHost(goods_img), imageView3);
				imageView3.setOnClickListener(new GoodsOnClickListener(goods_id));
			}
		}
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		params.topMargin=30;
		mainView.addView(view,params);
	}

	//功能按钮
	private void createFuns(JSONArray funArray) throws JSONException{
		List<HomeFunsBean> list=new ArrayList<HomeFunsBean>();
		for(int i=0;i<funArray.length();i++){
			JSONObject funObject=funArray.getJSONObject(i);
			String id=JSONUtil.getString(funObject, "id");
			String name=JSONUtil.getString(funObject, "name");
			String imageUrl=JSONUtil.getString(funObject, "imageUrl");
			String isfixed=JSONUtil.getString(funObject, "isfixed");
			String href=JSONUtil.getString(funObject, "href");
			
			HomeFunsBean homeFunsBean=new HomeFunsBean();
			homeFunsBean.text=name;
			homeFunsBean.imageUrl=DVolleyConstans.getServiceHost(imageUrl);
			homeFunsBean.isfixed=isfixed;
			homeFunsBean.href=href;
			list.add(homeFunsBean);
		}
		GridView gridView=(GridView)this.getView(R.layout.activity_home_gridview);
		gridView.setNumColumns(3);
		gridView.setBackgroundResource(R.color.white);
		HomeFunGridViewtAdapter adapter=new HomeFunGridViewtAdapter(this,list);
		gridView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		gridView.setOnItemClickListener(new FunOnClickListener(adapter));
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,this.getResources().getDimensionPixelSize(R.dimen.home_banner_height));
		mainView.addView(gridView,params);
	}
	
	//文章--公告
	private void findArticleNoticeList(JSONArray funArray) throws JSONException{
		List<TNews> list=new ArrayList<TNews>();
		for(int i=0;i<funArray.length();i++){
			JSONObject funObject=funArray.getJSONObject(i);
			String newID=JSONUtil.getString(funObject, "article_id");
			String title=JSONUtil.getString(funObject, "title");
			TNews news=new TNews();
			news.setNewID(newID);
			news.setTitle(title);
			list.add(news);
		}
		if(list.size()>0){
			View view=this.getView(R.layout.activity_home_article_notice);
			TextView textView=(TextView)view.findViewById(R.id.home_article_notice);
			textView.setText(list.get(0).getTitle());
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
			mainView.addView(view,params);
		}
		
	}
	
	//团购--限时促销
	private void findGroupTimePromotionList(JSONArray funArray) throws JSONException{
		List<TGroup> list=new ArrayList<TGroup>();
		for(int i=0;i<funArray.length();i++){
			JSONObject funObject=funArray.getJSONObject(i);
			String groupID=JSONUtil.getString(funObject, "act_id");
			String groupName=JSONUtil.getString(funObject, "act_name");
			String shopPrice=JSONUtil.getString(funObject, "shop_price");
			String groupImage=JSONUtil.getString(funObject, "goods_img");
			long countdownTotalTime=JSONUtil.getLong(funObject, "countdown_time_total");
			JSONObject countdownTime=JSONUtil.getJSONObject(funObject, "countdown_time");
			TGroup group=new TGroup();
			group.setGroupID(groupID);
			group.setGroupName(groupName);
			group.setShopPrice(shopPrice);
			group.setCountdownTotalTime(countdownTotalTime);
			group.setGroupImage(DVolleyConstans.getServiceHost(groupImage));
			group.setCountdownDay(JSONUtil.getInt(countdownTime, "day"));
			group.setCountdownHour(JSONUtil.getInt(countdownTime, "hour"));
			group.setCountdownMin(JSONUtil.getInt(countdownTime, "min"));
			group.setCountdownSec(JSONUtil.getInt(countdownTime, "sec"));
			list.add(group);
		}
		View titleView=this.getView(R.layout.activity_home_listview_item_promotion_title);
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		mainView.addView(titleView,params);
		
		GridView gridView=(GridView)this.getView(R.layout.activity_home_gridview);
		gridView.setHorizontalSpacing(10);
		gridView.setVerticalSpacing(10);
		gridView.setNumColumns(2);
		gridView.setBackgroundColor(Color.WHITE);
		HomePromotionGridViewtAdapter adapter=new HomePromotionGridViewtAdapter(this,list);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new GroupOnClickListener(adapter));
		adapter.notifyDataSetChanged();
		params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,this.getResources().getDimensionPixelSize(R.dimen.home_banner_height));
		mainView.addView(gridView,params);
	}
	//商品
	class GoodsOnClickListener implements OnClickListener{
		private String goodsID;
		public GoodsOnClickListener(String goodsID){
			this.goodsID=goodsID;
		}
		@Override
		public void onClick(View v) {
			Intent intent=new Intent(HomeActivity.this,GoodsDetailsActivity.class);
			intent.putExtra("goodsID", goodsID);
			HomeActivity.this.startActivity(intent);
		}
	}
	
	//商品列表
	class GoodsListOnClickListener implements OnClickListener{
		private String categoryID;
		public GoodsListOnClickListener(String categoryID){
			this.categoryID=categoryID;
		}
		@Override
		public void onClick(View v) {
			Intent intent=new Intent(HomeActivity.this,GoodsListActivity.class);
			intent.putExtra("categoryID", categoryID);
			HomeActivity.this.startActivity(intent);
		}
	}
	
	//功能按钮
	class FunOnClickListener implements OnItemClickListener{
		private HomeFunGridViewtAdapter adapter;
		public FunOnClickListener(HomeFunGridViewtAdapter adapter){
			this.adapter=adapter;
		}
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			HomeFunsBean homeFunsBean=adapter.getItem(position);
			HomeActivity.this.startActivity(homeFunsBean.isfixed,homeFunsBean.href);
		}
	}
	//团购
	class GroupOnClickListener implements OnItemClickListener{
		private HomePromotionGridViewtAdapter adapter;
		public GroupOnClickListener(HomePromotionGridViewtAdapter adapter){
			this.adapter=adapter;
		}
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			TGroup group=adapter.getItem(position);
			Intent intent=new Intent(HomeActivity.this,GroupDetailsActivity.class);
			intent.putExtra("groupID", group.getGroupID());
			HomeActivity.this.startActivity(intent);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == msgButton) {
			Intent intent = new Intent(this, MessageActivity.class);
			this.startActivity(intent);
		}
	}
}