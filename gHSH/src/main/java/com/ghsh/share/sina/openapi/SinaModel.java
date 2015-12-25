package com.ghsh.share.sina.openapi;

import java.util.HashMap;
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

public class SinaModel extends DVolleyModel{
	private final String userinfo_URL="https://api.weibo.com/2/users/show.json";
	private DResponseService userinfoResponse;
	public SinaModel(Context context) {
		super(context);
	}
	
	/**获取用户信息*/
	public void getUserinfo(String uid,String access_token){
		Map<String, String> params = new HashMap<String, String>();;
		params.put("uid", uid);
		params.put("access_token", access_token);
		params.put("source", "2751934534");
		
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
				returnBean.setType(DVolleyConstans.METHOD_USER_LOGIN_SINA_GET_USERINFO);
				volleyModel.onMessageResponse(returnBean,DVolleyConstans.RESULT_FAIL,null,new Exception("获取用户信息失败"));
				return;
			}
			
			
			String request=JSONUtil.getString(jsonObject, "request");
			String error_code=JSONUtil.getString(jsonObject, "error_code");
			String error=JSONUtil.getString(jsonObject, "error");
			if(error!=null&&!error.equals("")){
				//获取用户信息错误
				ReturnBean returnBean=new ReturnBean();
				returnBean.setType(DVolleyConstans.METHOD_USER_LOGIN_SINA_GET_USERINFO);
				volleyModel.onMessageResponse(returnBean,DVolleyConstans.RESULT_FAIL,null,new Exception("获取用户信息失败:"+"request="+request+" error_code="+error_code+" error="+error));
				return;
			}
			SinaUserBean userBean = SinaUserBean.parse(jsonObject);
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_USER_LOGIN_SINA_GET_USERINFO);
			returnBean.setObject(userBean);
			volleyModel.onMessageResponse(returnBean,DVolleyConstans.RESULT_OK,"获取用户信息成功",null);
		}
	}
	
}
