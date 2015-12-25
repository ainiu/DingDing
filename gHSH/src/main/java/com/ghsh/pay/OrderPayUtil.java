package com.ghsh.pay;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.ghsh.Constants;
import com.ghsh.R;
import com.ghsh.activity.PayWebActivity;
import com.ghsh.code.bean.TPayBean;
import com.ghsh.code.exception.AppViewException;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.OrderPayModel;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.pay.alipay.AlipayUtil;
import com.ghsh.pay.upmp.UpmpPay;
import com.ghsh.pay.weixin.WeixinUtil;
import com.ghsh.view.DToastView;
import com.ghsh.view.util.DAlertUtil;

public class OrderPayUtil implements DResponseListener{
	
	private OrderPayModel orderPayModel;
	private Activity activity;
	private Dialog progressDialog;
	private WeixinUtil weixinUtil;//微信支付操作类
	private UpmpPay upmpPay;//银联支付操作类
	public OrderPayUtil(Activity activity,Dialog progressDialog){
		this.activity=activity;
		this.progressDialog=progressDialog;
		orderPayModel=new OrderPayModel(activity);
		orderPayModel.addResponseListener(this);
	}
	
	//订单支付
	public void payOrder(final String orderID,final String paymentID,android.content.DialogInterface.OnClickListener cancelListener){
		if(orderID==null||orderID.equals("")){
			DToastView.makeText(activity, "支付错误，订单号为空", Toast.LENGTH_SHORT).show();
			return;
		}
		DAlertUtil.alertOKAndCel(activity,R.string.orderdetails_alter_message_order, new android.content.DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				progressDialog.show();
				orderPayModel.payOrder(orderID,paymentID);
			} 
		},cancelListener).show();
	}
	//充值付款
	public void payRecharge(ReturnBean bean, int result,String message, Throwable error){
		progressDialog.show();
		this.onMessageResponse(bean, result, message, error);
	}
	
	@Override
	public void onMessageResponse(ReturnBean bean, int result,String message, Throwable error) {
		progressDialog.dismiss();
		if(error!=null){
			DToastView.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT);
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_GET){
				TPayBean payBean=(TPayBean)bean.getObject();
				if(payBean==null||payBean.getPaymentCode()==null||payBean.getPaymentCode().equals("")){
					DToastView.makeText(activity,message, Toast.LENGTH_SHORT).show();
					activity.finish();
				}else if(payBean.getPaymentCode().equals(Constants.ORDER_PAYMENT_alipayfree)){
					//支付宝--免签支付
					Intent intent=new Intent(activity,PayWebActivity.class);
					intent.putExtra("html", payBean.getDesc());
					activity.startActivity(intent);
				}else if(payBean.getPaymentCode().equals(Constants.ORDER_PAYMENT_cod)){
					//货到付款
					DToastView.makeText(activity,message, Toast.LENGTH_SHORT).show();
					activity.finish();
					return;
				}else if(payBean.getPaymentCode().equals(Constants.ORDER_PAYMENT_alipay_mobile)){
					//支付宝快捷支付
					this.alipaymobile(payBean.getDesc());
					return;
				}else if(payBean.getPaymentCode().equals(Constants.ORDER_PAYMENT_balance)){
					//余额支付
					DToastView.makeText(activity,message, Toast.LENGTH_SHORT).show();
					activity.finish();
					return;
				}else if(payBean.getPaymentCode().equals(Constants.ORDER_PAYMENT_weizhifuapp)){
					//微信APP支付
					this.weizhifuapp(payBean.getDesc());
					return;
				}else if(payBean.getPaymentCode().equals(Constants.ORDER_PAYMENT_upmp_mobile)){
					//银联APP支付
					this.upmpapp(payBean.getDesc());
					return;
				}else{
					DToastView.makeText(activity,"支付失败:PaymentCode="+payBean.getPaymentCode(), Toast.LENGTH_SHORT);
					activity.finish();
					return;
				}
			}
		}
		DToastView.makeText(activity, message, Toast.LENGTH_SHORT).show();
		activity.finish();
	}
	//支付回调
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(upmpPay!=null){
			upmpPay.onActivityResult(requestCode, resultCode, data);
		}
	}
	/**支付宝快捷支付*/
	private void alipaymobile(String payInfo){
		try {
			JSONObject jsonObject=new JSONObject(payInfo);
			String partner=JSONUtil.getString(jsonObject, "partner");
			String sellerID=JSONUtil.getString(jsonObject, "sellerID");
			String alipayKey=JSONUtil.getString(jsonObject, "alipayKey");
			String alipayPartnerKey=JSONUtil.getString(jsonObject, "alipayPartnerKey");
			String orderInfo=JSONUtil.getString(jsonObject, "orderInfo");
			AlipayUtil AlipayUtil=new AlipayUtil(activity,new AlipayUtil.Listener() {
				@Override
				public void onPayResult(int status, Exception error) {
					activity.finish();
				}
			});
			AlipayUtil.pay(orderInfo,alipayPartnerKey);
		} catch (JSONException e) {
			AppViewException.onViewException(e);
		}
	}
	/**微信APP支付*/
	private void weizhifuapp(String content){
		try {
			JSONObject jsonObject=new JSONObject(content);
			String appID=JSONUtil.getString(jsonObject, "appid");
			String mchID=JSONUtil.getString(jsonObject, "partnerid");
			String prepayID=JSONUtil.getString(jsonObject, "prepayid");
			String packageValue=JSONUtil.getString(jsonObject, "package");
			String nonceStr=JSONUtil.getString(jsonObject, "noncestr");
			String timeStamp=JSONUtil.getString(jsonObject, "timestamp");
			String sign=JSONUtil.getString(jsonObject, "sign");
			if(weixinUtil==null){
				weixinUtil=new WeixinUtil(activity);
			}
			weixinUtil.pay(appID,mchID,prepayID,packageValue,nonceStr,timeStamp,sign);
		} catch (JSONException e) {
			AppViewException.onViewException(e);
		}
	}
	
	/**银联APP支付*/
	private void upmpapp(String content){
		try {
			JSONObject jsonObject=new JSONObject(content);
			String orderId=JSONUtil.getString(jsonObject, "orderID");
			if(upmpPay==null){
				upmpPay=new UpmpPay(activity);
			}
			upmpPay.pay(orderId);
		} catch (JSONException e) {
			AppViewException.onViewException(e);
		}
	}
}
