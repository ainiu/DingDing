package com.ghsh.code.volley.model;

import java.math.BigDecimal;
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
/**
 * 用户
 * */
public class UserModel extends DVolleyModel{
	private final String query_base_by_userid_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user&m=getUserInfo");
	private final String modify_pwd_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user&m=changePassword");
	private final String modify_userinfo_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user&m=modifyUserInfo");
	private final String get_money_point_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user&m=getMoneyPoint");
	
	private DResponseService getUserInfoResponse=null;
	private DResponseService modifyPwdResponse=null;
	private DResponseService modifyUserInfoResponse=null;
	private DResponseService getMoneyAndPointResponse=null;
	public UserModel(Context context) {
		super(context);
	}
	
	/**查询余额和积分*/
	public void getMoneyAndPointID(String userID){
		Map<String, String> params = this.newParams();  
		params.put("userID", userID);
		if(getMoneyAndPointResponse==null){
			getMoneyAndPointResponse=new GetMoneyAndPointResponse(this);
		}
		DVolley.get(get_money_point_URL, params,getMoneyAndPointResponse);
	}
	/**根据用户名-查询用户基本信息*/
	public void getUserInfo(String userID){
		Map<String, String> params = this.newParams();  
		params.put("userID", userID);
		if(getUserInfoResponse==null){
			getUserInfoResponse=new GetUserInfoResponse(this);
		}
		DVolley.get(query_base_by_userid_URL, params,getUserInfoResponse);
	}
	/**修改密码*/
	public void modifyPwd(String userID,String oldPwd,String newPwd){
		Map<String, String> params = this.newParams();  
		params.put("userID", userID);
		params.put("password", EncryptionUtil.MD5_32(oldPwd));
		params.put("newPassword", EncryptionUtil.MD5_32(newPwd));
		if(modifyPwdResponse==null){
			modifyPwdResponse=new ModifyPwdResponse(this);
		}
		DVolley.get(modify_pwd_URL, params,modifyPwdResponse);
	}
	/**修改用户信息*/
	public void modifyUserInfo(String userID,String columnName,String columnValue){
		Map<String, String> params = this.newParams();  
		params.put("userID", userID);
		params.put("columnName",columnName);
		params.put("columnValue", VolleyUtil.encode(columnValue));
		if(modifyUserInfoResponse==null){
			modifyUserInfoResponse=new ModifyUserInfoResponse(this);
		}
		DVolley.get(modify_userinfo_URL, params,modifyUserInfoResponse);
	}
	
	private class GetMoneyAndPointResponse extends DResponseService{
		public GetMoneyAndPointResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			JSONObject contentObject=callResult.getContentObject();
			ReturnBean returnBean=new ReturnBean();
			if(contentObject!=null&&contentObject.length()!=0){
				BigDecimal money=JSONUtil.getBigDecimal(contentObject, "user_money");
				int payPoints=JSONUtil.getInt(contentObject, "pay_points");
				TUsers user=new TUsers();
				user.setMoney(money);
				user.setPoint(payPoints);
				returnBean.setObject(user);
			}
			returnBean.setType(DVolleyConstans.METHOD_USER_MONEY_AND_POINT);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	/**修改用户信息返回*/
	private class ModifyUserInfoResponse extends DResponseService{
		public ModifyUserInfoResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_USER_MODIFY_USERINFO);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	/**修改密码返回*/
	private class ModifyPwdResponse extends DResponseService{
		public ModifyPwdResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_USER_MODIFY_PWD);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	/**查询用户信息*/
	private class GetUserInfoResponse extends DResponseService{
		public GetUserInfoResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_USER_GET_BASEINFO);
			JSONObject contentObject=callResult.getContentObject();
			if(contentObject!=null){
				String userID=JSONUtil.getString(contentObject,"user_id");
				String userName=JSONUtil.getString(contentObject,"user_name");
				String email=JSONUtil.getString(contentObject,"email");
				String nickName=JSONUtil.getString(contentObject,"nicheng");
				String birthday=JSONUtil.getString(contentObject,"birthday");
				String mobile=JSONUtil.getString(contentObject,"mobile_phone");
				String regTime=JSONUtil.getString(contentObject,"reg_time");
				String lastLogin=JSONUtil.getString(contentObject,"last_login");
				String portraitURL=JSONUtil.getString(contentObject,"portrait");
				
				TUsers user=new TUsers();
				user.setUserID(userID);
				user.setUserName(userName);
				user.setEmail(email);
				user.setNickName(nickName);
				user.setBirthDay(birthday);
				user.setMobile(mobile);
				user.setRegTime(regTime);
				user.setLastTime(lastLogin);
				user.setPortraitURL(DVolleyConstans.getServiceHost(portraitURL));
				returnBean.setObject(user);
			}
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
