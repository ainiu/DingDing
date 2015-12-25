package com.ghsh.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;

import com.ghsh.Constants;
import com.ghsh.code.exception.AppViewException;

/** 
 * 应用数据清除管理器
 * 描    述:主要功能有清除内/外缓存，清除数据库，清除sharedPreference，清除files和清除自定义目录
 * @author jason
 *  */
public class CleanManager {
	/**获取本地缓存大小*/
	public static String getCacheSize(Context context){
		double fileSize=0l;
		try {
			//获取外部缓存
			File externalCacheDir=new File(Constants.getSDCachePath(context));
			if(externalCacheDir.exists()){
				File[] files=externalCacheDir.listFiles();
				FileInputStream fis=null;
				for(File file:files){
					fis = new FileInputStream(file);
					fileSize = fileSize+fis.available(); //这就是文件大小
					fis.close();
				}
			}
		} catch (IOException e) {
			AppViewException.onViewException(e);
		}
		if(fileSize!=0){
			fileSize=fileSize/1024;
			if(fileSize<1024){
				return String.format("%.2f",fileSize)+"KB";
			}
			fileSize=fileSize/1024;
			if(fileSize<1024){
				return String.format("%.2f",fileSize)+"MB";
			}
			fileSize=fileSize/1024;
			if(fileSize<1024){
				return String.format("%.2f",fileSize)+"GB";
			}
		}
		return "0KB";
	}
	
	/**
	 * 清除cache
	 * @param context
	 */
	public static void cleanCache(Context context) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			//清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) 
			deleteFilesByDirectory(new File(Constants.getSDCachePath(context)));
		}
	}
	
	
	
	/** 
	 * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
	 * @param context 
	 * */
	public static void cleanInternalCache(Context context) {
		deleteFilesByDirectory(context.getCacheDir());
	}

	/** 
	 * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
	 *  @param context 
	 * */
	public static void cleanDatabases(Context context) {
		deleteFilesByDirectory(new File("/data/data/"+ context.getPackageName() + "/databases"));
	}

	/**
	 * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
	 * @param context
	 */
	public static void cleanSharedPreference(Context context) {
		deleteFilesByDirectory(new File("/data/data/"
				+ context.getPackageName() + "/shared_prefs"));
	}

	/**
	 * 按名字清除本应用数据库  
	 * @param context
	 * @param dbName 
	 * */
	public static void cleanDatabaseByName(Context context, String dbName) {
		context.deleteDatabase(dbName);
	}

	/**
	 * 清除/data/data/com.xxx.xxx/files下的内容 
	 * @param context 
	 * */
	public static void cleanFiles(Context context) {
		deleteFilesByDirectory(context.getFilesDir());
	}

	

	/**
	 * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 
	 * @param filePath 
	 * */
	public static void cleanCustomCache(String filePath) {
		deleteFilesByDirectory(new File(filePath));
	}

	/**
	 * 清除本应用所有的数据 
	 * @param context 
	 * @param filepath
	 * */
	public static void cleanApplicationData(Context context, String... filepath) {
		cleanInternalCache(context);
		cleanCache(context);
		cleanDatabases(context);
		cleanSharedPreference(context);
		cleanFiles(context);
		for (String filePath : filepath) {
			cleanCustomCache(filePath);
		}
	}

	/** 
	 * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 
	 * @param directory 
	 *  */
	private static void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
		}
	}
}