package com.ghsh.code.volley.model;

import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TUsers;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.code.volley.util.VolleyUtil;
import com.ghsh.util.MenberUtils;
/**
 * 注册--第三方
 * */
public class RegisterOtherModel extends DVolleyModel{
	private final String register_orther_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user&m=registerOther");
	
	private DResponseService otherRegisterResponse;
	
	public RegisterOtherModel(Context context) {
		super(context);
	}
	/**第三方注册*/
	public void registerOtherMenber(String openID, String type,String nikeName,String portrait){
		Map<String, String> params = this.newParams();  
		params.put("openID", openID);
		params.put("type", type);
		params.put("nikeName", VolleyUtil.encode(nikeName));
		params.put("portrait", portrait);
		if(otherRegisterResponse==null){
			otherRegisterResponse=new OtherRegisterResponse(this);
		}
		DVolley.get(register_orther_URL, params,otherRegisterResponse);
	}
	
	private class OtherRegisterResponse extends DResponseService{
		public OtherRegisterResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_USER_REGISTER);
			JSONObject contentObject=callResult.getContentObject();
			if(contentObject!=null){
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
}
