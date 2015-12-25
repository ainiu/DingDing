package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TCoupon;
import com.ghsh.code.bean.TMyCoupon;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.R;
/**
 *  我的优惠劵
 * */
public class MyCouponModel extends DVolleyModel {
	
	private final String find_coupon_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=coupon&m=findCouponList");
	private final String receive_coupon_URL=DVolleyConstans.getServiceHost("phoneapi/index.php?c=coupon&m=receiveCoupon");
	private DResponseService findCouponListResponse;
	private DResponseService receiveCouponResponse;
	public MyCouponModel(Context context) {
		super(context);
	}
	
	/**获取优惠劵列表*/ 
	public void findCouponList(String userID,int couponType,int currentPage){
		Map<String, String> params = this.newParams();
		params.put("couponType", couponType+"");
		params.put("currentPage", currentPage+"");
		params.put("userID",userID);
		if(findCouponListResponse==null){
			findCouponListResponse=new FindCouponListResponse(this);
		}
		DVolley.get(find_coupon_list_URL, params,findCouponListResponse);
	}
	
	/**领取优惠劵*/ 
	public void receiveCoupon(String userID,String couponID){
		Map<String, String> params = this.newParams();
		params.put("userID", userID);
		params.put("couponID",couponID);
		if(receiveCouponResponse==null){
			receiveCouponResponse=new ReceiveCouponResponse(this);
		}
		DVolley.get(receive_coupon_URL, params,receiveCouponResponse);
	}
	
	private class FindCouponListResponse extends DResponseService{
		public FindCouponListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			List<TMyCoupon> list=new ArrayList<TMyCoupon>();
			JSONArray couponArray=callResult.getContentArray();
			if(couponArray!=null&&couponArray.length()!=0){
				for(int i=0;i<couponArray.length();i++){
					JSONObject couponObject=couponArray.getJSONObject(i);
					String couponID=JSONUtil.getString(couponObject,"couponID");
					String couponValue=JSONUtil.getString(couponObject,"couponValue");
					String time=JSONUtil.getString(couponObject,"time");
					String minAmount=JSONUtil.getString(couponObject,"minAmount");
					String sendType=JSONUtil.getString(couponObject,"sendType");
					String status=JSONUtil.getString(couponObject,"status");
					
					TMyCoupon myCoupon=new TMyCoupon();
					myCoupon.setCouponID(couponID);
					myCoupon.setCouponValue(couponValue);
					myCoupon.setTime(time);
					myCoupon.setMinAmount(minAmount);
					myCoupon.setSendType(sendType);
					myCoupon.setStatus(status);
					list.add(myCoupon);
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class ReceiveCouponResponse extends DResponseService{
		public ReceiveCouponResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_ADD);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
