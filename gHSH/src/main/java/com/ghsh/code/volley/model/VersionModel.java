package com.ghsh.code.volley.model;

import java.util.Map;

import android.content.Context;

import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
//版本号
public class VersionModel extends DVolleyModel {
	private final String get_apk_version=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=version&m=getAndroidApkVersion");
	private DResponseService getApkVersionResponse;
	public VersionModel(Context context) {
		super(context);
	}
	/**获取apk版本号，用于自动更新*/
	public void getApkVersion(){
		Map<String, String> params = this.newParams();  
		if(getApkVersionResponse==null){
			getApkVersionResponse=new GetApkVersionResponse(this);
		}
		DVolley.get(get_apk_version, params,getApkVersionResponse);
	}
	
	private class GetApkVersionResponse extends DResponseService{
		public GetApkVersionResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_GET_APK_VERSION);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
		
	}
}
