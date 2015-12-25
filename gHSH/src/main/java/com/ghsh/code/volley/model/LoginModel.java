package com.ghsh.code.volley.model;

import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

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
import com.ghsh.util.MenberUtils;

public class LoginModel extends DVolleyModel {
	private final String login_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user&m=login");
	private LoginResponse loginResponse;
	public LoginModel(Context context) {
		super(context);
	}
	/**登录*/
	public void login(String mobile,String password){
		Map<String, String> params = this.newParams();
		params.put("mobile",  mobile);
		params.put("password",EncryptionUtil.MD5_32(password) );
		if(loginResponse==null){
			loginResponse=new LoginResponse(this);
		}
		DVolley.get(login_URL, params,loginResponse);
	}
	
	
	private class LoginResponse extends DResponseService{
		public LoginResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
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
			returnBean.setType(DVolleyConstans.METHOD_USER_LOGIN);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
