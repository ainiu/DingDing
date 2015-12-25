package com.baidu.dingding.code;


/**界面回调监听*/
public interface DResponseListener {
         void onMessageResponse(ReturnBean bean,int result,String message,Throwable error);
}
