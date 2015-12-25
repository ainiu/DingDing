package com.ghsh.activity.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.ghsh.ActivityManagerModel;
import com.ghsh.DApplication;
import com.ghsh.R;
import com.ghsh.activity.GoodsDetailsActivity;
import com.ghsh.activity.GoodsListActivity;
import com.ghsh.activity.LoginActivity;
import com.ghsh.activity.MainTabActivity;
import com.ghsh.code.volley.DVolley;
import com.ghsh.util.MenberUtils;
import com.ghsh.view.DToastView;
import com.ghsh.view.util.ViewUtils;

public class BaseActivity extends Activity {
	private boolean isBackExit=false;//是否按返回键退出--默认不是
	private int isBackButtonVisible=View.VISIBLE;//是否有返回按钮--默认有
	private static Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManagerModel.addLiveActivity(this);
		context = this;
	}
	@Override
	protected void onStart() {
		super.onStart();
		//设置头部背景颜色
		this.setHeaderStyle(this);
		ActivityManagerModel.addVisibleActivity(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		ActivityManagerModel.removeVisibleActivity(this);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		ActivityManagerModel.addForegroundActivity(this);
		ViewUtils.updateConfiguration(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		ActivityManagerModel.removeForegroundActivity(this);
		DVolley.cancelAll();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityManagerModel.removeLiveActivity(this);
	}
	
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	protected View getView(int layoutID) {
		return getLayoutInflater().inflate(layoutID, null);
	}

	protected void showShortToast(String text) {
		DToastView.makeText((Activity)context, text,Toast.LENGTH_SHORT).show();
	}

	protected void showShortToast(int resId) {
		DToastView.makeText(this, resId,Toast.LENGTH_SHORT).show();
	}

	
	protected void loginOut(){
		MenberUtils.loginOut(this);
	}
	protected String getMenberName(){
		return MenberUtils.getName(this);
	}
	public static String getUserID(){
		return MenberUtils.getUserID(context);
	}
	protected String getShareOpenID(){
		return MenberUtils.getShareOpenID(this);
	}
	protected String getShareType(){
		return MenberUtils.getShareType(this);
	}
	protected void startLogin(){
		Intent intent=new Intent(this,LoginActivity.class);
		this.startActivity(intent);
	}
	public static boolean checkLogin(){
		if(!MenberUtils.isLogin(context)){
			Intent intent=new Intent(context,LoginActivity.class);
			context.startActivity(intent);
			((Activity) context).finish();
			return false;
		}
		return true;
	}

	public void setBackExit(boolean isBackExit) {
		this.isBackExit = isBackExit;
	}

	public int isBackButtonVisible() {
		return isBackButtonVisible;
	}
	public void setBackButtonVisible(int isBackButtonVisible) {
		this.isBackButtonVisible = isBackButtonVisible;
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(isBackExit){
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				return ActivityManagerModel.exitApp(this);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	/**获取固定连接 class*/
	public void startActivity(String fixed,String href){
		if((fixed==null||fixed.equals(""))&&(href==null||href.equals(""))){
			this.showShortToast(R.string.not_message);
			return;
		}
		Class<?> clazz=((DApplication)this.getApplication()).getClassByFixed(fixed);
		if(clazz==null){
			this.showShortToast(R.string.not_message);
			return;
		}
		//如果是是tab页面，则跳转到tab页面
		Activity activity=getParent();
		if(activity instanceof MainTabActivity){
			if(fixed.equals("homePage")){
				MainTabActivity base=(MainTabActivity)activity;
				base.onChangedTabIndex(0);
				return;
			}else if(fixed.equals("goodsCategoryListPage")){
				MainTabActivity base=(MainTabActivity)activity;
				base.onChangedTabIndex(1);
				return;
			}else if(fixed.equals("personalPage")){
				MainTabActivity base=(MainTabActivity)activity;
				base.onChangedTabIndex(2);
				return;
			}else if(fixed.equals("shoopingCartListPage")){
				MainTabActivity base=(MainTabActivity)activity;
				base.onChangedTabIndex(3);
				return;
			}else if(fixed.equals("newsListPage")){
				MainTabActivity base=(MainTabActivity)activity;
				base.onChangedTabIndex(4);
				return;
			}
		}
		//不是tab页面
		Intent intent=new Intent(this,clazz);
		if(clazz.equals(GoodsDetailsActivity.class)){
			intent.putExtra("goodsID", href);
		}else if(clazz.equals(GoodsListActivity.class)){
			intent.putExtra("categoryID", href);
		}
		this.startActivity(intent);
	}
	/**设置头部 样式*/
	private  void setHeaderStyle(final Activity activity){
		View view=activity.findViewById(R.id.header_layout);
		if(view==null){
			return;
		}
		//设置返回按钮
		ImageView backView= (ImageView) activity.findViewById(R.id.header_back_view);
		if(backView!=null&&backView.getTag()==null){
			if(activity instanceof BaseActivity){
				//设置返回按钮显示还是隐藏
				backView.setVisibility(((BaseActivity)activity).isBackButtonVisible());
			}
			if(backView.getVisibility()==View.VISIBLE){
				backView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						activity.finish();
					}
				});
			}
		}
	}
}
