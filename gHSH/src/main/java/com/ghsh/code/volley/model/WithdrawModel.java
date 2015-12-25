package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TBank;
import com.ghsh.code.bean.TBankAccount;
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
 * 提现
 * */
public class WithdrawModel extends DVolleyModel {
	private final String find_user_cart_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=cash&m=getBank_user");
	private final String find_bank_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=cash&m=getBank");
	
	private final String add_bankCard_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=cash&m=getAdd_bankcard");
	private final String add_user_cart_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=cash&m=getAdd_cash_record");
	
	
	private DResponseService findUserCartListResponse;
	private DResponseService findBankListResponse;
	
	private DResponseService addBankCardResponse;
	private DResponseService addUserBankCardResponse;
	public WithdrawModel(Context context) {
		super(context);
	}
	/**获取我的银行卡列表*/ 
	public void findUserCartList(String userID){
		Map<String, String> params = this.newParams();
		params.put("userID", userID);
		if(findUserCartListResponse==null){
			findUserCartListResponse=new FindUserCartListResponse(this);
		}
		DVolley.get(find_user_cart_list_URL, params,findUserCartListResponse);
	}
	/**获取银行记录*/ 
	public void findBankList(){
		Map<String, String> params = this.newParams();
		if(findBankListResponse==null){
			findBankListResponse=new FindBankListResponse(this);
		}
		DVolley.get(find_bank_list_URL, params,findBankListResponse);
	}
	
	/**添加银行卡*/ 
	public void addBankCard(String userID,String bankID,String bankSN,String name,String mobile){
		Map<String, String> params = this.newParams();
		params.put("userID", userID);
		params.put("bank_id", bankID);
		params.put("bank_sn", bankSN);
		params.put("user_name", name);
		params.put("user_phone", mobile);
		if(addBankCardResponse==null){
			addBankCardResponse=new AddBankCardResponse(this);
		}
		DVolley.get(add_bankCard_URL, params,addBankCardResponse);
	}
	
	/**提现到银行卡*/ 
	public void addUserBankCard(String userID,String userBankID,String money){
		Map<String, String> params = this.newParams();
		params.put("userID", userID);
		params.put("userBankID", userBankID);
		params.put("money", money);
		if(addUserBankCardResponse==null){
			addUserBankCardResponse=new AddUserBankCardResponse(this);
		}
		DVolley.get(add_user_cart_URL, params,addUserBankCardResponse);
	}
	
	
	
	private class FindUserCartListResponse extends DResponseService{
		public FindUserCartListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}  
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			List<TBankAccount> list=new ArrayList<TBankAccount>();
			JSONArray couponArray=callResult.getContentArray();
			if(couponArray!=null&&couponArray.length()!=0){
				for(int i=0;i<couponArray.length();i++){
					JSONObject bankAccountObject=couponArray.getJSONObject(i);
					String id=JSONUtil.getString(bankAccountObject,"id");
					String bankID=JSONUtil.getString(bankAccountObject,"bank_id");
					String bankSN=JSONUtil.getString(bankAccountObject,"bank_sn");
					String bankName=JSONUtil.getString(bankAccountObject,"name");
					String time=JSONUtil.getString(bankAccountObject,"time");
					String name=JSONUtil.getString(bankAccountObject,"user_name");
					String mobile=JSONUtil.getString(bankAccountObject,"user_phone");
					
					TBankAccount bankAccount=new TBankAccount();
					bankAccount.setId(id);
					bankAccount.setBankID(bankID);
					bankAccount.setBankName(bankName);
					bankAccount.setBankSN(bankSN);
					bankAccount.setAddTime(VolleyUtil.formatSecond(time));
					bankAccount.setName(name);
					bankAccount.setMobile(mobile);
					list.add(bankAccount);
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class FindBankListResponse extends DResponseService{
		public FindBankListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}  
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			List<TBank> list=new ArrayList<TBank>();
			JSONArray couponArray=callResult.getContentArray();
			if(couponArray!=null&&couponArray.length()!=0){
				for(int i=0;i<couponArray.length();i++){
					JSONObject bankAccountObject=couponArray.getJSONObject(i);
					String bankID=JSONUtil.getString(bankAccountObject,"id");
					String bankName=JSONUtil.getString(bankAccountObject,"name");
					
					TBank bank=new TBank();
					bank.setBankID(bankID);
					bank.setBankName(bankName);
					list.add(bank);
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class AddBankCardResponse extends DResponseService{
		public AddBankCardResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}  
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_ADD);
			returnBean.setObject(null);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class AddUserBankCardResponse extends DResponseService{
		public AddUserBankCardResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}  
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_ADD);
			returnBean.setObject(null);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
