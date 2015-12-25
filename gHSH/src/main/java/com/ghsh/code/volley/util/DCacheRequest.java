package com.ghsh.code.volley.util;

import java.util.Map;

import android.os.Handler;
import android.os.Message;

import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.util.FileUtils;
/**
 * 先查询本地缓存，在查询网络
 * */
public class DCacheRequest extends Thread{

	private boolean isReadCache;
	
	private String cachePath;
	
	private DResponseService response;
	
	private String url;
	
	private  Map<String, String> params;
	
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			//读取网络数据
			if(msg.obj!=null){
				response.onCacheResponse(msg.obj+"");
			}
			DVolley.get(url, params, response);//读取网络数据
		}
	}; 
	
	public DCacheRequest(String url, Map<String, String> params,boolean isReadCache,String cachePath,DResponseService response){
		this.isReadCache=isReadCache;
		this.cachePath=cachePath;
		this.response=response;
		this.url=url;
		this.params=params;
	}
	@Override
	public void run() {
		Message message=new Message();
		if(isReadCache){
			//读取本地数据
			message.obj=FileUtils.readStr(cachePath);
		}
		handler.sendMessage(message);
	}
}
