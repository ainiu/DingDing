package com.ghsh.share.tencent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.ghsh.Constants;
import com.ghsh.share.qq.QQShareWeiboDialog;
import com.ghsh.view.DToastView;
import com.ghsh.R;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.Authorize;
import com.tencent.weibo.sdk.android.component.sso.AuthHelper;
import com.tencent.weibo.sdk.android.component.sso.OnAuthListener;
import com.tencent.weibo.sdk.android.component.sso.WeiboToken;

public class TencentWeiboShare {
	
	private Activity activity;
	private String APP_KEY,App_Secret;
	private String shareTitle;
	private String shareImageUrl;
	private Bitmap shareImageBitmap;
	public TencentWeiboShare(Activity activity,String APP_KEY,String App_Secret) {
		this.activity = activity;
		this.APP_KEY=APP_KEY;
		this.App_Secret=App_Secret;
		
		
//		if(tencentWeiboShare==null){
//		tencentWeiboShare=new TencentWeiboShare(this,shareConfig.APP_KEY,shareConfig.APP_Secret);
//	}
//	tencentWeiboShare.shearWeibo(goods.getName(), goods.getDefaultImage(),shareImage);
	}
	
	/** 授权 */
	public void auth() {
		// 注册当前应用的appid和appkeysec，并指定一个OnAuthListener
		// OnAuthListener在授权过程中实施监听
		AuthHelper.register(activity,  Long.valueOf(APP_KEY), App_Secret, new OnAuthListener() {
			// 如果当前设备没有安装腾讯微博客户端，走这里
			@Override
			public void onWeiBoNotInstalled() {
				DToastView.makeText(activity, "安装腾讯微博客户端",Toast.LENGTH_SHORT).show();
				AuthHelper.unregister(activity);
				Intent i = new Intent(activity, Authorize.class);
				activity.startActivity(i);
			}
			// 如果当前设备没安装指定版本的微博客户端，走这里
			@Override
			public void onWeiboVersionMisMatch() {
				DToastView.makeText(activity, "没有安装微博客户端",Toast.LENGTH_SHORT).show();
				AuthHelper.unregister(activity);
				Intent i = new Intent(activity, Authorize.class);
				activity.startActivity(i);
			}
			// 如果授权失败，走这里
			@Override
			public void onAuthFail(int result, String err) {
				DToastView.makeText(activity, R.string.share_auth_fail, Toast.LENGTH_SHORT).show();
				AuthHelper.unregister(activity);
			}
			// 授权成功，走这里
			// 授权成功后，所有的授权信息是存放在WeiboToken对象里面的，可以根据具体的使用场景，将授权信息存放到自己期望的位置，
			// 在这里，存放到了applicationcontext中
			@Override
			public void onAuthPassed(String name, WeiboToken token) {
				Util.saveSharePersistent(activity, "ACCESS_TOKEN",token.accessToken);
				Util.saveSharePersistent(activity, "EXPIRES_IN",String.valueOf(token.expiresIn));
				Util.saveSharePersistent(activity, "OPEN_ID", token.openID);
				// Util.saveSharePersistent(context, "OPEN_KEY", token.omasKey);
				Util.saveSharePersistent(activity, "REFRESH_TOKEN", "");
				// Util.saveSharePersistent(context, "NAME", name);
				// Util.saveSharePersistent(context, "NICK", name);
				Util.saveSharePersistent(activity, "CLIENT_ID", Util.getConfig().getProperty("APP_KEY"));
				Util.saveSharePersistent(activity, "AUTHORIZETIME",String.valueOf(System.currentTimeMillis() / 1000l));
				AuthHelper.unregister(activity);
				if(Constants.DEBUG){
					Log.i("TencentWeiboShare 腾讯微博授权成功：", "name:"+name+" token:"+token.accessToken);
				}
				TencentWeiboShare.this.shearWeibo(shareTitle, shareImageUrl, shareImageBitmap);
			}
		});
		AuthHelper.auth(activity, "");
	}
	
	/** 分享 */
	public void shearWeibo(String shareTitle,String shareImageUrl,Bitmap shareImageBitmap){
		this.shareTitle=shareTitle;
		this.shareImageUrl=shareImageUrl;
		this.shareImageBitmap=shareImageBitmap;
		String accessToken=Util.getSharePersistent(activity, "ACCESS_TOKEN");
		if(accessToken==null||accessToken.equals("")){
			this.auth();
		}else{
			QQShareWeiboDialog qqShareWeiboDialog=new QQShareWeiboDialog(activity);
			qqShareWeiboDialog.showWeibo(shareTitle, shareImageUrl, shareImageBitmap, accessToken);
		}
	}
}
