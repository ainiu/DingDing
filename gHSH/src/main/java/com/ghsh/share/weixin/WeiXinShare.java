package com.ghsh.share.weixin;

import java.util.Date;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.ghsh.code.exception.AppViewException;
import com.ghsh.share.weixin.manager.WeixinCallBackManager;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class WeiXinShare  {
	private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
	private IWXAPI api;
	private Activity activity;
	private String APP_ID;
	
	public WeiXinShare(Activity activity,String APP_ID) {
		this.activity = activity;
		this.APP_ID=APP_ID;
		api = WXAPIFactory.createWXAPI(activity,APP_ID);
		api.registerApp(APP_ID);
	}

	public void login(WeixinCallBackManager.Listener listener) {
		SendAuth.Req req = new SendAuth.Req();
		req.scope = "snsapi_userinfo";
		req.state = System.currentTimeMillis()+"";
		api.sendReq(req);
		WeixinCallBackManager.addCallBackListener(listener);
	}
	/**分享到微信朋友圈*/
	public void sharePengyouquan(String message,Bitmap shareImage,String shareUrl){
		if (!api.isWXAppInstalled()) {
			Toast.makeText(activity, "请先安装微信客户端",Toast.LENGTH_LONG).show();
			return;
		}
		try {
			int wxSdkVersion = api.getWXAppSupportAPI();
			if (wxSdkVersion >= TIMELINE_SUPPORTED_VERSION) {
				WXWebpageObject webpage = new WXWebpageObject();
				webpage.webpageUrl = shareUrl;
				WXMediaMessage msg = new WXMediaMessage(webpage);
				msg.description = message;
				if (null != shareImage) {
					Bitmap thumbBmp = Bitmap.createScaledBitmap(shareImage,150, 150, true);
					msg.thumbData = WeiXinUtil.bmpToByteArray(thumbBmp, true);
				}
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = String.valueOf(System.currentTimeMillis());
				req.message = msg;
				req.scene = SendMessageToWX.Req.WXSceneTimeline;
				msg.title = message;
				if (api.isWXAppInstalled()) {
					api.sendReq(req);
				} else {
					Toast.makeText(activity, "未安装微信客户端",Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(activity, "当前版本不支持发送到微信朋友圈", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			AppViewException.onViewException(e);
		}
	}
	
	/**分享到微信好友*/
	public void shareHaoyou(String message,Bitmap shareImage,String shareUrl){
		if (!api.isWXAppInstalled()) {
			Toast.makeText(activity, "请先安装微信客户端",Toast.LENGTH_LONG).show();
			return;
		}
		try{
			WXWebpageObject webpage = new WXWebpageObject();
			webpage.webpageUrl = shareUrl;
			WXMediaMessage msg = new WXMediaMessage(webpage);
			msg.description = message;
			if (null != shareImage) {
				Bitmap thumbBmp = Bitmap.createScaledBitmap(shareImage,150, 150, true);
				msg.thumbData = WeiXinUtil.bmpToByteArray(thumbBmp, true);
			}
			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = String.valueOf(System.currentTimeMillis());
			req.message = msg;
			req.scene = SendMessageToWX.Req.WXSceneSession;
			msg.title = message;
			if (api.isWXAppInstalled()) {
				api.sendReq(req);
			} else {
				Toast.makeText(activity, "未安装微信客户端",Toast.LENGTH_LONG).show();
			}
		} catch(Exception e) {
			AppViewException.onViewException(e);
		}
	}
}
