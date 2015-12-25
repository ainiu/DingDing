package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TShare;
import com.ghsh.code.bean.TShareLoginBean;
import com.ghsh.code.bean.TUsers;
import com.ghsh.code.util.EncryptionUtil;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.code.volley.util.VolleyUtil;

public class ShareModel extends DVolleyModel{
	private final String share_login_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user&m=loginOther");
	private final String share_bindaccount_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user&m=bindLogin");
	private DResponseService shareLoginResponse;
	private DResponseService bindAccountResponse;
	
	public ShareModel(Context context) {
		super(context);
	}
	
	/**第三方登录*/
	public void shareLogin(String openID,String type,String nikeName,String headUrl){
		Map<String, String> params = this.newParams();
		params.put("openID", openID);
		params.put("type", type);
		params.put("nickName", VolleyUtil.encode(nikeName));
		params.put("userPortrait", headUrl);
		if(shareLoginResponse==null){
			shareLoginResponse=new ShareLoginResponse(this);
		}
		DVolley.get(share_login_URL, params,shareLoginResponse);
	}
	/**绑定帐号*/
	public void bindAccount(String openID,String type,String nickName,String portrait,String mobile,String password){
		Map<String, String> params = this.newParams();
		params.put("openID", openID);
		params.put("type", type);
		params.put("portrait", portrait);
		params.put("nickName", VolleyUtil.encode(nickName));
		params.put("mobile", mobile);
		params.put("password", EncryptionUtil.MD5_32(password));
		if(bindAccountResponse==null){
			bindAccountResponse=new BindAccountResponse(this);
		}
		DVolley.get(share_bindaccount_URL, params,bindAccountResponse);
	}
	private class BindAccountResponse extends DResponseService{
		public BindAccountResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_USER_LOGIN_BINDACCOUNT);
			JSONObject contentObject=callResult.getContentObject();
			if(contentObject!=null&&contentObject.length()!=0){
				String userID=JSONUtil.getString(contentObject, "user_id");
				String email=JSONUtil.getString(contentObject, "email");
				String userName=JSONUtil.getString(contentObject, "user_name");
				String mobile=JSONUtil.getString(contentObject, "mobile_phone");
				String nickName=JSONUtil.getString(contentObject, "nicheng");
				
				TUsers users=new TUsers();
				users.setUserID(userID);
				users.setUserName(userName);
				users.setMobile(mobile);
				users.setEmail(email);
				users.setNickName(nickName);
				returnBean.setObject(users);
			}
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	private class ShareLoginResponse extends DResponseService{
		public ShareLoginResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_USER_LOGIN_SHARE);
			JSONObject contentObject=callResult.getContentObject();
			if(contentObject!=null&&contentObject.length()!=0){
				String userID=JSONUtil.getString(contentObject, "user_id");
				String email=JSONUtil.getString(contentObject, "email");
				String userName=JSONUtil.getString(contentObject, "user_name");
				String mobile=JSONUtil.getString(contentObject, "mobile_phone");
				String nickName=JSONUtil.getString(contentObject, "nicheng");
				
				String shareOpenID=JSONUtil.getString(contentObject, "share_open_id");
				String shareType=JSONUtil.getString(contentObject, "share_type");
				String shareNickName=JSONUtil.getString(contentObject, "share_nick_name");
				String sharePortrait=JSONUtil.getString(contentObject, "share_portrait");
				
				TUsers users=new TUsers();
				users.setUserID(userID);
				users.setUserName(userName);
				users.setMobile(mobile);
				users.setEmail(email);
				users.setNickName(nickName);
				
				users.setShareOpenID(shareOpenID);
				users.setShareType(shareType);
				users.setSharePortrait(sharePortrait);
				users.setShareNickName(shareNickName);
				returnBean.setObject(users);
			}
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
}
