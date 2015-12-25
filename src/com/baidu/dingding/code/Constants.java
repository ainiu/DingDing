
package com.baidu.dingding.code;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Environment;

/**
 * 静态常数。
 */
public class Constants {


    public static final boolean DEBUG = true; //是否开启日记

    private static final String SD_CARD_DIR="sdcard_temp";//SD卡路径

    private static final String  CACHE_DIR="cache_temp";//内存路径

    private static final String IMAGE_DIR="dtouch_image";//图库路径



    /**获取SDCard文件缓存路径   当前目录用来保存其他图片信息*/
    public static String getSDCachePath(Context context) {
        String filePath = null;
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            //外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
            if(context.getExternalCacheDir()!=null){
                filePath=context.getExternalCacheDir().getPath()+ File.separator;
            }
        }
        if(filePath==null){
            filePath = context.getFilesDir().getPath() + File.separator + Constants.SD_CARD_DIR+ File.separator;
        }
        File file=new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
        return filePath;
    }
    /**获取软件安装路径文件缓存路径  当前目录用来存贮主题配置信息图片*/
    public static String getCachePath(Context context) {
        String filePath = context.getFilesDir().getPath() + File.separator + Constants.CACHE_DIR+ File.separator;
        File file=new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
        return filePath;
    }
    /**获取SD卡的下载的图片保存的位置   当前目录用来存贮用户保存的图片*/
    public static String getSDPicturesPath() {
        String filePath=null;
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            filePath=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+ File.separator+IMAGE_DIR+ File.separator;
            File file=new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            }
        }
        return filePath;
    }

    /**
     * 获取高德地图 缓存和读取目录
     */
    public static  String getGaoDeSdCacheDir(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            java.io.File fExternalStorageDirectory = Environment.getExternalStorageDirectory();
            java.io.File autonaviDir = new java.io.File(fExternalStorageDirectory, "amapsdk");
            boolean result = false;
            if (!autonaviDir.exists()) {
                result = autonaviDir.mkdir();
            }
            java.io.File minimapDir = new java.io.File(autonaviDir,"offlineMap");
            if (!minimapDir.exists()) {
                result = minimapDir.mkdir();
            }
            return minimapDir.toString() + "/";
        } else {
            return getSDCachePath(context);
        }
    }
    /**
     * 获取更新apk下载目录
     */
    public static  String getAPKDownloadDir(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            java.io.File sdFile = Environment.getExternalStorageDirectory();
            java.io.File apkFile = new java.io.File(sdFile, SD_CARD_DIR);
            if (!apkFile.exists()) {
                apkFile.mkdir();
            }
            return apkFile.toString() + "/";
        } else {
            return getSDCachePath(context);
        }
    }
    /**获取WEB VIEW 缓存目录*/
    public static String getWebViewCachePath(Context context){
        return context.getFilesDir().getAbsolutePath()+ "/webcache";
    }
}
