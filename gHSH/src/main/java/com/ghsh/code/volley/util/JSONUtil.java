package com.ghsh.code.volley.util;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ghsh.code.exception.AppViewException;

public class JSONUtil {
	
	public static String getString(JSONObject object,String name) throws JSONException {
		if(!object.has(name)){
			return "";
		}
		if(object.isNull(name)){
			return "";
		}
		return object.getString(name);
	}
	public static int getInt(JSONObject object,String name) throws JSONException {
		if(!object.has(name)){
			return 0;
		}
		if(object.isNull(name)){
			return 0;
		}
		int value=0;
		try {
			value=Integer.parseInt(object.getString(name));
		} catch (Exception e) {
			AppViewException.onViewException(e);
		}
		return value;
	}
	public static long getLong(JSONObject object,String name) throws JSONException {
		if(!object.has(name)){
			return 0;
		}
		if(object.isNull(name)){
			return 0;
		}
		long value=0;
		try {
			value=Long.parseLong(object.getString(name));
		} catch (Exception e) {
			AppViewException.onViewException(e);
		}
		return value;
	}
	public static BigDecimal getBigDecimal(JSONObject object,String name) throws JSONException {
		if(!object.has(name)){
			return new BigDecimal("0.00");
		}
		if(object.isNull(name)){
			return new BigDecimal("0.00");
		}
		BigDecimal value=new BigDecimal("0.00");
		try {
			value=new BigDecimal(object.getString(name));
		} catch (Exception e) {
			AppViewException.onViewException(e);
		}
		return value;
	}
	
	public static boolean getBoolean(JSONObject object,String name) throws JSONException {
		if(!object.has(name)){
			return false;
		}
		if(object.isNull(name)){
			return false;
		}
		try {
			return object.getBoolean(name);
		} catch (Exception e) {
		} 
		String str=object.getString(name);
		return str.equals("1")?true:false;
	}
	
	public static JSONObject getJSONObject(JSONObject object,String name) throws JSONException {
		if(!object.has(name)){
			return null;
		}
		if(object.isNull(name)){
			return null;
		}
		return object.getJSONObject(name);
	}
	public static JSONArray getJSONArray(JSONObject object,String name) throws JSONException {
		if(!object.has(name)){
			return null;
		}
		if(object.isNull(name)){
			return null;
		}
		return object.getJSONArray(name);
	}
}
