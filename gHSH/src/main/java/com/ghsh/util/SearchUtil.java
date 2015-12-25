package com.ghsh.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SearchUtil {
	private static final String config="search_config";
	/**写入关键字*/
	public static void write(Context context,String key){
		if(key==null||key.equals("")){
        	return;
        }
		SharedPreferences pref = context.getSharedPreferences(config, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString(key,"");
        editor.commit();
	}
	/**读取所有*/
	public static List<String> readeAll(Context context){
		List<String> list=new ArrayList<String>();
		SharedPreferences pref = context.getSharedPreferences(config, Context.MODE_APPEND);
		Map<String, ?> map=pref.getAll();
		if(map!=null&&map.size()!=0){
			list.addAll(map.keySet());
		}
		return list;
	}
	/**清空所有所有*/
	public static void clearAll(Context context){
		SharedPreferences pref = context.getSharedPreferences(config, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}
}
