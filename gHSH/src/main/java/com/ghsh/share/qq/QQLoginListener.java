package com.ghsh.share.qq;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

import com.ghsh.Constants;
import com.ghsh.code.bean.TShareLoginBean;
import com.ghsh.code.exception.AppViewException;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.R;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class QQLoginListener implements IUiListener{
	private Activity activity;
	private DResponseListener responseListener;
	private UserInfo userInfo;
	private Tencent mTencent;
	public QQLoginListener(Activity activity,DResponseListener responseListener,Tencent mTencent){
		this.activity=activity;
		this.mTencent= mTencent;
		this.responseListener=responseListener;
	}
	@Override
	public void onComplete(Object object) {
		//授权成功
		try {
			if(Constants.DEBUG){
				Log.i("QQLoginListener 登录授权成功1：", object+"");
			}
			JSONObject jsonObject=new JSONObject(object.toString());
			String openID=JSONUtil.getString(jsonObject, com.tencent.connect.common.Constants.PARAM_OPEN_ID);
			String token = JSONUtil.getString(jsonObject, com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
	        String expires = JSONUtil.getString(jsonObject, com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
			if(userInfo==null){
				userInfo = new UserInfo(activity,mTencent.getQQToken());
			}
			userInfo.getUserInfo(new UserInfoIUiListener(openID));
		} catch (JSONException e) {
			AppViewException.onViewException(e);
			responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL, null, e);
		}
	}
	@Override
	public void onCancel() {
		//授权取消
		String message=activity.getResources().getString(R.string.share_auth_cancel);
		responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL,message, new Exception(message));
	}

	@Override
	public void onError(UiError error) {
		String message=activity.getResources().getString(R.string.share_auth_fail);
		responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL,message, new Exception(message));
	}
	
	class UserInfoIUiListener implements IUiListener{
		private String openID;
		public UserInfoIUiListener(String openID){
			this.openID=openID;
		}
		@Override
		public void onCancel() {
			String message=activity.getResources().getString(R.string.share_auth_cancel);
			responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL,message, new Exception(message));
		}
		
		@Override
		public void onComplete(Object object) {
			try {
				if(Constants.DEBUG){
					Log.i("QQLoginListener 获取QQ用户信息成功：", object+"");
				}
				QQUserBean qqUserBean = QQUserBean.parse(object+"");
				if(qqUserBean.ret==0){
					//获取用户信息成功
					TShareLoginBean loginBean=new TShareLoginBean(openID,Constants.SHARE_LOGIN_QQ);
					loginBean.setNickName(qqUserBean.nickname);
					loginBean.setUserPortrait(qqUserBean.figureurl_qq_2);
					ReturnBean bean=new ReturnBean();
					bean.setType(DVolleyConstans.METHOD_USER_LOGIN_AUTH);
					bean.setObject(loginBean);
					String message=activity.getResources().getString(R.string.share_auth_success);
					responseListener.onMessageResponse(bean, DVolleyConstans.RESULT_OK, message, null);
				}else{
					//获取用户信息失败
					String message=activity.getResources().getString(R.string.share_auth_fail);
					responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL,message, new Exception(message));
				}
			} catch (JSONException e) {
				AppViewException.onViewException(e);
				String message=activity.getResources().getString(R.string.share_auth_fail);
				responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL,message, new Exception(message));
			}
		}

		@Override
		public void onError(UiError error) {
			String message=activity.getResources().getString(R.string.share_auth_fail)+": " + error.errorMessage;
			responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL,message, new Exception(message));
		}
	}
}
