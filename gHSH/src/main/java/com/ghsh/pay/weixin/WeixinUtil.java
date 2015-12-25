package com.ghsh.pay.weixin;

import android.app.Activity;
import android.widget.Toast;

import com.ghsh.share.weixin.manager.WeixinCallBackManager;
import com.ghsh.view.DToastView;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
/**微信支付*/
public class WeixinUtil implements WeixinCallBackManager.Listener{
	
	private Activity activity;
	private IWXAPI api; 
	public WeixinUtil(Activity activity){
		this.activity=activity;
	}
	public void pay(String appId,String partnerId,String prepayId,String packageValue,String nonceStr,String timeStamp,String sign){
		WeixinCallBackManager.addCallBackListener(this);
		api = WXAPIFactory.createWXAPI(activity,appId);
		api.registerApp(appId);
		PayReq request = new PayReq();
		request.appId =appId;
		request.partnerId = partnerId;//商户ID
		request.prepayId= prepayId;
		request.packageValue = packageValue;
		request.nonceStr= nonceStr;
		request.timeStamp= timeStamp;
		request.sign=sign;
		api.sendReq(request);
	}
	@Override
	public void onWeixincallBack(BaseResp resp) {
		if(resp.getType()==ConstantsAPI.COMMAND_PAY_BY_WX){
			//如果是支付
			switch (resp.errCode) {
				case BaseResp.ErrCode.ERR_OK:
					//支付成功
					DToastView.makeText(activity, "支付成功",Toast.LENGTH_SHORT).show();
					break;
				case BaseResp.ErrCode.ERR_USER_CANCEL:
					//用户取消
					DToastView.makeText(activity, "支付取消",Toast.LENGTH_SHORT).show();
					break;
				case BaseResp.ErrCode.ERR_COMM:
					//错误
					//可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
					DToastView.makeText(activity, "支付失败",Toast.LENGTH_SHORT).show();
					break;
				default:
					DToastView.makeText(activity, "支付失败",Toast.LENGTH_SHORT).show();
					break;
			}
			activity.finish();
			return;
		}
		DToastView.makeText(activity, "支付失败，不是支付操作",Toast.LENGTH_SHORT).show();
		activity.finish();
	}
}
