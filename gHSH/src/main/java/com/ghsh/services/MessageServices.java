package com.ghsh.services;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ghsh.broadcast.BroadcastUtils;
import com.ghsh.code.bean.TMessage;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.MessageModel;
import com.ghsh.util.MenberUtils;
import com.ghsh.util.MessageUtils;
import com.ghsh.view.util.NotificationUtil;

public class MessageServices extends Service implements Runnable,DResponseListener {
	
	private MessageModel messageModel;
	private boolean isRun=true;
	private Thread thread;
	private NotificationUtil notificationUtil;
	private String userID;
	@Override
	public void onCreate() {
		super.onCreate();
		notificationUtil=new NotificationUtil(this);
		messageModel=new MessageModel(this);
		messageModel.addResponseListener(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onDestroy() {
		isRun=false;
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		userID=MenberUtils.getUserID(this);
		if(userID==null||userID.equals("")){
			return super.onStartCommand(intent, flags, startId);
		}
		if(thread!=null&&thread.isAlive()){
			isRun=false;
			thread.interrupt();
		}
		isRun=true;
		thread=new Thread(this);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void run() {
		while(isRun){
			messageModel.findNewMessageList(userID);
			try {
				Thread.sleep(10*60*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		if(error!=null){
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_QUERYALL){
				List<TMessage> messageList=(List<TMessage>)bean.getObject();
				if(messageList==null||messageList.size()==0){
					return;
				}
				ArrayList<TMessage> weiduList=MessageUtils.findWeiDuMsgList(this,messageList);
				if(weiduList!=null&&weiduList.size()!=0){
					sendBroadCast(weiduList.size(),weiduList.get(0),messageList.size());
				}
			}
		}
	}
	
	private void sendBroadCast(int newMsgSize,TMessage message,int newAllMsgSize){
		BroadcastUtils.sendNewMessageSize(this, newMsgSize);
		notificationUtil.notifiMsg(message,newAllMsgSize);
	}
}
