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
/**
 * 注册---手机号码
 * */
public class RegisterMobileModel extends DVolleyModel{
	private final String register_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user&m=registerMobile");
	private final String register_send_verify_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=sms&m=sendRegVerify");
	
	
	private RegisterResponse registerResponse;
	private DResponseService sendVerifyResponse;
	
	public RegisterMobileModel(Context context) {
		super(context);
	}
	/**注册*/
	public void registerMenber(String mobile, String password,String verify){
		Map<String, String> params = RegisterMobileModel.this.newParams();  
		params.put("mobile", mobile);
		params.put("verify", verify);
		params.put("password", EncryptionUtil.MD5_32(password));
		if(registerResponse==null){
			registerResponse=new RegisterResponse(this);
		}
		DVolley.get(register_URL, params,registerResponse);
	}
	
	/**注册--发送验证码*/
	public void sendVerify(String mobile){
		Map<String, String> params = this.newParams();  
		params.put("mobile", mobile);
		if(sendVerifyResponse==null){
			sendVerifyResponse=new SendVerifyResponse(this);
		}
		DVolley.get(register_send_verify_URL, params,sendVerifyResponse);
	}
	
	private class RegisterResponse extends DResponseService{
		public RegisterResponse(DVolleyModel volleyModel) {
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
			returnBean.setType(DVolleyConstans.METHOD_USER_REGISTER);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class SendVerifyResponse extends DResponseService{
		public SendVerifyResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_USER_REGISTER_VERIFY);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
