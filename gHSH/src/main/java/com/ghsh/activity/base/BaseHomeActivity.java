package com.ghsh.activity.base;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;

import com.ghsh.ActivityManagerModel;
import com.ghsh.broadcast.BroadcastUtils;
import com.ghsh.code.exception.AppViewException;
import com.ghsh.code.home.HomeModel;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.dialog.DProgressDialog;


public abstract class BaseHomeActivity extends BaseActivity implements DResponseListener{
	
	private HomeModel homeModel;
	private DProgressDialog progressDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		homeModel=new HomeModel(this);
		homeModel.addResponseListener(this);
		progressDialog = new DProgressDialog(this);
		progressDialog.show();
		homeModel.findHomePage(true);
	}
	//注册未读消息通知
	protected void registerMessage(){
		this.registerReceiver(new MessageNewBroadCast(), BroadcastUtils.getMessageFilter());
	}
	private class MessageNewBroadCast extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent==null){
				return;
			}
			int size=intent.getIntExtra("MESSAGE_NEW_SIZE", 0);
			BaseHomeActivity.this.onNewMessage(size);
		}
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return ActivityManagerModel.exitApp(this);
		}
		return true;
	}
	
	/**查询数据*/
	protected void startLoadHomeData(){
		homeModel.findHomePage(false);
	}
	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		progressDialog.dismiss();
		JSONArray jsonArray=null;
		if(error!=null){
			this.showShortToast(error.getMessage());
		}
		if(result==DVolleyConstans.RESULT_OK){
			jsonArray=(JSONArray)bean.getObject();
		}
		try {
			this.loadHomeData(jsonArray);
		} catch (Exception e) {
			this.showShortToast(e.getMessage());
			AppViewException.onViewException(e);
		}
	}
	
	protected abstract void loadHomeData(JSONArray jsonArray)throws Exception;
	
	protected void onNewMessage(int size){}
}
 