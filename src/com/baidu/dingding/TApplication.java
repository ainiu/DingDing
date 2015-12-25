package com.baidu.dingding;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.baidu.dingding.code.DVolley;

import java.util.ArrayList;

public class TApplication extends Application {

	public static boolean isRelease = false;                                                        //日志开关
	private ArrayList<Activity> mList = new ArrayList<Activity>();
	private ArrayList<Activity> detailList = new ArrayList<Activity>();
	public static String userNumber="";
	private static Context context;

	public synchronized static TApplication getInstance() {
		if (null == sInstance) {
			sInstance = new TApplication();
		}
		return sInstance;
	}

	//add Activity
	public void addActivity(Activity activity) {
		mList.add(activity);
	}


	//退出程序
	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	public void addDetailActivity(Activity activity) {
		detailList.add(activity);
	}

	/**
	 * 关闭多余的Detail页面，使其总数不超过2个
	 */
	public void removeExtraActivity() {
		if (detailList.size() > 2) {
			detailList.get(0).finish();
			detailList.remove(0);
		}
	}

	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}
	/**
	 * Log or request TAG
	 */
	public static final String TAG = "VolleyPatterns";

	/**
	 * Global request queue for Volley
	 */
	private RequestQueue mRequestQueue;

	/**
	 * A singleton instance of the application class for easy access in other
	 * places
	 */
	private static TApplication sInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		DVolley.init(this);
		// initialize the singleton
		sInstance = this;
		//CityJsonParse.initConfige(this);
		context=getApplicationContext();
	}

	public static  Context getContext(){
		return  context;
	}

	/**
	 * @return ApplicationController singleton instance
	 */

	/**
	 * @return The Volley Request queue, the queue will be created if it is null
	 */
	public RequestQueue getRequestQueue() {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		if (mRequestQueue == null) {
			// 1
			// 2
			synchronized (TApplication.class) {
				if (mRequestQueue == null) {
					mRequestQueue = Volley
							.newRequestQueue(getApplicationContext());
				}
			}
		}
		return mRequestQueue;
	}

	/**
	 * Adds the specified request to the global queue, if tag is specified then
	 * it is used else Default TAG is used.
	 *
	 * @param req
	 * @param tag
	 */
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		VolleyLog.d("Adding request to queue: %s", req.getUrl());

		getRequestQueue().add(req);
	}


	public <T> void addToRequestQueue(Request<T> req) {
		// set the default tag if tag is empty
		req.setTag(TAG);

		getRequestQueue().add(req);
	}

	/**
	 * Cancels all pending requests by the specified TAG, it is important to
	 * specify a TAG so that the pending/ongoing requests can be cancelled.
	 *
	 * @param tag
	 */
	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

}
