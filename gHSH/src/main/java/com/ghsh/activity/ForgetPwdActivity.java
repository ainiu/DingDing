package com.ghsh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.ForgetpwdModel;
import com.ghsh.dialog.DProgressDialog;

/**
 * 忘记密码
 * */
public class ForgetPwdActivity extends BaseActivity implements OnClickListener,DResponseListener{
	private TextView titleView;
	private Button okButton,sendButton;
	private EditText mobileView,verifyView;
	private ForgetpwdModel forgetpwdModel;
	private DProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgetpwd);
		this.initView();
		this.initLitenter();
		forgetpwdModel=new ForgetpwdModel(this);
		forgetpwdModel.addResponseListener(this);
	}

	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.forgetPwd_title);
		
		mobileView= (EditText) this.findViewById(R.id.forgetpwd_mobile);
		verifyView= (EditText) this.findViewById(R.id.forgetpwd_verify);
		sendButton=(Button) this.findViewById(R.id.forgetpwd_sendButton);
		okButton=(Button) this.findViewById(R.id.forgetpwd_okButton);
	}
	private void initLitenter(){
		sendButton.setOnClickListener(this);
		okButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		String mobile=mobileView.getText().toString().trim();
		
		if(mobile.equals("")){
			this.showShortToast("手机号码不能为空，请重新输入！");
			return ;
		}
		
		if(v==sendButton){
			//发送验证码
			forgetpwdModel.forgetpwdVerifySend(mobile);
			this.time();
		}else if(v==okButton){
			//验证码验证
			String verify=verifyView.getText().toString().trim();
			if(verify.equals("")){
				this.showShortToast("验证码不能为空，请重新输入！");
				return ;
			}
			progressDialog.show();
			forgetpwdModel.forgetpwdVerify(mobile,verify);
		}
	}

	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		progressDialog.dismiss();
		if(error!=null){
			this.showShortToast(error.getMessage());
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_USER_FORGETPWD_VERIFY_SEND){
				//发送验证码返回
				this.showShortToast(message);
				return;
			}else if(bean.getType()==DVolleyConstans.METHOD_USER_FORGETPWD_VERIFY){
				//验证码 验证返回
				String mobile=mobileView.getText().toString().trim();
				String verify=verifyView.getText().toString().trim();
				Intent intent=new Intent(this,ForgetNewPwdActivity.class);
				intent.putExtra("verify", verify);
				intent.putExtra("mobile", mobile);
				this.startActivity(intent);
				this.finish();
				return;
			}
		}
		timeFlag=false;
		this.showShortToast(message);
	}
	
	private void time(){
		timeFlag=true;
		sendButton.setEnabled(false);
		Thread thread=new Thread(){
			@Override
			public void run() {
				Message msgs=new Message();
				msgs.what=60;
				handler.sendMessage(msgs);
			}
		};
		thread.start();
	}
	
	private boolean timeFlag=true;
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(!timeFlag){
				sendButton.setText("发送验证码");
				sendButton.setEnabled(true);
				return;
			}
			int time=msg.what-1;
			if(time>0){
				Message message=new Message();
				message.what=time;
				sendButton.setText("发送验证码("+time+")");
				handler.sendMessageDelayed(message, 1000);
			}else{
				sendButton.setText("发送验证码");
				sendButton.setEnabled(true);
			}
		}
	};
}
