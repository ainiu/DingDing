package com.ghsh.view.util;

import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ghsh.activity.MessageActivity;
import com.ghsh.code.bean.TMessage;
import com.ghsh.R;

public class NotificationUtil {
	private NotificationManager notificationManager;// 通知信息
	
	private Context context;
	
	private static int NOTIFI_MESSAGE_ID=0;
	
	public NotificationUtil(Context context){
		this.context=context;
		notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
	}
	
	public void notifiMsg(TMessage message,int newAllMsgSize){
		Notification notification = this.getNotification(message.getTitle(),newAllMsgSize);
		Intent intent = new Intent(context, MessageActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//加入这句，没一个通知都是一个新的task，不会覆盖当前的task
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,intent, 0);
		notification.setLatestEventInfo(context,message.getTitle(),message.getDesc(),contentIntent);
		notificationManager.notify(NOTIFI_MESSAGE_ID,notification);
//		NOTIFI_MESSAGE_ID++;
	}
	
	private Notification getNotification(String title,int number) {
		// 通知
		Notification notification = new Notification();
		notification.icon = R.drawable.icon;// 设置图标
		notification.tickerText = title;
		if (notification.number==0) {
			notification.number = number;//number字段表示此通知代表的当前事件数量，它将覆盖在状态栏图标的顶部
		}else{
			notification.number = notification.number+number;
			
		}
		notification.when = System.currentTimeMillis();//通知产生的时间，会在通知信息里显示
		/*
		 * DEFAULT_ALL 使用所有默认值，比如声音，震动，闪屏等等 
		 * DEFAULT_LIGHTS 使用默认闪光提示
		 * DEFAULT_SOUNDS 使用默认提示声音 
		 * DEFAULT_VIBRATE 使用默认手机震动，需加上<uses-permission android:name="android.permission.VIBRATE" />权限
		 */
		notification.defaults = Notification.DEFAULT_SOUND;
//		notification.flags = Notification.FLAG_ONGOING_EVENT;//加入这句，无法滑动取消（进入任务栏，不在通知栏）
		
		/*
		 * FLAG_AUTO_CANCEL 该通知能被状态栏的清除按钮给清除掉
		 * FLAG_NO_CLEAR 该通知不能被状态栏的清除按钮给清除掉
		 * FLAG_ONGOING_EVENT 通知放到通知栏的"Ongoing"即"正在运行"组中  
		 * FLAG_INSISTENT 是否一直进行，比如音乐一直播放，知道用户响应
		 */
		
//		或者可以自己的LED提醒模式:
//		notification.ledARGB = 0xff00ff00;//Color.BLUE;
//		notification.ledOnMS = 300; //亮的时间毫秒
//		notification.ledOffMS = 1000; //灭的时间毫秒
//		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		return notification;
	}
}
