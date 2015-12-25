package com.ghsh;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;

import com.ghsh.activity.GoodsCategoryActivity;
import com.ghsh.activity.GoodsDetailsActivity;
import com.ghsh.activity.GoodsListActivity;
import com.ghsh.activity.GroupbuyListActivity;
import com.ghsh.activity.HomeActivity;
import com.ghsh.activity.MapGaoDeActivity;
import com.ghsh.activity.MessageActivity;
import com.ghsh.activity.MyCollectActivity;
import com.ghsh.activity.MySendInfoActivity;
import com.ghsh.activity.NewHomeActivity;
import com.ghsh.activity.OrderListActivity;
import com.ghsh.activity.PersonalActivity;
import com.ghsh.activity.SearchActivity;
import com.ghsh.activity.SettingActivity;
import com.ghsh.activity.ShoppingCartActivity;
import com.ghsh.activity.SinglePageWebActivity;
import com.ghsh.activity.WebActivity;
import com.ghsh.code.exception.CrashHandler;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.util.ImageAsyncUtil;

public class DApplication extends Application{
	
	private static Map<String,Class<?>> fixed_href_MAP=new HashMap<String,Class<?>>();//固定连接
	static{
		fixed_href_MAP.put("homePage", HomeActivity.class);//分类：goodsCategoryListPage
		fixed_href_MAP.put("goodsCategoryListPage", GoodsCategoryActivity.class);//分类：goodsCategoryListPage
		fixed_href_MAP.put("searchPage", SearchActivity.class);//搜索：searchPage
		fixed_href_MAP.put("shoopingCartListPage", ShoppingCartActivity.class);//购物车：shoopingCartListPage
		fixed_href_MAP.put("personalPage", PersonalActivity.class);//个人中心：personalPage
		fixed_href_MAP.put("messageListPage", MessageActivity.class);//消息中心：messageListPage
		fixed_href_MAP.put("sendInfoListPage", MySendInfoActivity.class);//收货地址：sendInfoListPage
		fixed_href_MAP.put("collectListPage", MyCollectActivity.class);//我的收藏：collectListPage
		fixed_href_MAP.put("goodsListPage", GoodsListActivity.class);//商品列表：goodsListPage
		fixed_href_MAP.put("orderListPage", OrderListActivity.class);//全部订单列表：orderListPage
		fixed_href_MAP.put("settingPage", SettingActivity.class);//设置：settingPage
		fixed_href_MAP.put("goodsDetailsPage", GoodsDetailsActivity.class);//商品明细：goodsDetailsPage
		fixed_href_MAP.put("singlePage", SinglePageWebActivity.class);//单页
		fixed_href_MAP.put("groupbuyPage", GroupbuyListActivity.class);//团购
		fixed_href_MAP.put("storeMapPage", MapGaoDeActivity.class);//店铺地图
		fixed_href_MAP.put("newsListPage", NewHomeActivity.class);//新闻
		fixed_href_MAP.put("", WebActivity.class);//WebActivity
	}
	@Override
	public void onCreate() {
		super.onCreate();
		DVolley.init(this);
		ImageAsyncUtil.initSkinDir(this);
		DVolleyConstans.initConfige(this);//初始化店铺信息
		ShareConstants.initConfige(this);//初始化分享
		CrashHandler crashHandler = CrashHandler.getInstance();
		// 注册crashHandler
		crashHandler.init(getApplicationContext());
	}
	
	/**获取固定连接 class*/
	public Class<?> getClassByFixed(String fixed){
		return fixed_href_MAP.get(fixed);
	}
}
