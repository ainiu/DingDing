package com.ghsh.services;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.ghsh.AppManager;
import com.ghsh.Constants;
import com.ghsh.code.exception.AppViewException;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.VersionModel;
import com.ghsh.view.DToastView;
import com.ghsh.view.util.DAlertUtil;
import com.ghsh.R;

public class ApkUpdateVersion implements DResponseListener{

	private Context context;
	private boolean isTip=false;//是否提示
	private VersionModel versionModel;
	
	public ApkUpdateVersion(Context context,boolean isTip){
		this.context=context;
		this.isTip=isTip;
		versionModel=new VersionModel(context);
		versionModel.addResponseListener(this);
	}
	public void checkVersion(){
		versionModel.getApkVersion();//检查apk版本
	}
	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		if(error!=null){
			//检查版本，错误不提示
			if(isTip){
				this.showTost(error.getMessage());
			}
			return;
		}
		if(result!=DVolleyConstans.RESULT_OK||bean.getType()!=DVolleyConstans.METHOD_GET_APK_VERSION){
			if(isTip){
				this.showTost(message);
			}
			return;
		}
		float newVersion=0;
		int oldVersion=0;
		try {
			newVersion=Float.parseFloat(bean.getObject()+"");
			oldVersion=AppManager.getVersionCode(context);
		} catch (Exception e) {
			AppViewException.onViewException(e);
		}
		if(Constants.DEBUG){
			Log.i("APP自动更新", "当前版本："+oldVersion+" 最新版本："+newVersion);
		}
		
		if(newVersion<=oldVersion){
			if(isTip){
				this.showTost(R.string.update_apk_tip_version);
			}
			return;
		}
		//"发现新版本，可以更新..."
		final float tempVersion=newVersion;
		DAlertUtil.alertOKAndCel(context,R.string.update_apk_alert_version ,R.string.update_apk_alert_version_ok_button, new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent=new Intent(context, UpdateService.class);
				intent.putExtra("version", tempVersion);
				context.startService(intent);
			}
		}).show();
//		//提示是否更新版本
//		AlertDialog.Builder builder = new AlertDialog.Builder(context);
//		builder.setTitle("发现新版本，可以更新...").setMessage("更新操作").setPositiveButton("确认更新", new OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				Intent intent=new Intent(context, UpdateService.class);
//				intent.putExtra("version", newVersion);
//				context.startService(intent);
//			}
//		}).setNegativeButton("取消", null).create().show();
	}
	
	private void showTost(String text){
		if(isTip){
			DToastView.makeText((Activity)context, text, Toast.LENGTH_SHORT).show();
		}
	}
	private void showTost(int text){
		if(isTip){
			DToastView.makeText((Activity)context, text, Toast.LENGTH_SHORT).show();
		}
	}
}
