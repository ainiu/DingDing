package com.ghsh.share.qq;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ghsh.Constants;
import com.ghsh.code.exception.AppViewException;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.view.DToastView;
import com.ghsh.R;
import com.tencent.connect.auth.QQToken;
import com.tencent.open.t.Weibo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.weibo.sdk.android.api.WeiboAPI;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;

/**
 * 商品属性选择框
 * */
public class QQShareWeiboDialog extends Dialog implements android.view.View.OnClickListener{
	private Activity activity;
	private ImageView imageView;
	private TextView titleView;
	private EditText commentView;
	private Button sendButton,cancelButton;
	private DProgressDialog progressDialog;
	private WeiboAPI weiboAPI;// 腾讯微博API
	private Weibo mWeibo;//QQ腾讯微博API
	private String shareImageUrl,shareLocalImagePath;
	public QQShareWeiboDialog(Activity activity) {
		super(activity, R.style.dialog_style_comment);
		this.activity = activity;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_qq_share_weibo);
		Window window = this.getWindow();
		window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		WindowManager m = activity.getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
		p.width=d.getWidth();
		window.setAttributes(p);
		window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
		this.initView();
		this.initListener();
	}
	private void initView() {
		progressDialog=new DProgressDialog(activity);
		imageView=(ImageView)this.findViewById(R.id.dialog_qq_share_weibo_image_view);
		titleView=(TextView)this.findViewById(R.id.dialog_qq_share_weibo_title_view);
		commentView=(EditText)this.findViewById(R.id.dialog_qq_share_weibo_comment_view);
		sendButton=(Button)this.findViewById(R.id.dialog_qq_share_weibo_send_button);
		cancelButton=(Button)this.findViewById(R.id.dialog_qq_share_weibo_cancel_button);
	}
	private void initListener() {
		sendButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
	}
	
	public void showQQWeibo(String shareTitle,String shareLocalImagePath,Bitmap shareImageBitmap,QQToken qqToken) {
		super.show();
		this.shareLocalImagePath=shareLocalImagePath;
		titleView.setText(shareTitle);
		commentView.setText(shareTitle);
		if(shareImageBitmap!=null){
			imageView.setImageBitmap(shareImageBitmap);
		}
		mWeibo = new Weibo(activity, qqToken);
	}
	public void showWeibo(String shareTitle,String shareImageUrl,Bitmap shareImageBitmap,String accessToken) {
		super.show();
		this.shareImageUrl=shareImageUrl;
		titleView.setText(shareTitle);
		commentView.setText(shareTitle);
		if(shareImageBitmap!=null){
			imageView.setImageBitmap(shareImageBitmap);
		}
		AccountModel account = new AccountModel(accessToken);
		weiboAPI = new WeiboAPI(account);
	}
	
	@Override
	public void onClick(View v) {
		if(v==sendButton){
			//发送
			progressDialog.show();
			String shareComment = commentView.getText().toString().trim();
			if(shareComment.equals("")){
				shareComment=titleView.getText().toString().trim();
			}
			if(weiboAPI!=null){
				weiboAPI.reAddWeibo(activity, shareComment, shareImageUrl, null,null, null, null, new SendWeiboListener(), null,BaseVO.TYPE_JSON);
			}
			if(mWeibo!=null){
				mWeibo.sendPicText(shareComment,shareLocalImagePath, new SendQQWeiboListener());
			}
		}else if(v==cancelButton){
			//取消
			this.cancel();
		}
	}
	
	class SendQQWeiboListener implements IUiListener{
		@Override
		public void onCancel() {
			String message=activity.getResources().getString(R.string.share_cancel);
			DToastView.makeText(activity,message, Toast.LENGTH_SHORT).show();
			QQShareWeiboDialog.this.dismiss();
			activity.finish();
		}
		@Override
		public void onComplete(Object object) {
			try {
				JSONObject jsonObject=(JSONObject)object;
				String ret = JSONUtil.getString(jsonObject, "ret");
				String msg=JSONUtil.getString(jsonObject, "msg");
				if(ret!=null&&ret.equals("0")){
					//分享成功
					String message=activity.getResources().getString(R.string.share_success);
					DToastView.makeText(activity,message,Toast.LENGTH_SHORT).show();
				}else{
					//分享失败
					String message=activity.getResources().getString(R.string.share_fail)+"ret:"+ret+" msg:"+msg;
					DToastView.makeText(activity,message, Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				AppViewException.onViewException(e);
			}
			QQShareWeiboDialog.this.dismiss();
			activity.finish();
		}
		@Override
		public void onError(UiError error) {
			String message=activity.getResources().getString(R.string.share_fail)+error.errorMessage;
			DToastView.makeText(activity,message, Toast.LENGTH_SHORT).show();
			QQShareWeiboDialog.this.dismiss();
			activity.finish();
		}
	}
	class SendWeiboListener implements HttpCallback{
		@Override
		public void onResult(Object object) {
			progressDialog.dismiss();
			ModelResult result = (ModelResult) object;
			if(Constants.DEBUG){
				Log.i("QQShareWeiboDialog 腾讯微博发送结果：", "isExpires:"+result.isExpires()+" isSuccess:"+result.isSuccess()+" getError_message:"+result.getError_message());
			}
			if (result.isExpires()) {
				String message=activity.getResources().getString(R.string.share_fail)+result.getError_message();
				DToastView.makeText(activity,message, Toast.LENGTH_SHORT).show();
			} else {
				if (result.isSuccess()) {
					String message=activity.getResources().getString(R.string.share_success);
					DToastView.makeText(activity,message,Toast.LENGTH_SHORT).show();
				} else {
					String message=activity.getResources().getString(R.string.share_fail)+result.getError_message();
					DToastView.makeText(activity,message, Toast.LENGTH_SHORT).show();
				}
			}
			QQShareWeiboDialog.this.dismiss();
			activity.finish();
		}
	}
}
