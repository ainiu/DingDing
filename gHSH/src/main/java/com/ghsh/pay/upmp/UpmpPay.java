package com.ghsh.pay.upmp;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.ghsh.view.DToastView;
import com.unionpay.UPPayAssistEx;
/**银联支付*/
public class UpmpPay{
	
	private Activity activity;
	
	/**mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境*/
    private final String mMode = "00";
    
	public UpmpPay(Activity activity){
		this.activity=activity;
	}
	 
	public void pay(String orderSn){
		 UPPayAssistEx.startPayByJAR(activity, com.unionpay.uppay.PayActivity.class, null, null, orderSn, mMode);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
		if(requestCode==10){
	        String msg = "";
	        /*
	         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
	         */
	        String str = data.getExtras().getString("pay_result");
	        if (str.equalsIgnoreCase("success")) {
	            msg = "支付成功！";
	        } else if (str.equalsIgnoreCase("fail")) {
	            msg = "支付失败！";
	        } else if (str.equalsIgnoreCase("cancel")) {
	            msg = "用户取消了支付";
	        }
	        DToastView.makeText(activity,msg,Toast.LENGTH_SHORT).show();
	        activity.finish();
		}
    }
	
}
