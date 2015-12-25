package com.baidu.dingding.code;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.baidu.dingding.until.LogUtil;

public abstract class RequestListener implements Listener, ErrorListener {

	@Override
	public void onErrorResponse(VolleyError error) {
		LogUtil.i("错误", error.getMessage());
	}
	
}
