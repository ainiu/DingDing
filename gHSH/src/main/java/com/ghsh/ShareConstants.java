package com.ghsh;

import android.content.Context;
import android.content.res.AssetManager;

import com.ghsh.code.bean.TShare;
import com.ghsh.code.exception.AppViewException;
import com.ghsh.code.volley.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ShareConstants {
	private static List<TShare.ShareBean> shareBeanList=new ArrayList<TShare.ShareBean>();
	
	public final static int REQUEST_CODE_QQ=10100;//QQ onActivityResult
	public final static int REQUEST_CODE_SINA_WEIBO=32973;//QQ onActivityResult
	
	/**初始化*/
	public static void initConfige(Context context){
		AssetManager assetManager =context.getResources().getAssets();
		try {
			InputStream is = assetManager.open("share.json");

			int len=0;
			byte[] bytes=new byte[1024];
			StringBuffer sb=new StringBuffer();
			while((len=is.read(bytes, 0, bytes.length))!=-1){
				sb.append(new String(bytes,0,len));
			}
			is.close();
			if(sb.length()==0){
				return;
			}
			JSONObject jsonObject=new JSONObject(sb.toString());
			if(jsonObject==null||jsonObject.length()==0){
				return;
			}
			JSONArray contentArray=jsonObject.getJSONArray("content");
			if(contentArray==null||contentArray.length()==0){
				return;
			}
			shareBeanList.clear();
			for(int i=0;i<contentArray.length();i++){
				JSONObject contentObject=contentArray.getJSONObject(i);
				String shareId=JSONUtil.getString(contentObject, "shareId");
				String shareCode=JSONUtil.getString(contentObject, "shareCode");
				String shareName=JSONUtil.getString(contentObject, "shareName");
				String shareDesc=JSONUtil.getString(contentObject, "shareDesc");
				String sortOrder=JSONUtil.getString(contentObject, "sortOrder");
				boolean isLogin=JSONUtil.getBoolean(contentObject, "isLogin");
				JSONObject config=JSONUtil.getJSONObject(contentObject, "config");
				
				TShare.ShareBean shareBean=new TShare.ShareBean();
				shareBean.shareId=shareId;
				shareBean.shareCode=shareCode;
				shareBean.shareName=shareName;
				shareBean.shareDesc=shareDesc;
				shareBean.sortOrder=sortOrder;
				shareBean.isLogin=isLogin;
				if(config!=null&&config.length()!=0){
					TShare.ShareConfig shareConfig=new TShare.ShareConfig();
					shareConfig.APP_ID=JSONUtil.getString(config, "APP_ID");
					shareConfig.APP_KEY=JSONUtil.getString(config, "APP_KEY");
					shareConfig.APP_Secret=JSONUtil.getString(config, "APP_Secret");
					shareBean.shareConfig=shareConfig;
				}
				shareBeanList.add(shareBean);
			}
		} catch (Exception e) {
			AppViewException.onViewException(e);
		}
	}
	/**获取分享列表*/
	public static List<TShare> getShareList(){
		List<TShare> shareList=new ArrayList<TShare>();
		TShare.ShareBean qqwechat=ShareConstants.getQQWechatShareBean();
		TShare.ShareBean qq=ShareConstants.getQQShareBean();
//		TShare.ShareBean qqweibo=ShareConstants.getQQWeiboShareBean();
		TShare.ShareBean sinaweibo=ShareConstants.getSinaWeiboShareBean();
		if(qqwechat!=null){
			shareList.add(new TShare("1","微信朋友圈",R.drawable.share_wechat_pengyou_icon,qqwechat));
			shareList.add(new TShare("2","微信好友",R.drawable.share_wechat_friend_icon,qqwechat));
		}
		if(qq!=null){
			shareList.add(new TShare("3","QQ好友",R.drawable.share_qq_icon,qq));
			shareList.add(new TShare("4","QQ空间",R.drawable.share_qq_qzone_icon,qq));
			shareList.add(new TShare("5","腾讯微博",R.drawable.share_tencent_weibo_icon,qq));
		}
		if(sinaweibo!=null){
			shareList.add(new TShare("6","新浪微博",R.drawable.share_sina_weibo_icon,sinaweibo));
		}
		return shareList;
	}
	/**获取登录列表*/
	public static List<TShare> getLoginList(){
		List<TShare> shareList=new ArrayList<TShare>();
		TShare.ShareBean qqwechat=ShareConstants.getQQWechatShareBean();
		TShare.ShareBean qq=ShareConstants.getQQShareBean();
		TShare.ShareBean sinaweibo=ShareConstants.getSinaWeiboShareBean();
		if(qqwechat!=null&&qqwechat.isLogin){
			shareList.add(new TShare("1","微信登录",R.drawable.share_login_wechat_icon,qqwechat));
		}
		if(qq!=null&&qq.isLogin){
			shareList.add(new TShare("2","QQ登录",R.drawable.share_login_qq_icon,qq));
		}
		if(sinaweibo!=null&&sinaweibo.isLogin){
			shareList.add(new TShare("3","新浪微博登录",R.drawable.share_login_sina_weibo_icon,sinaweibo));
		}
		return shareList;
	}
	/**获取QQ TShare.ShareBean*/
	public static TShare.ShareBean getQQShareBean(){
		TShare.ShareBean shareBean=ShareConstants.getShareBeanByShareCode("QQ");
		if(shareBean!=null){
			TShare.ShareConfig shareConfig=shareBean.shareConfig;
			if(shareBean.shareConfig.APP_ID==null||shareConfig.APP_ID.equals("")){
				return null;
			}
			if(shareConfig.APP_KEY==null||shareConfig.APP_KEY.equals("")){
				return null;
			}
			return shareBean;
		}
		return null;
	}
	/**获取微信 TShare.ShareBean*/
	public static TShare.ShareBean getQQWechatShareBean(){
		TShare.ShareBean shareBean=ShareConstants.getShareBeanByShareCode("QQwechat");
		if(shareBean!=null){
			TShare.ShareConfig shareConfig=shareBean.shareConfig;
			if(shareConfig.APP_ID==null||shareConfig.APP_ID.equals("")){
				return null;
			}
			if(shareConfig.APP_Secret==null||shareConfig.APP_Secret.equals("")){
				return null;
			}
			return shareBean;
		}
		return null;
	}
//	/**获取QQ微博 TShare.ShareBean*/
//	public static TShare.ShareBean getQQWeiboShareBean(){
//		TShare.ShareBean shareBean=ShareConstants.getShareBeanByShareCode("QQweibo");
//		if(shareBean!=null){
//			TShare.ShareConfig shareConfig=shareBean.shareConfig;
//			if(shareConfig.APP_KEY==null||shareConfig.APP_KEY.equals("")){
//				return null;
//			}
//			if(shareConfig.APP_Secret==null||shareConfig.APP_Secret.equals("")){
//				return null;
//			}
//			return shareBean;
//		}
//		return null;
//	}
	/**获取新浪微博 TShare.ShareBean*/
	public static TShare.ShareBean getSinaWeiboShareBean(){
		TShare.ShareBean shareBean=ShareConstants.getShareBeanByShareCode("sinaweibo");
		if(shareBean!=null){
			TShare.ShareConfig shareConfig=shareBean.shareConfig;
			if(shareConfig.APP_KEY==null||shareConfig.APP_KEY.equals("")){
				return null;
			}
			return shareBean;
		}
		return null;
	}
	
	/**根据shareCode获取TShare.ShareBean*/
	private static TShare.ShareBean getShareBeanByShareCode(String shareCode){
		for(TShare.ShareBean shareBean:shareBeanList){
			if(shareBean.shareCode!=null&&shareBean.shareCode.equals(shareCode)){
				if(shareBean.shareConfig!=null){
					return shareBean;
				}
			}
		}
		return null;
	}
	
	/**获取新浪REDIRECT_URL*/ 
	public static String getSinaWeiBo_REDIRECT_URL() {
		return "https://api.weibo.com/oauth2/default.html";
	}
	/**获取新浪SCOPE*/ 
	public static String getSinaWeiBo_SCOPE() {
		return "email,direct_messages_read,direct_messages_write,"
				+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
				+ "follow_app_official_microblog," + "invitation_write";
	}
}
