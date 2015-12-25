package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TMyBonus;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
/**
 *  我的红包
 * */
public class MyBonusModel extends DVolleyModel {
	
	private final String find_bonus_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=bonus&m=findBonusList");
	private final String find_bonus_order_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=bonus&m=findOrderBonusList");
	private DResponseService findBonusListResponse;
	private DResponseService findBonusOrderListResponse;
	public MyBonusModel(Context context) {
		super(context);
	}
	
	/**获取列表*/ 
	public void findBonusList(String userID,int bonusStatus,int currentPage){
		Map<String, String> params = this.newParams();
		params.put("bonusStatus", bonusStatus+"");
		params.put("currentPage", currentPage+"");
		params.put("userID",userID);
		if(findBonusListResponse==null){
			findBonusListResponse=new FindBonusListResponse(this);
		}
		DVolley.get(find_bonus_list_URL, params,findBonusListResponse);
	}
	
	/**获取订单红包列表*/ 
	public void findOrderBonusList(String userID,String account){
		Map<String, String> params = this.newParams();
		params.put("account", account);
		params.put("userID",userID);
		if(findBonusOrderListResponse==null){
			findBonusOrderListResponse=new FindBonusOrderListResponse(this);
		}
		DVolley.get(find_bonus_order_list_URL, params,findBonusOrderListResponse);
	}
	
	private class FindBonusListResponse extends DResponseService{
		public FindBonusListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			List<TMyBonus> list=new ArrayList<TMyBonus>();
			JSONArray contentArray=callResult.getContentArray();
			if(contentArray!=null&&contentArray.length()!=0){
				for(int i=0;i<contentArray.length();i++){
					JSONObject contentObject=contentArray.getJSONObject(i);
					String bonusID=JSONUtil.getString(contentObject,"bonus_id");
					String typeName=JSONUtil.getString(contentObject,"type_name");
					String typeMoney=JSONUtil.getString(contentObject,"type_money");
					String minGoodsAmount=JSONUtil.getString(contentObject,"min_goods_amount");
					String time=JSONUtil.getString(contentObject,"time");
					String status=JSONUtil.getString(contentObject,"status");
					
					TMyBonus bonus=new TMyBonus();
					bonus.setBonusID(bonusID);
					bonus.setTypeName(typeName);
					bonus.setTypeMoney(typeMoney);
					bonus.setMinGoodsAmount(minGoodsAmount);
					bonus.setTime(time);
					bonus.setStatus(status);
					list.add(bonus);
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class FindBonusOrderListResponse extends DResponseService{
		public FindBonusOrderListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			List<TMyBonus> list=new ArrayList<TMyBonus>();
			JSONArray contentArray=callResult.getContentArray();
			if(contentArray!=null&&contentArray.length()!=0){
				for(int i=0;i<contentArray.length();i++){
					JSONObject contentObject=contentArray.getJSONObject(i);
					String bonusID=JSONUtil.getString(contentObject,"bonus_id");
					String typeName=JSONUtil.getString(contentObject,"type_name");
					String typeMoney=JSONUtil.getString(contentObject,"type_money");
					String minGoodsAmount=JSONUtil.getString(contentObject,"min_goods_amount");
					
					TMyBonus bonus=new TMyBonus();
					bonus.setBonusID(bonusID);
					bonus.setTypeName(typeName);
					bonus.setTypeMoney(typeMoney);
					bonus.setMinGoodsAmount(minGoodsAmount);
					list.add(bonus);
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
}
