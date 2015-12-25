package com.ghsh.code.volley.model;

import java.util.Map;

import android.content.Context;

import com.ghsh.code.util.EncryptionUtil;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;

public class ForgetpwdModel extends DVolleyModel{
	private final String forgetpwd_verify_send_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=sms&m=sendForgetpwdVerify");
	private final String forgetpwd_verify_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=forgetpwd&m=forgetpwdVerify");
	private final String forgetpwd_setNewPwd_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=forgetpwd&m=forgetpwdSetPwd");
	
	private DResponseService forgetpwdVerifySendResponse;
	private DResponseService forgetpwdVerifyResponse;
	private DResponseService forgetpwdSetNewPwdResponse;
	
	public ForgetpwdModel(Context context) {
		super(context);
	}
	
	/**找回密码--发送验证码*/
	public void forgetpwdVerifySend(String mobile){
		Map<String, String> params = this.newParams();
		params.put("mobile", mobile);
		if(forgetpwdVerifySendResponse==null){
			forgetpwdVerifySendResponse=new ForgetpwdVerifySendResponse(this);
		}
		DVolley.get(forgetpwd_verify_send_URL, params,forgetpwdVerifySendResponse);
	}
	/**找回密码--验证码 验证*/
	public void forgetpwdVerify(String mobile,String verify){
		Map<String, String> params = this.newParams();
		params.put("mobile",  mobile);
		params.put("verify", verify);
		if(forgetpwdVerifyResponse==null){
			forgetpwdVerifyResponse=new ForgetpwdVerifyResponse(this);
		}
		DVolley.get(forgetpwd_verify_URL, params,forgetpwdVerifyResponse);
	}
	/**找回密码--设置新密码*/
	public void forgetpwdSetNewPwd(String mobile,String verify,String password){
		Map<String, String> params = this.newParams();
		params.put("verify", verify);
		params.put("mobile",  mobile);
		params.put("password",EncryptionUtil.MD5_32(password));
		if(forgetpwdSetNewPwdResponse==null){
			forgetpwdSetNewPwdResponse=new ForgetpwdSetNewPwdResponse(this);
		}
		DVolley.get(forgetpwd_setNewPwd_URL, params,forgetpwdSetNewPwdResponse);
	}
	
	private class ForgetpwdSetNewPwdResponse extends DResponseService{
		public ForgetpwdSetNewPwdResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_USER_FORGETPWD_NEW);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	private class ForgetpwdVerifySendResponse extends DResponseService{
		public ForgetpwdVerifySendResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_USER_FORGETPWD_VERIFY_SEND);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
		
	}
	private class ForgetpwdVerifyResponse extends DResponseService{
		public ForgetpwdVerifyResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_USER_FORGETPWD_VERIFY);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
