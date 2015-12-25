package com.ghsh.share.sina;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.ghsh.Constants;
import com.ghsh.code.bean.TShareLoginBean;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.share.sina.openapi.SinaModel;
import com.ghsh.share.sina.openapi.SinaUserBean;
import com.ghsh.share.sina.openapi.SinaUsersAPI;
import com.ghsh.share.weixin.WeixinLoginListener.AccessTokenBean;
import com.ghsh.share.weixin.manager.WeixinUserBean;
import com.ghsh.R;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

public class SinaWeiboLoginListener implements WeiboAuthListener,DResponseListener{
	private Activity activity;
	private SinaUsersAPI usersAPI;
	private DResponseListener responseListener;
	private SinaModel sinaModel;
	public SinaWeiboLoginListener(Activity activity,DResponseListener responseListener){
		this.activity=activity;
		this.responseListener=responseListener;
		sinaModel=new SinaModel(activity);
		sinaModel.addResponseListener(this);
	}
	@Override
	public void onComplete(Bundle values) {
		//授权成功
		Oauth2AccessToken token = Oauth2AccessToken.parseAccessToken(values);
		if (token.isSessionValid()) {
			//获取用户信息
			sinaModel.getUserinfo(token.getUid(),token.getToken());
//			if(usersAPI==null){
//				usersAPI=new SinaUsersAPI(token);
//			}
//			usersAPI.show(Long.parseLong(token.getUid()), new UserInfoRequestListener());
		} else {
			// 以下几种情况，您会收到 Code：
			// 1. 当您未在平台上注册的应用程序的包名与签名时；
			// 2. 当您注册的应用程序包名与签名不正确时；
			// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
			String code = values.getString("code");
			String message=activity.getResources().getString(R.string.share_auth_fail);
			if (!TextUtils.isEmpty(code)) {
				message = message + "code: " + code;
			}
			responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL, message, new Exception(message));
		}
	}
	
	@Override
	public void onCancel() {
		//授权取消
		String message=activity.getResources().getString(R.string.share_auth_cancel);
		responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL, message, new Exception(message));
	}

	@Override
	public void onWeiboException(WeiboException e) {
		//授权失败
		String message=activity.getResources().getString(R.string.share_auth_fail);
		message=message+":"+e.getMessage();
		responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL, message, new Exception(message));
	}

	
	class UserInfoRequestListener implements RequestListener{
		@Override
		public void onWeiboException(WeiboException e) {
			//授权失败
			String message=activity.getResources().getString(R.string.share_auth_fail);
			message=message+":获取用户信息"+e.getMessage();
			responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL, message, new Exception(message));
		}

		@Override
		public void onComplete(String response) {
			//获取用户信息成功
			if (!TextUtils.isEmpty(response)) {
				SinaUserBean userBean = SinaUserBean.parse(response);
				TShareLoginBean loginBean=new TShareLoginBean(userBean.id+"",Constants.SHARE_LOGIN_SINA_WEIBO);
				loginBean.setNickName(userBean.screen_name);
				loginBean.setUserPortrait(userBean.profile_image_url);
				ReturnBean bean=new ReturnBean();
				bean.setType(DVolleyConstans.METHOD_USER_LOGIN_AUTH);
				bean.setObject(loginBean);
				String message=activity.getResources().getString(R.string.share_auth_success);
				responseListener.onMessageResponse(bean, DVolleyConstans.RESULT_OK, message, null);
			}else{
				//授权失败
				String message=activity.getResources().getString(R.string.share_auth_fail);
				message=message+":"+response;
				responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL, message, new Exception(message));
			}
		}
	}


	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		if(error!=null){
			responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL, message,error);
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_USER_LOGIN_SINA_GET_USERINFO){
				//获取用户信息成功
				SinaUserBean userBean=(SinaUserBean)bean.getObject();
				TShareLoginBean loginBean=new TShareLoginBean(userBean.id+"",Constants.SHARE_LOGIN_SINA_WEIBO);
				loginBean.setNickName(userBean.screen_name);
				loginBean.setUserPortrait(userBean.profile_image_url);
				ReturnBean returnBean=new ReturnBean();
				returnBean.setType(DVolleyConstans.METHOD_USER_LOGIN_AUTH);
				returnBean.setObject(loginBean);
				String temp=activity.getResources().getString(R.string.share_auth_success);
				responseListener.onMessageResponse(returnBean, DVolleyConstans.RESULT_OK, temp, null);
				return;
			}
		}
		message=activity.getResources().getString(R.string.share_auth_fail);
		responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL, message, new Exception(message));
	}
}
