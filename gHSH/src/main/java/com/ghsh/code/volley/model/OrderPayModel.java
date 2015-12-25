package com.ghsh.code.volley.model;

import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TPayBean;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;

public class OrderPayModel extends DVolleyModel {
	
	private final String pay_order_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=order&m=pay");
	
	private DResponseService payOrderResponse;
	public OrderPayModel(Context context) {
		super(context);
	}
	/**支付订单*/
	public void payOrder(String orderID,String paymentID){
		if(paymentID==null){
			paymentID="";
		}
		Map<String, String> params = this.newParams();  
		params.put("paymentID",paymentID);
		params.put("orderID",orderID);
		if(payOrderResponse==null){
			payOrderResponse=new PayOrderResponse(this);
		}
		DVolley.get(pay_order_URL, params,payOrderResponse);
	}
	
	private class PayOrderResponse extends DResponseService{
		public PayOrderResponse(DVolleyModel volleyModel) {
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
			returnBean.setType(DVolleyConstans.METHOD_GET);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
