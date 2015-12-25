package com.ghsh.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ghsh.ShareConstants;
import com.ghsh.code.bean.TShare;
import com.ghsh.share.weixin.manager.WeixinCallBackManager;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
/**
 * 测试使用,不用删除
 * */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
	// IWXAPI 是第三方app和微信通信的openapi接口
		private IWXAPI api;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        TShare.ShareBean shareBean=ShareConstants.getQQWechatShareBean();
	        if(shareBean!=null){
	        	 api = WXAPIFactory.createWXAPI(this, shareBean.shareConfig.APP_ID, false);
	             api.handleIntent(getIntent(), this);
	        }
	       
	    }
	    
	    @Override
		protected void onNewIntent(Intent intent) {
			super.onNewIntent(intent);
			setIntent(intent);
			if(api!=null){
				 api.handleIntent(intent, this);
			}
		}


		// 微信发送请求到第三方应用时，会回调到该方法
		@Override
		public void onReq(BaseReq req) {
			switch (req.getType()) {
			case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
				break;
			case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
				break;
			default:
				break;
			}
		}

		// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
		@Override
		public void onResp(BaseResp resp) {
			WeixinCallBackManager.onRespNotifyAll(resp);
//			int result = 0;
//			switch (resp.errCode) {
//			case BaseResp.ErrCode.ERR_OK:
//				//发送成功
//				break;
//			case BaseResp.ErrCode.ERR_USER_CANCEL:
//				//发送取消
//				break;
//			case BaseResp.ErrCode.ERR_AUTH_DENIED:
//				//发送被拒绝
//				break;
//			default:
//				//发送返回
//				break;
//			}
//			Toast.makeText(this, "哈哈哈", Toast.LENGTH_LONG).show();
			this.finish();
		}
}