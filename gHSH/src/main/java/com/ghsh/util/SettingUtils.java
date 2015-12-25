package com.ghsh.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SettingUtils {
	private static final String config="setting_config";
	/**写入加载图片模式*/
	public static void writeNvoice(Context context,int nvoice){
		SharedPreferences pref = context.getSharedPreferences(config, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putInt("nvoice", nvoice);
        editor.commit();
	}
	/**读取加载图片模式*/
	public static int readeNvoice(Context context){
		SharedPreferences pref = context.getSharedPreferences(config, Context.MODE_APPEND);
		return pref.getInt("nvoice", 1);
	}
	/**写入推送模式*/
	public static void writePush(Context context,boolean isPush){
		SharedPreferences pref = context.getSharedPreferences(config, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putBoolean("isPush", isPush);
        editor.commit();
	}
	/**读取推送模式*/
	public static boolean readePush(Context context){
		SharedPreferences pref = context.getSharedPreferences(config, Context.MODE_APPEND);
		return pref.getBoolean("isPush", false);
	}
	/**写入引导页状态*/
	public static void writeConduct(Context context,boolean conduct){
		SharedPreferences pref = context.getSharedPreferences(config, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putBoolean("conduct", conduct);
        editor.commit();
	}
	/**读取引导页状态*/
	public static boolean readeConduct(Context context){
		SharedPreferences pref = context.getSharedPreferences(config, Context.MODE_APPEND);
		return pref.getBoolean("conduct", false);
	}
}
