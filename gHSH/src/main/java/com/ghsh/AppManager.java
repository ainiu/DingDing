package com.ghsh;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

import com.ghsh.code.exception.AppViewException;
/**
 * 获取版本号
 * **/
public class AppManager {

	public static String getPackageName(Context context) {
		return context.getPackageName();
	}
	
	public static int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			String packageName=context.getPackageName();
			versionCode = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
		} catch (NameNotFoundException e) {
			AppViewException.onViewException(e);
		}
		return versionCode;
	}

	public static String getVersionName(Context context) {
		String versionName = "";
		try {
			String packageName=context.getPackageName();
			versionName = context.getPackageManager().getPackageInfo(packageName, 0).versionName;
		} catch (NameNotFoundException e) {
			AppViewException.onViewException(e);
		}
		return versionName;
	}
}
