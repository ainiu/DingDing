package com.fanc.mycar.code;


/**界面回调监听*/
public interface DResponseListener {
        public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error);
}
