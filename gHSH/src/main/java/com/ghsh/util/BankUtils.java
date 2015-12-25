package com.ghsh.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class BankUtils {
	private static final String config="bank_config";
	
	public static void writeBank(Context context,String userID,String trueName,String mobile,String bankName,String bankNum) {
		SharedPreferences pref = context.getSharedPreferences(config,Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putString("trueName"+userID, trueName);
		editor.putString("mobile"+userID, mobile);
		editor.putString("bankName"+userID,bankName);
		editor.putString("bankNum"+userID, bankNum);
		editor.commit();
	}
	
	public static String getTrueName(Context context,String userID) {
		SharedPreferences pref = context.getSharedPreferences(config, Context.MODE_APPEND);
		return pref.getString("trueName"+userID, "");
	}
	public static String getMobile(Context context,String userID) {
		SharedPreferences pref = context.getSharedPreferences(config, Context.MODE_APPEND);
		return pref.getString("mobile"+userID, "");
	}
	public static String getBankName(Context context,String userID) {
		SharedPreferences pref = context.getSharedPreferences(config, Context.MODE_APPEND);
		return pref.getString("bankName"+userID, "");
	}
	public static String getBankNum(Context context,String userID) {
		SharedPreferences pref = context.getSharedPreferences(config, Context.MODE_APPEND);
		return pref.getString("bankNum"+userID, "");
	}
}
