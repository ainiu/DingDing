package com.fanc.mycar.code;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

/**
 * 网络数据，服务端回调监听
 * */
public abstract class DResponseService implements Response.Listener<String>,ErrorListener {
    private final String TAG = "DResponseService";
    private DCallResult callResult;
    protected DVolleyModel volleyModel;

    public DResponseService(DVolleyModel volleyModel) {
        this.volleyModel=volleyModel;
        callResult = new DCallResult();
    }

    /**网络数据回调*/
    @Override
    public final void onResponse(String response) {
        try {
            callResult.setCache(false);
            callResult.setResponse(response);
            this.myOnResponse(callResult);
        } catch (Exception e) {
            volleyModel.onMessageResponse(null, DVolleyConstans.RESULT_FAIL, null, e);
        }
        if(Constants.DEBUG){
            Log.i(TAG+"网络数据", response);
        }
    }
    /**缓存数据回调*/
    public final void onCacheResponse(String response) {
        try {
            callResult.setCache(true);
            callResult.setResponse(response);
            this.myOnResponse(callResult);
        } catch (Exception e) {
            volleyModel.onMessageResponse(null, DVolleyConstans.RESULT_FAIL, null, e);
        }
        if(Constants.DEBUG){
            Log.i(TAG+"本地缓存数据", response);
        }
    }
    /**错误回调*/
    @Override
    public final void onErrorResponse(VolleyError error) {
        if(error instanceof com.android.volley.ServerError){
            volleyModel.onMessageResponse(null,DVolleyConstans.RESULT_FAIL,null,new Exception("服务器正在维护，请稍后再试！"));
        }else if(error instanceof com.android.volley.NoConnectionError){
            volleyModel.onMessageResponse(null,DVolleyConstans.RESULT_NOT_NEWWORK,null,new Exception("请检查网络是否已经连接！"));
        }else if(error instanceof com.android.volley.TimeoutError){
            volleyModel.onMessageResponse(null,DVolleyConstans.RESULT_NOT_NEWWORK,null,new Exception("登录超时，请检查网络是否已经连接！"));
        }else{
            volleyModel.onMessageResponse(null,DVolleyConstans.RESULT_FAIL,null,error);
        }
        if(Constants.DEBUG){
            Log.i(TAG, error+"");
        }
    }
    protected abstract void myOnResponse(DCallResult callResult) throws Exception;
}
