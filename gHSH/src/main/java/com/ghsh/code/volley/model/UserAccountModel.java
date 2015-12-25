package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TPayBean;
import com.ghsh.code.bean.TWithdrawRecode;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.code.volley.util.VolleyUtil;
/**
 * 账户明细model
 * */
public class UserAccountModel extends DVolleyModel{
	
	private final String find_record_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user_account&m=findAccountRecord");
	private final String add_withdraw_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user_account&m=addWithdraw");
	private final String add_recharge_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user_account&m=addRecharge");
	private DResponseService findRecordListResponse;
	private DResponseService addWithdrawResponse;
	private DResponseService addRechargeResponse;
	public UserAccountModel(Context context) {
		super(context);
	}
	
	/**查询账户明细*/ 
	public void findRecordList(String userID,int currentPage){
		Map<String, String> params = this.newParams();
		params.put("userID", userID);
		params.put("currentPage", currentPage+"");
		if(findRecordListResponse==null){
			findRecordListResponse=new FindRecordListResponse(this);
		}
		DVolley.get(find_record_list_URL, params,findRecordListResponse);
	}
	
	/**申请提现*/ 
	public void addWithdraw(String userID,String money,String trueName,String mobile,String bankName,String bankNum,String desc){
		Map<String, String> params = this.newParams();
		params.put("userID", userID);
		params.put("money", money);
		params.put("trueName", VolleyUtil.encode(trueName));
		params.put("mobile", mobile);
		params.put("bankName",  VolleyUtil.encode(bankName));
		params.put("bankNum", bankNum);
		params.put("desc", VolleyUtil.encode(desc));
		if(addWithdrawResponse==null){
			addWithdrawResponse=new AddWithdrawResponse(this);
		}
		DVolley.get(add_withdraw_URL, params,addWithdrawResponse);
	}
	
	/**充值*/ 
	public void addRecharge(String userID,String money,String desc,String paymentID){
		Map<String, String> params = this.newParams();
		params.put("userID", userID);
		params.put("money", money);
		params.put("mobile", paymentID);
		params.put("desc", VolleyUtil.encode(desc));
		if(addRechargeResponse==null){
			addRechargeResponse=new AddRechargeResponse(this);
		}
		DVolley.get(add_recharge_URL, params,addRechargeResponse);
	}
	
	
	
	private class FindRecordListResponse extends DResponseService{
		public FindRecordListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			List<TWithdrawRecode> list=new ArrayList<TWithdrawRecode>();
			JSONArray contentArray=callResult.getContentArray();
			if(contentArray!=null&&contentArray.length()!=0){
				for(int i=0;i<contentArray.length();i++){
					JSONObject recordObject=contentArray.getJSONObject(i);
					String id=JSONUtil.getString(recordObject,"id");
					String title=JSONUtil.getString(recordObject,"title");
					String desc=JSONUtil.getString(recordObject,"desc");
					String money=JSONUtil.getString(recordObject,"amount");
					String addTime=JSONUtil.getString(recordObject,"add_time");
					String status=JSONUtil.getString(recordObject,"status");
					
					TWithdrawRecode withdrawRecode=new TWithdrawRecode();
					withdrawRecode.setId(id);
					withdrawRecode.setTitle(title);
					withdrawRecode.setDesc(desc);
					withdrawRecode.setMoney(money);
					withdrawRecode.setAddTime(addTime);
					withdrawRecode.setStatus(status);
					list.add(withdrawRecode);
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	private class AddWithdrawResponse extends DResponseService{
		public AddWithdrawResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_ADD);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	private class AddRechargeResponse extends DResponseService{
		public AddRechargeResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			JSONObject contentObject=callResult.getContentObject();
			if(contentObject!=null&&contentObject.length()!=0){
				String paymentCode=JSONUtil.getString(contentObject, "paymentCode");
				String desc=JSONUtil.getString(contentObject, "desc");
				TPayBean payBean=new TPayBean();
				payBean.setPaymentCode(paymentCode);
				payBean.setDesc(desc);
				returnBean.setObject(payBean);
			}
			returnBean.setType(DVolleyConstans.METHOD_ADD);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
