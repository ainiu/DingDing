package com.ghsh.share.weixin.manager;

import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.share.weixin.WeixinLoginListener;

public class WeixinModel extends DVolleyModel{
	private final String accessToken_URL="https://api.weixin.qq.com/sns/oauth2/access_token";
	private final String userinfo_URL="https://api.weixin.qq.com/sns/userinfo";
	private DResponseService accessTokenResponse;
	private DResponseService userinfoResponse;
	public WeixinModel(Context context) {
		super(context);
	}
	/**获取AccessToken*/
	public void getAccessToken(String appid,String appSecret,String code){
		Map<String, String> params = this.newParams();
		params.put("appid", appid);
		params.put("secret", appSecret);
		params.put("code", code);
		params.put("grant_type", "authorization_code");
		if(accessTokenResponse==null){
			accessTokenResponse=new AccessTokenResponse(this); 
		}
		DVolley.get(accessToken_URL, params,accessTokenResponse);
	}
	/**获取用户信息*/
	public void getUserinfo(String access_token,String openid){
		Map<String, String> params = this.newParams();
		params.put("access_token", access_token);
		params.put("openid", openid);
		if(userinfoResponse==null){
			userinfoResponse=new UserinfoResponse(this); 
		}
		DVolley.get(userinfo_URL, params,userinfoResponse);
	}
	private class UserinfoResponse extends DResponseService {
		public UserinfoResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		public void myOnResponse(DCallResult callResult) throws Exception {
			JSONObject jsonObject=callResult.getResponse();
			if(jsonObject==null||jsonObject.length()==0){
				//获取用户信息错误
				ReturnBean returnBean=new ReturnBean();
				returnBean.setType(DVolleyConstans.METHOD_USER_LOGIN_WEIXIN_GET_USERINFO);
				volleyModel.onMessageResponse(returnBean,DVolleyConstans.RESULT_FAIL,null,new Exception("获取用户信息失败"));
				return;
			}
			String errcode=JSONUtil.getString(jsonObject, "errcode");
			String errmsg=JSONUtil.getString(jsonObject, "errmsg");
			if(errcode!=null&&!errcode.equals("")){
				//获取用户信息错误
				ReturnBean returnBean=new ReturnBean();
				returnBean.setType(DVolleyConstans.METHOD_USER_LOGIN_WEIXIN_GET_USERINFO);
				volleyModel.onMessageResponse(returnBean,DVolleyConstans.RESULT_FAIL,null,new Exception("获取用户信息失败:"+"errcode="+errcode+" errmsg="+errmsg));
				return;
			}
			
			String openid=JSONUtil.getString(jsonObject, "openid");
			String nickname=JSONUtil.getString(jsonObject, "nickname");
			String sex=JSONUtil.getString(jsonObject, "sex");
			String province=JSONUtil.getString(jsonObject, "province");
			String city=JSONUtil.getString(jsonObject, "city");
			String country=JSONUtil.getString(jsonObject, "country");
			String headimgurl=JSONUtil.getString(jsonObject, "headimgurl");
//			String privilege=JSONUtil.getString(jsonObject, "privilege");
			String unionid=JSONUtil.getString(jsonObject, "unionid");
			
			
			WeixinUserBean weixinUserBean=new WeixinUserBean();
			weixinUserBean.setOpenid(openid);
			weixinUserBean.setNickname(new String(nickname.getBytes("ISO-8859-1"), "UTF-8"));
			weixinUserBean.setSex(sex);
			weixinUserBean.setProvince(province);
			weixinUserBean.setCity(city);
			weixinUserBean.setCountry(country);
			weixinUserBean.setHeadimgurl(headimgurl);
			weixinUserBean.setUnionid(unionid);
			
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_USER_LOGIN_WEIXIN_GET_USERINFO);
			returnBean.setObject(weixinUserBean);
			volleyModel.onMessageResponse(returnBean,DVolleyConstans.RESULT_OK,"获取用户信息成功",null);
		}
	}
	private class AccessTokenResponse extends DResponseService {
		public AccessTokenResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}

		@Override
		public void myOnResponse(DCallResult callResult) throws Exception {
			JSONObject jsonObject=callResult.getResponse();
			if(jsonObject==null||jsonObject.length()==0){
				//获取AccessToken错误
				ReturnBean returnBean=new ReturnBean();
				returnBean.setType(DVolleyConstans.METHOD_USER_LOGIN_WEIXIN_GET_ACCESSTOKEN);
				volleyModel.onMessageResponse(returnBean,DVolleyConstans.RESULT_FAIL,null,new Exception("获取AccessToken失败"));
				return;
			}
			String errcode=JSONUtil.getString(jsonObject, "errcode");
			String errmsg=JSONUtil.getString(jsonObject, "errmsg");
			if(errcode!=null&&!errcode.equals("")){
				//获取AccessToken错误
				ReturnBean returnBean=new ReturnBean();
				returnBean.setType(DVolleyConstans.METHOD_USER_LOGIN_WEIXIN_GET_ACCESSTOKEN);
				volleyModel.onMessageResponse(returnBean,DVolleyConstans.RESULT_FAIL,null,new Exception("获取AccessToken失败:"+"errcode="+errcode+" errmsg="+errmsg));
				return;
			}
			
			String access_token=JSONUtil.getString(jsonObject, "access_token");
			String expires_in=JSONUtil.getString(jsonObject, "expires_in");
			String refresh_token=JSONUtil.getString(jsonObject, "refresh_token");
			String openid=JSONUtil.getString(jsonObject, "openid");
			String scope=JSONUtil.getString(jsonObject, "scope");
			WeixinLoginListener.AccessTokenBean accessTokenBean=new WeixinLoginListener.AccessTokenBean();
			accessTokenBean.access_token=access_token;
			accessTokenBean.expires_in=expires_in;
			accessTokenBean.refresh_token=refresh_token;
			accessTokenBean.openid=openid;
			accessTokenBean.scope=scope;
			
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_USER_LOGIN_WEIXIN_GET_ACCESSTOKEN);
			returnBean.setObject(accessTokenBean);
			volleyModel.onMessageResponse(returnBean,DVolleyConstans.RESULT_OK,"获取accessToken成功",null);
		}
	}
}
