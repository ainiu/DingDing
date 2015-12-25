
package com.ghsh;

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
	
	public static int COMMENT_TYPE0=0;//所有评论
	public static int COMMENT_TYPE1=1;//差评 
	public static int COMMENT_TYPE2=2;//中评
	public static int COMMENT_TYPE3=3;//好评
	
	//第三方登录类型
	public static String SHARE_LOGIN_QQ="QQ";
	public static String SHARE_LOGIN_SINA_WEIBO="SinaWeiBo";
	public static String SHARE_LOGIN_WEIXIN="Weixin";
		
	//订单综合状态 1:待付款 2：待发货  3：待收货  4：已完成  5：取消 6：无效  7：退货  8:已评论
	public static String ORDER_TYLE_0="0";
	public static String ORDER_TYLE_1="1";
	public static String ORDER_TYLE_2="2";
	public static String ORDER_TYLE_3="3";
	public static String ORDER_TYLE_4="4";
	public static String ORDER_TYLE_5="5";
	public static String ORDER_TYLE_6="6";
	public static String ORDER_TYLE_7="7";
	public static String ORDER_TYLE_8="8";
		
	/**订单综合状态*/
	public static Map<String,String> ORDER_TYLE_MAP(){
		Map<String,String> map=new HashMap<String,String>();
		map.put(ORDER_TYLE_0, "未确认");
		map.put(ORDER_TYLE_1, "待付款");
		map.put(ORDER_TYLE_2, "待发货");
		map.put(ORDER_TYLE_3, "待收货");
		map.put(ORDER_TYLE_4, "已完成");
		map.put(ORDER_TYLE_5, "已取消");
		map.put(ORDER_TYLE_6, "无效订单");
		map.put(ORDER_TYLE_7, "已退货");
		map.put(ORDER_TYLE_8, "已评论");
		return map;
	}
	
	public static int GROUP_STATUS_1=1;
	/**团购状态*/
	public static Map<String,String> GROUP_STATUS_MAP(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("0", "未开始");
		map.put("1", "进行中");
		map.put("2", "已结束");
		map.put("3", "已完成");
		map.put("4", "已取消");
		return map;
	}
	
	public static String ORDER_PAYMENT_cod="cod";//货到付款
	public static String ORDER_PAYMENT_bank="bank";//等待付款
	public static String ORDER_PAYMENT_alipayfree="alipayfree";//支付宝免签接口
	public static String ORDER_PAYMENT_alipay_mobile="alipay";//支付宝快捷支付
	public static String ORDER_PAYMENT_weizhifuapp="wx_new_jspay";//微信APP支付
	public static String ORDER_PAYMENT_upmp_mobile="upop_mobile";//银联APP支付
	public static String ORDER_PAYMENT_balance="balance";//余额支付
	
	/**支付方式*/
	public static Map<String,String> ORDER_PAYMENT_MAP(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("cod", "货到付款");
		map.put("bank", "等待付款");
		map.put("alipayfree", "支付宝免签接口");
		map.put("alipay", "支付宝");
		map.put("upop_mobile", "银联支付");
		map.put("balance", "余额支付");
		return map;
	}
	
	//优惠劵状态 1:未领取 2:已领取 3:已领完 4:已过期 5:未使用 6：已使用
	public static String COUPON_STATUS_1="1";
	public static String COUPON_STATUS_2="2";
	public static String COUPON_STATUS_3="3";
	public static String COUPON_STATUS_4="4";
	public static String COUPON_STATUS_5="5";
	public static String COUPON_STATUS_6="6";
	/**优惠劵状态*/
	public static Map<String,String> COUPON_STATUS_1_MAP(){
		Map<String,String> map=new HashMap<String,String>();
		map.put(COUPON_STATUS_1, "未领取");
		map.put(COUPON_STATUS_2, "已领取");
		map.put(COUPON_STATUS_3, "已领完");
		map.put(COUPON_STATUS_4, "已过期");
		map.put(COUPON_STATUS_5, "未使用");
		map.put(COUPON_STATUS_6, "已使用");
		return map;
	}
	
	//红包状态  1:未使用 2:已使用 3:已过期
	public static String BONUS_STATUS_1="1";
	public static String BONUS_STATUS_2="2";
	public static String BONUS_STATUS_3="3";
	/**红包状态*/
	public static Map<String,String> BONUS_STATUS_MAP(){
		Map<String,String> map=new HashMap<String,String>();
		map.put(BONUS_STATUS_1, "未使用"); 
		map.put(BONUS_STATUS_2, "已使用");
		map.put(BONUS_STATUS_3, "已过期");
		return map;
	}
	
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
