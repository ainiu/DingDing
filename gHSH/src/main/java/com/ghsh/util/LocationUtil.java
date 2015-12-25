package com.ghsh.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**定位数据*/
public class LocationUtil {
	
	private static final String config="location_config";
	
	public static void saveLocation(Context context,String cityName,String cityCode) {
		SharedPreferences pref = context.getSharedPreferences(config,Context.MODE_APPEND);
		Editor editor = pref.edit();
		if(cityName!=null){
			cityName=cityName.replace("省", "");
			cityName=cityName.replace("市", "");
		}
		editor.putString("cityName", cityName);
		editor.putString("cityCode", cityCode);
		editor.commit();
	}
	
	
	public static String getCityName(Context context){
		SharedPreferences pref = context.getSharedPreferences(config, Context.MODE_APPEND);
		return pref.getString("cityName", "全国");
	}
}
