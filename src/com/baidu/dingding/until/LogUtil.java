package com.baidu.dingding.until;

import com.baidu.dingding.TApplication;

import android.util.Log;

/*
 *   统一处理打印的日志
 * */
public class LogUtil {
	public static void i(String tag, Object msg) {
		if (TApplication.isRelease) {
			return;
		}
		Log.i(tag, String.valueOf(msg));
	}

}
