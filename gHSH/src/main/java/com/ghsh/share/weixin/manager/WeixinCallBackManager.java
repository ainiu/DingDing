package com.ghsh.share.weixin.manager;

import java.util.ArrayList;
import java.util.List;

import com.tencent.mm.sdk.modelbase.BaseResp;

public class WeixinCallBackManager {
	
	private static List<Listener> callBackListeners=new ArrayList<Listener>();
	
	public static void addCallBackListener(Listener listener){
		if(!callBackListeners.contains(listener)){
			callBackListeners.add(listener);
		}
	}
	public static void onRespNotifyAll(BaseResp resp){
		for(Listener listener:callBackListeners){
			listener.onWeixincallBack(resp);
		}
		callBackListeners.clear();
	}
	public static interface Listener{
		public void onWeixincallBack(BaseResp resp);
	}
}
