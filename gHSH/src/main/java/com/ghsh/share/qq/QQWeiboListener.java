package com.ghsh.share.qq;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.view.DToastView;
import com.ghsh.R;
import com.tencent.open.t.Weibo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class QQWeiboListener implements IUiListener{
	private Activity activity;
	private Tencent mTencent;
	private Weibo mWeibo;
	private String shareText,shareLocalImagePath;
	public QQWeiboListener(Activity activity,Tencent mTencent,String shareText,String shareLocalImagePath){
		this.activity=activity;
		this.mTencent= mTencent;
		this.shareText=shareText;
		this.shareLocalImagePath=shareLocalImagePath;
	}
	@Override
	public void onComplete(Object object) {
		//授权成功
		mWeibo = new Weibo(activity, mTencent.getQQToken());
		mWeibo.sendPicText(shareText,shareLocalImagePath, new ShareIUiListener());
	}
	@Override
	public void onCancel() {
		//授权取消
		String message=activity.getResources().getString(R.string.share_auth_cancel);
	}
	
	@Override
	public void onError(UiError error) {
		String message=activity.getResources().getString(R.string.share_auth_fail)+": " + error.errorMessage;
	}
	/**分享*/
	class ShareIUiListener implements IUiListener{
		@Override
		public void onCancel() {
			
		}
		@Override
		public void onComplete(Object object) {
			//分享
			try {
				JSONObject jsonObject=(JSONObject)object;
				String ret = JSONUtil.getString(jsonObject, "ret");
				String msg=JSONUtil.getString(jsonObject, "msg");
				if(ret!=null&&ret.equals("0")){
					//分享成功
					
				}else{
					//分享失败
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void onError(UiError arg0) {
			
		}
	}
}
