package com.ghsh.share.qq;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.ghsh.view.DToastView;
import com.ghsh.R;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class QqShare implements IUiListener{

	private Activity activity;
	private Tencent mTencent;
	private String SCOPE="get_user_info,get_simple_userinfo,add_topic,add_pic_t,add_t";
	public QqShare(Activity activity,String APP_ID) {
		this.activity = activity;
		mTencent = Tencent.createInstance(APP_ID, activity);
	}
	
	/** QQ登录 */
	public void login(IUiListener listener) {
		if(!mTencent.isSupportSSOLogin(activity)){
			DToastView.makeText(activity, "请先安装QQ，在执行登录", Toast.LENGTH_SHORT);
			return;
		}
		if(mTencent.isSessionValid()){
			mTencent.logout(activity);
		}
		mTencent.login(activity, SCOPE, listener);
	}
	
	public Tencent getTencent(){
		return mTencent;
	}
	/**分享到QQ*/
	public void shareQQ(String message,String shareUrl,String imageUrl) {
		Bundle params = new Bundle();
		//分享类型
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		// 手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
//		params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
		// 分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_ SUMMARY不能全为空，最少必须有一个是有值的。
		params.putString(QQShare.SHARE_TO_QQ_TITLE, message);
		// 分享的消息摘要，最长50个字
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY, message);
		// 这条分享消息被好友点击后的跳转URL
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,shareUrl);
		// 分享的图片URL
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,imageUrl);
		params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 1);
		mTencent.shareToQQ(activity, params, this);
	}
	/**分享到QQ空间*/
	public void shareQzone(String message,String shareUrl,String imageUrl) {
		Bundle params = new Bundle();
		params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
		params.putString(QzoneShare.SHARE_TO_QQ_TITLE, message);// 必填
		params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, message);// 选填
		params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareUrl);// 必填
		ArrayList<String> imageList=new ArrayList<String>();
		imageList.add(imageUrl);
		params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,imageList);
		mTencent.shareToQzone(activity, params,this);
	}
	/**分享到腾讯微博*/
	public void shareQQWeiBo(final String shareTitle,final String shareLocalImagePath,final Bitmap shareImageBitmap){
		if(!mTencent.isSupportSSOLogin(activity)){
			DToastView.makeText(activity, "请先安装QQ，在执行登录", Toast.LENGTH_SHORT);
			return;
		}
		if(mTencent.isSessionValid()){
			//直接分享
			QQShareWeiboDialog qqShareWeiboDialog=new QQShareWeiboDialog(activity);
			qqShareWeiboDialog.showQQWeibo(shareTitle, shareLocalImagePath, shareImageBitmap,mTencent.getQQToken());
		}else{
			//授权分享
			mTencent.login(activity, SCOPE, new IUiListener(){
				@Override
				public void onCancel() {
					DToastView.makeText(activity, R.string.share_auth_cancel, Toast.LENGTH_SHORT).show();
				}
				@Override
				public void onComplete(Object arg0) {
					QQShareWeiboDialog qqShareWeiboDialog=new QQShareWeiboDialog(activity);
					qqShareWeiboDialog.showQQWeibo(shareTitle, shareLocalImagePath, shareImageBitmap,mTencent.getQQToken());
				}
				@Override
				public void onError(UiError error) {
					String message=activity.getResources().getString(R.string.share_auth_fail)+": " + error.errorMessage;
					DToastView.makeText(activity,message, Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(mTencent!=null){
			mTencent.onActivityResult(requestCode, resultCode, data);
		}
	}
	@Override
	public void onCancel() {
		Toast.makeText(activity, R.string.share_cancel, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onComplete(Object arg0) {
		Toast.makeText(activity, R.string.share_success, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onError(UiError arg0) {
		Toast.makeText(activity, R.string.share_fail, Toast.LENGTH_LONG).show();
	}
}
