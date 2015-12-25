package com.ghsh.share.sina;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.ghsh.ShareConstants;
import com.ghsh.share.sina.openapi.SinaWeiboAccessTokenKeeper;
import com.ghsh.R;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboDownloadListener;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;

public class SinaWeiboShare implements IWeiboDownloadListener,IWeiboHandler.Response{
	/** 微博 Web 授权类，提供登陆等功能 */
	private WeiboAuth mWeiboAuth;
	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能 */
	private Oauth2AccessToken mAccessToken;
	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private SsoHandler mSsoHandler;
	/** 微博分享的接口实例 */
	private IWeiboShareAPI mWeiboShareAPI;
	private Activity activity;
	private String APP_KEY;
	public SinaWeiboShare(Activity activity,String APP_KEY) {
		this.activity = activity;
		this.APP_KEY=APP_KEY;
	}

	public void handleWeiboResponse(Intent intent) {
		mWeiboShareAPI.handleWeiboResponse(intent, this);
	}

	/** 初始化 */
	public void init(Bundle savedInstanceState) {
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(activity,APP_KEY);
		// 如果未安装微博客户端，设置下载微博对应的回调
		if (!mWeiboShareAPI.isWeiboAppInstalled()) {
			mWeiboShareAPI.registerWeiboDownloadListener(this);
		}else{
			mWeiboShareAPI.registerApp();
			if (savedInstanceState != null) {
				mWeiboShareAPI.handleWeiboResponse(activity.getIntent(), this);
			}
		}
	}
	
	/** 授权 */
	public void authLogin(WeiboAuthListener listener) {
		mWeiboAuth = new WeiboAuth(activity,APP_KEY,ShareConstants.getSinaWeiBo_REDIRECT_URL(),ShareConstants.getSinaWeiBo_SCOPE());
		mSsoHandler = new SsoHandler(activity, mWeiboAuth);
		mSsoHandler.authorize(listener);
		
	}
	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	/**SSO授权需要在onActivityResult调用此方法*/
	public void authorizeCallBack(int requestCode, int resultCode, Intent data){
		if(mSsoHandler!=null){
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}
	
	/** 分享 */
	public void shearSinaWeibo(String text, Bitmap shareImage) {
		if (!mWeiboShareAPI.isWeiboAppInstalled()) {
			Toast.makeText(activity, "请先安装微博客户端！",Toast.LENGTH_SHORT).show();
			return;
		}
		if (!mWeiboShareAPI.isWeiboAppSupportAPI()) {
			Toast.makeText(activity, "微博客户端不支持 SDK 分享或微博客户端未安装或微博客户端是非官方版本。",Toast.LENGTH_SHORT).show();
			return;
		}
		Oauth2AccessToken token=SinaWeiboAccessTokenKeeper.readAccessToken(activity);
		if(token.getToken()==null||token.getToken().equals("")){
//			this.auth();
//			return;
		}
		if (mWeiboShareAPI.getWeiboAppSupportAPI() >= 10351) {
			WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
			// 初始化微博的分享消息
			TextObject textObject = new TextObject();
			textObject.text = text;
			weiboMessage.textObject = textObject;
			if (shareImage != null) {
				ImageObject imageObject = new ImageObject();
				imageObject.setImageObject(shareImage);
				weiboMessage.imageObject = imageObject;
			}
			SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
			request.transaction = String.valueOf(System.currentTimeMillis());
			request.multiMessage = weiboMessage;
			mWeiboShareAPI.sendRequest(request);
		} else {
			// 用户可以分享文本、图片、网页、音乐、视频中的一种
			WeiboMessage weiboMessage = new WeiboMessage();
			if (text != null && !text.equals("")) {
				TextObject textObject = new TextObject();
				textObject.text = text;
				weiboMessage.mediaObject = textObject;
			}
			if (shareImage != null) {
				ImageObject imageObject = new ImageObject();
				imageObject.setImageObject(shareImage);
				weiboMessage.mediaObject = imageObject;
			}
			SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
			request.transaction = String.valueOf(System.currentTimeMillis());
			request.message = weiboMessage;
			mWeiboShareAPI.sendRequest(request);
		}
	}

	

	@Override
	public void onCancel() {
		Toast.makeText(activity, "下载微博回调", Toast.LENGTH_SHORT).show();
	}
	/**分享*/
	@Override
	public void onResponse(BaseResponse baseResp) {
		// 接收微客户端博请求的数据
		switch (baseResp.errCode) {
		case WBConstants.ErrorCode.ERR_OK:
			Toast.makeText(activity, R.string.share_success, Toast.LENGTH_LONG).show();
			break;
		case WBConstants.ErrorCode.ERR_CANCEL:
			Toast.makeText(activity, R.string.share_cancel, Toast.LENGTH_LONG).show();
			break;
		case WBConstants.ErrorCode.ERR_FAIL:
			Toast.makeText(activity, R.string.share_fail, Toast.LENGTH_LONG).show();
			break;
		}
	}
}
