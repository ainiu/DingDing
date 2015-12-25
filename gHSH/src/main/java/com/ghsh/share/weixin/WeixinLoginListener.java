package com.ghsh.share.weixin;

import android.app.Activity;

import com.ghsh.Constants;
import com.ghsh.code.bean.TShareLoginBean;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.share.weixin.manager.WeixinCallBackManager;
import com.ghsh.share.weixin.manager.WeixinModel;
import com.ghsh.share.weixin.manager.WeixinUserBean;
import com.ghsh.R;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;

public class WeixinLoginListener implements WeixinCallBackManager.Listener,DResponseListener{
	private Activity activity;
	private DResponseListener responseListener;
	private WeixinModel weixinModel;
	private String appid,appSecret;
	public WeixinLoginListener(Activity activity,DResponseListener responseListener,String appid,String appSecret){
		this.activity=activity;
		this.responseListener=responseListener;
		this.appid=appid;
		this.appSecret=appSecret;
		weixinModel=new WeixinModel(activity);
		weixinModel.addResponseListener(this);
	}
	
	@Override
	public void onWeixincallBack(BaseResp resp) {
		String message="";
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				//授权成功,获取AccessToken
				String code = ((SendAuth.Resp) resp).code; //即为所需的code
				weixinModel.getAccessToken(appid, appSecret, code);
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				//授权取消
				message=activity.getResources().getString(R.string.share_auth_cancel);
				responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL, message, new Exception(message));
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				//授权拒绝
			default:
				//授权返回
				message=activity.getResources().getString(R.string.share_auth_fail);
				message=message+":"+resp.errStr;
				responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL, message, new Exception(message));
				break;
		}
	}

	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		if(error!=null){
			responseListener.onMessageResponse(null, DVolleyConstans.RESULT_FAIL, message,error);
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_USER_LOGIN_WEIXIN_GET_ACCESSTOKEN){
				//获取AccessToken返回
				AccessTokenBean accessTokenBean=(AccessTokenBean)bean.getObject();
				weixinModel.getUserinfo(accessTokenBean.access_token, accessTokenBean.openid);
				return;
			}else if(bean.getType()==DVolleyConstans.METHOD_USER_LOGIN_WEIXIN_GET_USERINFO){
				//获取用户信息成功
				WeixinUserBean weixinUserBean=(WeixinUserBean)bean.getObject();
				TShareLoginBean loginBean=new TShareLoginBean(weixinUserBean.getOpenid()+"",Constants.SHARE_LOGIN_WEIXIN);
				loginBean.setNickName(weixinUserBean.getNickname());
				loginBean.setUserPortrait(weixinUserBean.getHeadimgurl());
				
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
	
	public static class AccessTokenBean{
		public String access_token;
		public String expires_in;
		public String refresh_token;
		public String openid;
		public String scope;
	}
}
