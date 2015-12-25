package com.ghsh.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.ghsh.R;
import com.ghsh.activity.view.TabView;
import com.ghsh.services.ApkUpdateVersion;
import com.ghsh.util.LocationUtil;
import com.ghsh.view.util.ServicesUtil;

public class MainTabActivity extends ActivityGroup implements AMapLocationListener{
	private TabHost tabHost;
	private LocationManagerProxy mLocationManagerProxy;//定位
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_tab);// 该语句必须在以上两句之后
		this.initView();
//		ServicesUtil.startMessageServices(this);
		new ApkUpdateVersion(this,false).checkVersion();
	}
	private void initView(){
		tabHost=(TabHost)this.findViewById(R.id.tabhost);
		tabHost.setup(this.getLocalActivityManager());
		
		List<ButtonBean> list=new ArrayList<ButtonBean>();
		list.add(new ButtonBean(this,HomeActivity1.class,"首页",R.drawable.footer_home_icon,R.drawable.footer_home_icon));
		list.add(new ButtonBean(this,GoodsCategoryActivity.class,"分类",R.drawable.footer_category_icon,R.drawable.footer_category_icon));
		list.add(new ButtonBean(this,PersonalActivity.class,"个人中心",R.drawable.footer_persion_icon,R.drawable.footer_persion_icon));
		list.add(new ButtonBean(this,ShoppingCartActivity.class,"购物车",R.drawable.footer_cart_icon,R.drawable.footer_cart_icon));
		list.add(new ButtonBean(this,NewHomeActivity.class,"百科",R.drawable.footer_news_icon,R.drawable.footer_news_icon));
		
		for(int i=0;i<list.size();i++){
			ButtonBean bean=list.get(i);
			TabSpec spec =this.createTabSpec(tabHost, bean);
			tabHost.addTab(spec);
		}
		tabHost.setCurrentTab(0);
		
		//此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
        //在定位结束后，在合适的生命周期调用destroy()方法     
        //其中如果间隔时间为-1，则定位只定一次
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 60*1000, 15, this);
	    mLocationManagerProxy.setGpsEnable(false);
	}
	protected TabSpec createTabSpec(TabHost tabHost,ButtonBean bean){
		TabView tabView = new TabView(this);
		tabView.setImageView(bean.icon, bean.iconHid);
		tabView.setTextView(bean.text,bean.textColor, bean.textHidColor);
		tabView.setBgView(bean.bgColor, bean.hidBGColor);
		tabView.setBackgroundColor(Color.TRANSPARENT);
		return tabHost.newTabSpec(bean.id+"").setIndicator(tabView).setContent(bean.intent);
	}
	/**首页选择按钮，如果是tab，则回调*/
	public void onChangedTabIndex(int index){
		tabHost.setCurrentTab(index);
	}
	private void stopLocation(){
		if (mLocationManagerProxy != null) {
        	mLocationManagerProxy.removeUpdates(this);
        	mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy = null;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.stopLocation();
	}
	
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (amapLocation != null) {
            if (amapLocation.getAMapException().getErrorCode() == 0) {
            	LocationUtil.saveLocation(this, amapLocation.getCity(), amapLocation.getCityCode());
            	this.stopLocation();
            }
        }
	}
	
	
	@Override
	public void onLocationChanged(Location location) {
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
	@Override
	public void onProviderEnabled(String provider) {
	}
	@Override
	public void onProviderDisabled(String provider) {
	}
	
	/**底部按钮样式*/
	public static class ButtonBean implements Serializable{
		private static final long serialVersionUID = 1L;
		public String id;//编号
		public String text;//按钮文本
		public int textColor;//文本颜色
		public int textHidColor;//文本高亮颜色
		public Drawable icon;//图标
		public Drawable iconHid;//高亮图标
		public int bgColor;//背景颜色
		public int hidBGColor;//高亮背景颜色
		public Intent intent;
		
		public ButtonBean(Context context,Class<?> clazz,String text,int icon,int iconHid) {
			super();
			this.bgColor=context.getResources().getColor(R.color.footer_bg_color);
			this.hidBGColor=context.getResources().getColor(R.color.footer_bg_select_color);
			
			this.textColor=context.getResources().getColor(R.color.footer_tab_text_color);
			this.textHidColor=context.getResources().getColor(R.color.footer_tab_select_text_color);
			
			this.icon=context.getResources().getDrawable(icon);
			this.iconHid=context.getResources().getDrawable(iconHid);
			this.text = text;
			
			this.intent=new Intent(context,clazz);
		}
	}
}
