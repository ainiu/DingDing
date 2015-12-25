package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TPayment;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
/**
 * 获取支付方式
 * */
public class PaymentModel extends DVolleyModel {
	private final String find_payment_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=payment&m=findAll");
	private DResponseService findPaymentListResponse=new FindPaymentListResponse(this);
	
	public PaymentModel(Context context) {
		super(context);
	}
	/**支付方式列表*/
	public void findPaymentList(){
		Map<String, String> params = this.newParams();
		DVolley.get(find_payment_list_URL, params,findPaymentListResponse);
	}
	
	private class FindPaymentListResponse extends DResponseService{
		public FindPaymentListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			List<TPayment> list=new ArrayList<TPayment>();
			JSONArray paymentArray=callResult.getContentArray();
			if(paymentArray!=null&&paymentArray.length()!=0){
				for(int i=0;i<paymentArray.length();i++){
					JSONObject paymentObject=paymentArray.getJSONObject(i);
					String paymentID=JSONUtil.getString(paymentObject,"pay_id");
					String paymentCode=JSONUtil.getString(paymentObject,"pay_code");
					String paymentName=JSONUtil.getString(paymentObject,"pay_name");
					String paymentDesc=JSONUtil.getString(paymentObject,"pay_desc");
					
					TPayment payment=new TPayment();
					payment.setPaymentID(paymentID);
					payment.setPaymentCode(paymentCode);
					payment.setPaymentName(paymentName);
					payment.setPaymentDesc(paymentDesc);
					list.add(payment);
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
