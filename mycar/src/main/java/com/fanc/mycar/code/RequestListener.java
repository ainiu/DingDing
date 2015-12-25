package com.fanc.mycar.code;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.fanc.mycar.util.LogUtil;

public abstract class RequestListener implements Listener, ErrorListener {

	@Override
	public void onErrorResponse(VolleyError error) {
		LogUtil.i("错误", error.getMessage());
	}
	
}
