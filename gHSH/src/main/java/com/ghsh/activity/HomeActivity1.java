package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseHomeActivity;
import com.ghsh.activity.home.HomeBean;
import com.ghsh.activity.home.HomeFunsBean;
import com.ghsh.activity.home.HomeGoods;
import com.ghsh.adapter.HomeListViewAdapter;
import com.ghsh.code.bean.TGroup;
import com.ghsh.code.bean.TNews;
import com.ghsh.code.bean.TPic;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.view.pullrefresh.DListView;
import com.ghsh.view.pullrefresh.base.DOnRefreshListener;
import com.ghsh.view.util.ViewUtils;

public class HomeActivity1 extends BaseHomeActivity implements OnClickListener{
	private ImageView logoView;
	private TextView titleView;
	private Button msgButton;
	private EditText searchView;
	
	private DListView pullToRefresh;
	private ListView listView;
	private HomeListViewAdapter adapter=new HomeListViewAdapter(this,new ArrayList<HomeBean>());
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home1);
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
		
		pullToRefresh=(DListView)this.findViewById(R.id.home_refresh_listView);
		pullToRefresh.setPullDownEnabled(true);
		listView=pullToRefresh.getRefreshableView();
		listView.setAdapter(adapter);
	}
	private void initListener(){
		msgButton.setOnClickListener(this);
		pullToRefresh.setOnRefreshListener(new DOnRefreshListener() {
            @Override
            public void onPullDownToRefresh() {
            	HomeActivity1.this.startLoadHomeData();
            }
            @Override
            public void onPullUpToRefresh() {
            }
        });
		searchView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(HomeActivity1.this,SearchActivity.class);
				HomeActivity1.this.startActivity(intent);
			}
		});
	}
	@Override
	protected void loadHomeData(JSONArray jsonArray) throws Exception {
		pullToRefresh.onStopDownRefresh(null);
		if(jsonArray==null||jsonArray.length()==0){
			return;
		}
		List<HomeBean> homeBeanList=new ArrayList<HomeBean>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			String type=JSONUtil.getString(jsonObject, "type");
			JSONArray data=JSONUtil.getJSONArray(jsonObject, "data");
			if(data==null||data.length()==0){
				continue;
			}
			if(type.equals("ad")){
				//广告
				List<TPic> picList=this.createAd(data);
				if(picList.size()==1){
					homeBeanList.add(new HomeBean(HomeBean.TYPE_0, picList.get(0)));
				}else{
					homeBeanList.add(new HomeBean(HomeBean.TYPE_1, picList));
				}
			}else if(type.equals("article_notice_list")){
				//文章--公告
				homeBeanList.add(new HomeBean(HomeBean.TYPE_2, this.findArticleNoticeList(data)));
			}else if(type.equals("navigator")){
				//功能按钮
				homeBeanList.add(new HomeBean(HomeBean.TYPE_3, this.createFuns(data)));
			}else if(type.equals("category_goods_list")){
				//分类商品
				String cat_id=JSONUtil.getString(jsonObject, "cat_id");
				String cat_name=JSONUtil.getString(jsonObject, "cat_name");
				if(data.length()==3){
					//三个商品显示方式
					homeBeanList.add(new HomeBean(HomeBean.TYPE_4, this.createCategoryGoods3(cat_id,cat_name,data)));
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
				homeBeanList.add(new HomeBean(HomeBean.TYPE_6, null));
				homeBeanList.add(new HomeBean(HomeBean.TYPE_5, this.findGroupTimePromotionList(data)));
			}
		}
		adapter.setList(homeBeanList);
		adapter.notifyDataSetChanged();
	}
	//广告
	private List<TPic> createAd(JSONArray bannersArray) throws JSONException{
		List<TPic> picList=new ArrayList<TPic>();
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
		return picList;
	}
	
	//分类商品--3个商品
	private List<HomeGoods> createCategoryGoods3(String cateID,String cateName,JSONArray goodsArray) throws JSONException{
		List<HomeGoods> homeGoodsList=new ArrayList<HomeGoods>();
		for(int i=0;i<goodsArray.length();i++){
			JSONObject goodsObject=goodsArray.getJSONObject(i);
			String goodsID=JSONUtil.getString(goodsObject, "goods_id");
			String goodsImage=JSONUtil.getString(goodsObject, "goods_img");
			HomeGoods homeGoods=new HomeGoods();
			homeGoods.setCatID(cateID);
			homeGoods.setCatName(cateName);
			homeGoods.setGoodsID(goodsID);
			homeGoods.setGoodsImage(DVolleyConstans.getServiceHost(goodsImage));
			homeGoodsList.add(homeGoods);
		}
		return homeGoodsList;
	}

	//功能按钮
	private List<HomeFunsBean> createFuns(JSONArray funArray) throws JSONException{
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
		return list;
	}
	
	//文章--公告
	private List<TNews> findArticleNoticeList(JSONArray funArray) throws JSONException{
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
		return list;
	}
	//团购--限时促销
	private List<TGroup> findGroupTimePromotionList(JSONArray funArray) throws JSONException{
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
		return list;
	}
	

	@Override
	public void onClick(View v) {
		if (v == msgButton) {
			Intent intent = new Intent(this, MessageActivity.class);
			this.startActivity(intent);
		}
	}
}