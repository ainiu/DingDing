package com.ghsh.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ghsh.activity.LoginActivity;
import com.ghsh.code.bean.TUsers;

public class MenberUtils {
	private static final String config="menber_config";
	private static TUsers users;
	/**登录*/
	public static void login(Context context,TUsers users) {
		MenberUtils.users=users;
		SharedPreferences pref = context.getSharedPreferences(config,Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putString("userID", users.getUserID());
		editor.putString("userName", users.getUserName());
		editor.putString("mobile", users.getMobile());
		editor.putString("email", users.getEmail());
		editor.putString("nickName", users.getNickName());
//		editor.putString("share_openID", menberBean.share_openID);
//		editor.putString("share_type", menberBean.share_type);
		editor.commit();
	}
	/**退出登录*/
	public static void loginOut(Context context) {
		MenberUtils.users=null;
		SharedPreferences pref = context.getSharedPreferences(config,Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}
	/**是否登录*/
	public static boolean isLogin(Context context) {
		if(MenberUtils.readeMenberBean(context)!=null){
			return true;
		}
		return false;
	}
	
	public static boolean checkLogin(Activity activity){
		if(!MenberUtils.isLogin(activity)){
			Intent intent=new Intent(activity,LoginActivity.class);
			activity.startActivity(intent);
			activity.finish();
			return false;
		}
		return true;
	}
	
	public static String getName(Context context) {
		if(MenberUtils.isLogin(context)){
			TUsers users=MenberUtils.readeMenberBean(context);
			if(users.getNickName()!=null&&!users.getNickName().equals("")){
				return users.getNickName();
			}else if(users.getUserName()!=null&&!users.getUserName().equals("")){
				return users.getUserName();
			}else if(users.getMobile()!=null&&!users.getMobile().equals("")){
				return users.getMobile();
			}
		}
		return "";
	}
	
	
	/**获取用户ID*/
	public static String getUserID(Context context) {
		if(MenberUtils.isLogin(context)){
			return MenberUtils.readeMenberBean(context).getUserID();
		}
		return "";
//		return "2270";
	}
	/**获取分享OpenID*/
	public static String getShareOpenID(Context context) {
//		if(MenberUtils.getMenberBean(context)!=null){
//			return MenberUtils.menberBean.share_openID;
//		}
		return "";
	}
	/**获取分享Type*/
	public static String getShareType(Context context) {
//		if(MenberUtils.getMenberBean(context)!=null){
//			return MenberUtils.menberBean.share_type;
//		}
		return "";
	}
	/**读取用户消息*/
	private static TUsers readeMenberBean(Context context){
		if(MenberUtils.users!=null){
			return MenberUtils.users;
		}
		SharedPreferences pref = context.getSharedPreferences(config, Context.MODE_APPEND);
		TUsers users=new TUsers();
		users.setUserID(pref.getString("userID", ""));
		users.setUserName(pref.getString("userName", ""));
		users.setMobile(pref.getString("mobile", ""));
		users.setEmail(pref.getString("email", ""));
		users.setNickName(pref.getString("nickName", ""));
		if(users.getUserID()==null||users.getUserID().equals("")){
			return null;
		}
		return users;
	}
}
