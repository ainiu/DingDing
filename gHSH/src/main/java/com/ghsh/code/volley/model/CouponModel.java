package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TCoupon;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.R;
/**
 * 优惠劵
 * */
public class CouponModel extends DVolleyModel {
	private final String find_order_coupon_list_URL=DVolleyConstans.getServiceHost("phoneapi/index.php?c=coupon&m=findOrderCoupon");
	
	private DResponseService findOrderCouponListResponse;
	
	public CouponModel(Context context) {
		super(context);
	}
	/**获取订单可用优惠劵*/ 
	public void findOrderCouponList(String userID,String money){
		Map<String, String> params = this.newParams();
		params.put("userID", userID);
		params.put("money", money);
		if(findOrderCouponListResponse==null){
			findOrderCouponListResponse=new FindOrderCouponListResponse(this);
		}
		DVolley.get(find_order_coupon_list_URL, params,findOrderCouponListResponse);
	}
	
	private class FindOrderCouponListResponse extends DResponseService{
		public FindOrderCouponListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			List<TCoupon> list=new ArrayList<TCoupon>();
			TCoupon defaultCon=new TCoupon();
			defaultCon.setCouponID("");
			defaultCon.setName(mContext.getResources().getString(R.string.order_coupon_tip_no));
			defaultCon.setPrice("");
			list.add(defaultCon);
			JSONArray couponArray=callResult.getContentArray();
			if(couponArray!=null&&couponArray.length()!=0){
				for(int i=0;i<couponArray.length();i++){
					JSONObject couponObject=couponArray.getJSONObject(i);
					String couponID=JSONUtil.getString(couponObject,"couponID");
					String name=JSONUtil.getString(couponObject,"name");
					String price=JSONUtil.getString(couponObject,"price");
					
					TCoupon coupon=new TCoupon();
					coupon.setCouponID(couponID);
					coupon.setName(name);
					coupon.setPrice(price);
					list.add(coupon);
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
