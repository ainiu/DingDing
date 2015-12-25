package com.ghsh.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ghsh.code.bean.TMessage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MessageUtils {
	private static final String config="message_data_2";
	
	public static ArrayList<TMessage> findWeiDuMsgList(Context context,List<TMessage> messageList) {
		SharedPreferences pref = context.getSharedPreferences(config,Context.MODE_APPEND);
		Editor editor = pref.edit();
		
		Map<String,?> allMap=pref.getAll();
		ArrayList<TMessage> msgList=new ArrayList<TMessage>();
		for(TMessage msg:messageList){
			String msgID=msg.getMeaasgeID();
			if(!allMap.containsKey(msgID)){
				editor.putString(msgID, msgID);
				msgList.add(msg);
			}
		}
		editor.commit();
		return msgList;
	}
}
