package com.ghsh.broadcast;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class BroadcastUtils {

	public static IntentFilter getMessageFilter(){
		IntentFilter filter = new IntentFilter();
		filter.addAction("MESSAGE_NEW");
		return filter;
	}
	//发送新消息广告
	public static void sendNewMessageSize(Context context,int newMsgSize){
		Intent intent = new Intent("MESSAGE_NEW");
		intent.putExtra("MESSAGE_NEW_SIZE", newMsgSize);
		context.sendBroadcast(intent);
	}
}
