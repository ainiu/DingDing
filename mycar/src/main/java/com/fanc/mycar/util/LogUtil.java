package com.fanc.mycar.util;

import android.util.Log;

import com.fanc.mycar.TApplication;

/**统一处理*/
public class LogUtil {
	public static void i(String tag, Object msg) {
		if (TApplication.isRelease) {
			return;
		}
		Log.i(tag, String.valueOf(msg));
	}

}
