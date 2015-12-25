package com.baidu.dingding.until;

import android.content.Context;

import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.ReturnBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class DVolleyModel {

	protected ArrayList<DResponseListener> businessResponseList = new ArrayList<DResponseListener>();

	protected Context mContext;
	
	public DVolleyModel(Context context) {
		this.mContext = context;
	}
	
	/**回调页面监听*/
	public void onMessageResponse(ReturnBean bean,int result,String message,Throwable error) {
		for (DResponseListener businessResponse : businessResponseList) {
			businessResponse.onMessageResponse(bean,result,message,error);
		}
	}
	/**添加页面监听*/
	public void addResponseListener(DResponseListener listener) {
		if (!businessResponseList.contains(listener)) {
			businessResponseList.add(listener);
		}
	}
	/**请求基本参数*/
	protected Map<String,String> newParams(){
		Map<String, String> params = new HashMap<String, String>();
		return params;
	}
}
