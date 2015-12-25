package com.baidu.dingding;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.baidu.dingding.activity.BaseActivity;

import java.util.ArrayList;


public class ActivityManagerModel {
	public static ArrayList<BaseActivity> liveActivityList = new ArrayList<BaseActivity>();//所有
	public static ArrayList<BaseActivity> visibleActivityList = new ArrayList<BaseActivity>();//显示的
	public static ArrayList<BaseActivity> foregroundActivityList = new ArrayList<BaseActivity>();//前面显示
	
	public static void addLiveActivity(BaseActivity baseActivity) {
		if (!liveActivityList.contains(baseActivity)) {
			liveActivityList.add(baseActivity);
		}
	}


	public static void removeLiveActivity(BaseActivity baseActivity) {
		liveActivityList.remove(baseActivity);
		visibleActivityList.remove(baseActivity);
		foregroundActivityList.remove(baseActivity);
	}

	public static void addVisibleActivity(BaseActivity baseActivity) {
		if (!visibleActivityList.contains(baseActivity)) {
			visibleActivityList.add(baseActivity);
		}
	}

	public static void removeVisibleActivity(BaseActivity baseActivity) {
		visibleActivityList.remove(baseActivity);
	}

	public static void addForegroundActivity(BaseActivity baseActivity) {
		if (!foregroundActivityList.contains(baseActivity)) {
			foregroundActivityList.add(baseActivity);
		}
	}

	public static void removeForegroundActivity(BaseActivity baseActivity) {
		foregroundActivityList.remove(baseActivity);
	}

	public static void finishAll(){
		for(BaseActivity a:liveActivityList){
			a.finish();
		}
	}
	
	// 退出操作
	private static boolean isExit = false;
	public static boolean exitApp(Activity activity) {
		if (isExit == false) {
			isExit = true;
			Toast.makeText(activity, R.string.app_exit, Toast.LENGTH_SHORT).show();
			handler.sendEmptyMessageDelayed(0, 3000);
			return true;
		} else {
			// 关闭服务
			ActivityManagerModel.finishAll();
			activity.finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			return false;
		}
	}

	static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};
}
