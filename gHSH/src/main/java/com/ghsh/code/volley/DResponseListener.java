package com.ghsh.code.volley;

import com.ghsh.code.volley.bean.ReturnBean;

/**界面回调监听*/
public interface DResponseListener {
	public void onMessageResponse(ReturnBean bean,int result,String message,Throwable error);
}
