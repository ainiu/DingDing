package com.ghsh.pay.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.ghsh.view.DToastView;
/**支付宝支付*/
public class AlipayUtil {
//	public static final String PARTNER = "2088411660193638";
//	public static final String SELLER = "services@iasmall.com";
//	public static final String KEY = "l7pcmgv3x2b1h9qvtzrhgtony2gg223j";
//	public static final String RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALqrlMEqxGw84ITRSeB2tfOUjZUrRoDIYeIMmYG+7iY9ToYJYlfJ9ozG6kLLCShbM+FTI5ienGacjTmdVZz51jhfcYHnU39Til3IidxvINZkdCsQWSubT9diBVvn13U0nPgNFMNbmHbyE40/Gc66xbRLTopGGyaysedqE70GR31ZAgMBAAECgYAKXLf3OR6KO57jAJMvSKe1C8wIOczCHV2BpN1onGav7LtNKXwyD14GVoxuhMLPYLCyk2DoefWIpKwaRKeZ/SxtAD5ladK/w7xL/R40G99VC/ykuOSPnYgWQ8PW6G3dNXp1CnPM4FP09UO23nxmMxVC8xmxwHocKwM0u18GRMBQGQJBAPYnFyg8Li2kU4+KvBVmWTnkCKjeReMcoRBBSkvpCbqPAGvAMw1PW7IycpbHDfDfCRqzL5AMFcGdk0tnvIcQlUMCQQDCI1D56FcGXoepAmrvgvlYCvKWHUWN//+VT+nnB8ckgpn572vYkjLytVNcPHfsa2GPIvbqtD7iNJNFHDHFQqszAkAw5Q7qhRm/Izcd+jbmIVBz+WHm+U/0jwlHS1DQx1eRYTp6LNF3QV47hV3RqXSRdhw+230FJgFkVkKd5dtDEP41AkADKSYJW5IHJYTZ6JMrIRvEJjF65jEatb9IhAuP2l2Qp5uwKQi9duvjbbZUuxtMuxbUiMIyYgrgDfET3/ijeIlnAkAXR4yX+17+bP7HVbO+uPZqvqyXjQWN4ap8eQR74wOgMXoDwGq8OyJpj7d7qd0OjCJi0m0PTrKJIYwsiU09V5Fm";
	private Activity activity;
	private Listener listener;
	
	public AlipayUtil(Activity activity,Listener listener){
		this.activity=activity;
		this.listener=listener;
	}
	
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 0:
				DToastView.makeText(activity, "支付失败",Toast.LENGTH_SHORT).show();
				break;
			case 1:
				DToastView.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				DToastView.makeText(activity, "支付结果确认中",Toast.LENGTH_SHORT).show();
				break;
			case 3:
				DToastView.makeText(activity, "支付取消",Toast.LENGTH_SHORT).show();
				break;
			}
			listener.onPayResult(msg.what, null);
		}
	};
	public void pay(final String orderInfo,String RSA_private){
		String sign=null;
		try {
			sign = this.getSign(orderInfo, RSA_private);
		} catch (Exception e) {
			DToastView.makeText(activity, "支付失败:"+e.getMessage(),Toast.LENGTH_SHORT).show();
			listener.onPayResult(0, e);
			return;
		}
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"+ this.getSignType();
		Log.i("支付宝支付>>orderInfo", payInfo);
		Thread payRunnable = new Thread() {
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(activity);
				// 调用支付接口
				String result = alipay.pay(payInfo);
				Result resultObj = new Result(result);
				String resultStatus = resultObj.resultStatus;
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				Message message=new Message();
				if(TextUtils.equals(resultStatus, "9000")){
					//支付成功
					message.what=1;
				}else if(TextUtils.equals(resultStatus, "8000")){
					//判断resultStatus 为非“9000”则代表可能支付失败
					// “8000” 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					message.what=2;
				}else if(TextUtils.equals(resultStatus, "6001")){
					//支付取消
					message.what=3;
				}else{
					message.what=0;
				}
				handler.sendMessage(message);
				Log.i("支付宝支付>>状态", resultStatus);
			}
		};
		payRunnable.start();
	}
	
	/**对订单信息进行签名
	 * @throws UnsupportedEncodingException */
	private String getSign(String orderInfo,String RSA_private) throws Exception{
		String sign =SignUtils.sign(orderInfo, RSA_private);
		if(sign==null||sign.equals("")){
			throw new Exception("签名验证错误");
		}
		sign = URLEncoder.encode(sign, "UTF-8");
		return sign;
	}
	/**获取签名方式*/
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}
	
	public interface Listener{
		public void onPayResult(int status,Exception error);
	}
}
