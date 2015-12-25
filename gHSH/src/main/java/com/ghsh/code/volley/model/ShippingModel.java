package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TShipping;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
/**
 * 配送方式
 * */
public class ShippingModel extends DVolleyModel {
	private final String find_order_shipping_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=shipping&m=findAll");
	private DResponseService findOrderShippingResponse;
	
	public ShippingModel(Context context) {
		super(context);
	}
	/**获取配送方式*/
	public void findOrderShippingList(String addressID){
		Map<String, String> params = this.newParams();
		params.put("addressID", addressID);
		if(findOrderShippingResponse==null){
			findOrderShippingResponse=new FindOrderShippingResponse(this);
		}
		DVolley.get(find_order_shipping_list_URL, params,findOrderShippingResponse);
	}

	
	private class FindOrderShippingResponse extends DResponseService{
		public FindOrderShippingResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			List<TShipping> list=new ArrayList<TShipping>();
			JSONArray shippingArray=callResult.getContentArray();
			if(shippingArray!=null&&shippingArray.length()!=0){
				for(int i=0;i<shippingArray.length();i++){
					JSONObject shippingObject=shippingArray.getJSONObject(i);
					String shippingID=JSONUtil.getString(shippingObject,"shipping_id");
					String shippingName=JSONUtil.getString(shippingObject,"shipping_name");
					
					TShipping shipping=new TShipping();
					shipping.setShippingID(shippingID);
					shipping.setShippingName(shippingName);
					list.add(shipping);
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
