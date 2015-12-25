package com.ghsh.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.GoodsDetailsActivity;
import com.ghsh.activity.GoodsListActivity;
import com.ghsh.activity.GroupDetailsActivity;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.home.HomeBean;
import com.ghsh.activity.home.HomeFunsBean;
import com.ghsh.activity.home.HomeGoods;
import com.ghsh.code.bean.TGroup;
import com.ghsh.code.bean.TNews;
import com.ghsh.code.bean.TPic;
import com.ghsh.code.volley.DVolley;
import com.ghsh.view.banner.BannerImageView;

public class HomeListViewAdapter extends AbstractBaseAdapter<HomeBean> {
	public HomeListViewAdapter(Context context, List<HomeBean> list) {
		super(context, list);
	}
	
	@Override
	public int getViewTypeCount() {
		return HomeBean.TYPE_COUNT;
	}
//	返回值必须大于等于0，并且小于类型的个数。为什么是这个范围，是因为
//	在ListView里有一个数据，用来缓存已经使用过的Item View，详细信息自己看源码
	@Override
	public int getItemViewType(int position) {
	  if (list != null && position < list.size()) {
		  return list.get(position).getItemType();
      }
	  return super.getItemViewType(position);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final HomeBean homeBean=list.get(position);
		int currentType=this.getItemViewType(position);
		switch(currentType){
			case HomeBean.TYPE_0:
				//一张广告
				convertView=this.createAdOne(convertView,homeBean);
				break;
			case HomeBean.TYPE_1:
				//多张广告
				convertView=this.createAdMore(convertView,homeBean);
				break;
			case HomeBean.TYPE_2:
				//公告
				convertView=this.createArticleNotice(convertView,homeBean);
				break;
			case HomeBean.TYPE_3:
				//功能按钮
				convertView=this.createFuns(convertView,homeBean);
				break;
			case HomeBean.TYPE_4:
				//三个分类商品
				convertView=this.createGoods3(convertView,homeBean);
				break;
			case HomeBean.TYPE_5:
				//团购--限时促销
				convertView=this.createGroupTimePromotion(convertView,homeBean);
				break;
			case HomeBean.TYPE_6:
				//团购--限时促销
				convertView=this.createGroupTimePromotionTitle(convertView,homeBean);
				break;
		}
		return convertView;
	}

	
	//一张广告
	private View createAdOne(View convertView,HomeBean homeBean){
		AdHolder holder=null;
		TPic pic=(TPic)homeBean.getObject();
		if (convertView == null) {
			holder = new AdHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_home_listview_item_ad, null);
			holder.adImageView=(ImageView)convertView.findViewById(R.id.home_listview_ad_view);
			convertView.setTag(holder);
		}else{
			holder = (AdHolder) convertView.getTag();
		}
		DVolley.getImage(pic.getImageUrl(),holder.adImageView,R.drawable.default_image);
		holder.adImageView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.scale));
		return convertView;
	}
	//多张广告
	private View createAdMore(View convertView,HomeBean homeBean){
		AdHolder holder=null;
		List<TPic> picList=(List<TPic>)homeBean.getObject();
		if (convertView == null) {
			holder = new AdHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_home_listview_item_bannery, null);
			holder.bannerView=(BannerImageView)convertView.findViewById(R.id.home_listview_banner_view);
			convertView.setTag(holder);
		}else{
			holder = (AdHolder) convertView.getTag();
		}
		holder.bannerView.createPicView(picList);
		return convertView;
	}
	//文章--公告
	private View createArticleNotice(View convertView,HomeBean homeBean){
		NewsHolder holder=null;
		List<TNews> newsList=(List<TNews>)homeBean.getObject();
		if (convertView == null) {
			holder = new NewsHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_home_listview_item_article_notice, null);
			holder.noticeView=(TextView)convertView.findViewById(R.id.home_listview_article_notice_view);
			convertView.setTag(holder);
		}else{
			holder = (NewsHolder) convertView.getTag();
		}
		holder.noticeView.setText(newsList.get(0).getTitle());
		return convertView;
	}
	//功能按钮
	private View createFuns(View convertView,HomeBean homeBean){
		FunHolder holder=null;
		List<HomeFunsBean> beanList=(List<HomeFunsBean>)homeBean.getObject();
		if (convertView == null) {
			holder = new FunHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_home_listview_item_gridview, null);
			holder.gridView=(GridView)convertView.findViewById(R.id.home_listview_grid_view);
			convertView.setTag(holder);
		}else{
			holder = (FunHolder) convertView.getTag();
		}
		holder.gridView.setNumColumns(3);
		holder.gridView.setBackgroundResource(R.color.white);
		HomeFunGridViewtAdapter adapter=new HomeFunGridViewtAdapter(context,beanList);
		holder.gridView.setAdapter(adapter);
		holder.gridView.setOnItemClickListener(new FunOnClickListener(adapter));
//		adapter.notifyDataSetChanged();
		return convertView;
	}
	//分类商品--3个商品
	private View createGoods3(View convertView,HomeBean homeBean){
		Goods3Holder holder=null;
		List<HomeGoods> beanList=(List<HomeGoods>)homeBean.getObject();
		if (convertView == null) {
			holder = new Goods3Holder();
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_home_listview_item_goods_3, null);
			holder.nameView=(TextView)convertView.findViewById(R.id.home_category_name);
			holder.moreView=(TextView)convertView.findViewById(R.id.home_category_more);
			holder.imageView1=(ImageView)convertView.findViewById(R.id.home_item_image1);
			holder.imageView2=(ImageView)convertView.findViewById(R.id.home_item_image2);
			holder.imageView3=(ImageView)convertView.findViewById(R.id.home_item_image3);
			convertView.setTag(holder);
		}else{
			holder = (Goods3Holder) convertView.getTag();
		}
		for(int i=0;i<beanList.size();i++){
			HomeGoods homeGoods=beanList.get(i);
			if(i==0){
				DVolley.getImage(homeGoods.getGoodsImage(), holder.imageView1);
				holder.imageView1.setOnClickListener(new GoodsOnClickListener(homeGoods.getGoodsID()));
				holder.moreView.setOnClickListener(new GoodsListOnClickListener(homeGoods.getCatID()));
				holder.nameView.setText(homeGoods.getCatName());
			}else if(i==1){
				DVolley.getImage(homeGoods.getGoodsImage(), holder.imageView2);
				holder.imageView2.setOnClickListener(new GoodsOnClickListener(homeGoods.getGoodsID()));
			}else if(i==2){
				DVolley.getImage(homeGoods.getGoodsImage(), holder.imageView3);
				holder.imageView3.setOnClickListener(new GoodsOnClickListener(homeGoods.getGoodsID()));
			}
		}
		return convertView;
	}
	
	HomePromotionGridViewtAdapter homePromotionGridViewtAdapter=new HomePromotionGridViewtAdapter(context,new ArrayList<TGroup>());
	//团购--限时促销
	private View createGroupTimePromotion(View convertView,HomeBean homeBean){
		FunHolder holder=null;
		List<TGroup> beanList=(List<TGroup>)homeBean.getObject();
		if (convertView == null) {
			holder = new FunHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_home_listview_item_gridview, null);
			holder.gridView=(GridView)convertView.findViewById(R.id.home_listview_grid_view);
			convertView.setTag(holder);
		}else{
			holder = (FunHolder) convertView.getTag();
		}
		holder.gridView.setNumColumns(2);
		holder.gridView.setHorizontalSpacing(10);
		holder.gridView.setVerticalSpacing(10);
		holder.gridView.setBackgroundResource(R.color.white);
		homePromotionGridViewtAdapter.setList(beanList);
		holder.gridView.setAdapter(homePromotionGridViewtAdapter);
		holder.gridView.setOnItemClickListener(new GroupOnClickListener(homePromotionGridViewtAdapter));
//		adapter.notifyDataSetChanged();
		return convertView;
	}
	
	//团购--限时促销-标题
	private View createGroupTimePromotionTitle(View convertView,HomeBean homeBean){
		FunHolder holder=null;
		if (convertView == null) {
			holder = new FunHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_home_listview_item_promotion_title, null);
			convertView.setTag(holder);
		}else{
			holder = (FunHolder) convertView.getTag();
		}
		return convertView;
	}
	
	//商品
	class GoodsOnClickListener implements OnClickListener{
		private String goodsID;
		public GoodsOnClickListener(String goodsID){
			this.goodsID=goodsID;
		}
		@Override
		public void onClick(View v) {
			Intent intent=new Intent(context,GoodsDetailsActivity.class);
			intent.putExtra("goodsID", goodsID);
			context.startActivity(intent);
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
			Intent intent=new Intent(context,GoodsListActivity.class);
			intent.putExtra("categoryID", categoryID);
			context.startActivity(intent);
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
			if(context instanceof BaseActivity){
				((BaseActivity)context).startActivity(homeFunsBean.isfixed,homeFunsBean.href);
			}
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
			Intent intent=new Intent(context,GroupDetailsActivity.class);
			intent.putExtra("groupID", group.getGroupID());
			context.startActivity(intent);
		}
	}
	class AdHolder {
		ImageView adImageView;
		BannerImageView bannerView;
	}
	class NewsHolder {
		TextView noticeView;
	}
	class FunHolder {
		GridView gridView;
	}
	class Goods3Holder {
		TextView nameView;
		TextView moreView;

		ImageView imageView1;
		ImageView imageView2;
		ImageView imageView3;

	}
}
