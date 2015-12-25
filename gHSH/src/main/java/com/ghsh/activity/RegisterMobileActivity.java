package com.ghsh.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.bean.TUsers;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.RegisterMobileModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.listaner.NumberCharKeyListener;
import com.ghsh.util.MenberUtils;
import com.ghsh.util.RegexpUtil;
/**
 * 注册页面--手机验证码注册
 * */
public class RegisterMobileActivity extends BaseActivity implements OnClickListener ,DResponseListener {
	private TextView titleView;
	private Button registerButton,verifyButton;
	private EditText mobileView,verifyView,passwordView1,passwordView2;
	private TextView loginView;
	private RegisterMobileModel registerModel;
	private DProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_mobile);
		this.initView();
		this.initListener();
		registerModel=new RegisterMobileModel(this);
		registerModel.addResponseListener(this);
	}

	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.register_title);
		
		mobileView= (EditText) findViewById(R.id.register_mobile);
		verifyView= (EditText) findViewById(R.id.register_verify);
		passwordView1= (EditText) findViewById(R.id.register_password1);
		passwordView2= (EditText) findViewById(R.id.register_password2);
		
		registerButton = (Button) this.findViewById(R.id.register_button);
		verifyButton= (Button) this.findViewById(R.id.register_verify_button);
		loginView= (TextView) this.findViewById(R.id.register_login);
		loginView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	}
	
	private void initListener() {
		registerButton.setOnClickListener(this);
		verifyButton.setOnClickListener(this);
		loginView.setOnClickListener(this);
		
		// 设置只能输入0-9，a-z，A-Z
		mobileView.setKeyListener(new NumberCharKeyListener());
		passwordView1.setKeyListener(new NumberCharKeyListener());
		passwordView2.setKeyListener(new NumberCharKeyListener());
		// 设置输入最大长度
		mobileView.setFilters(new InputFilter[] { new InputFilter.LengthFilter(30) });
		passwordView1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
		passwordView2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
	}

	@Override
	public void onClick(View v) {
		if(v==loginView){
			//返回登录
			Intent intent=new Intent(this,LoginActivity.class);
			this.startActivity(intent);
			this.finish();
		}else if(v==verifyButton){
			//获取验证码
			String mobile=mobileView.getText().toString().trim().toLowerCase();
			if(mobile.equals("")){
				this.showShortToast(R.string.register_tip_mobile_empty);
				return;
			}
			if(!RegexpUtil.checkMobNumber(mobile)){
				this.showShortToast(R.string.register_tip_mobile_format);
				return;
			}
			this.startTime();
			registerModel.sendVerify(mobile);
		}else if(v==registerButton){
			//注册
			String mobile=mobileView.getText().toString().trim().toLowerCase();
			String password1=passwordView1.getText().toString().trim();
			String password2=passwordView2.getText().toString().trim();
			String verify=verifyView.getText().toString().trim();
			if(mobile.equals("")){
				this.showShortToast(R.string.register_tip_mobile_empty);
				return;
			}
			if(!RegexpUtil.checkMobNumber(mobile)){
				this.showShortToast(R.string.register_tip_mobile_format);
				return;
			}
			if(verify.equals("")){
				this.showShortToast(R.string.register_tip_mobile_verify);
				return;
			}
			if(password1.equals("")){
				this.showShortToast(R.string.register_tip_password_empty);
				return;
			}
			if(password1.length()<6){
				this.showShortToast(R.string.register_tip_password_length);
				return;
			}
			if(password2.equals("")){
				this.showShortToast(R.string.register_tip_password2_empty);
				return;
			}
			if(password2.length()<6){
				this.showShortToast(R.string.register_tip_password2_length);
				return;
			}
			if(!password1.equals(password2)){
				this.showShortToast(R.string.register_tip_password_not_equal);
				return;
			}
			if(RegexpUtil.checkNumber(password1)){
				this.showShortToast(R.string.register_tip_password_not_sing);
				return;
			}
			if(RegexpUtil.checkLetter(password1)){
				this.showShortToast(R.string.register_tip_password_not_sing);
				return;
			}
			progressDialog.show();
			registerModel.registerMenber(mobile,password1,verify);
		}
	}
	
	@Override
	public void onMessageResponse(ReturnBean bean,int result,String message,Throwable error) {
		progressDialog.dismiss();
		if(error!=null){
			showShortToast(error.getLocalizedMessage());
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_USER_REGISTER){
				//注册返回
				showShortToast(message);
				MenberUtils.login(this, (TUsers)bean.getObject());
				this.setResult(Activity.RESULT_OK);
				this.finish();
		        return;
			}else if(bean.getType()==DVolleyConstans.METHOD_USER_REGISTER_VERIFY){
				//发送验证码返回
				showShortToast(message);
		        return;
			}
		}
		showShortToast(message);
	}
	
	Handler handler;
	private void startTime(){
		final String text=this.getResources().getString(R.string.register_button_verify);
		verifyButton.setEnabled(false);
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				int time=msg.what;
				time--;
				if(time>=1){
					verifyButton.setText(text+"("+time+")");
					Message message=new Message();
					message.what=time;
					handler.sendMessageDelayed(message, 1000);
				}
				if(time<=0){
					verifyButton.setText(text);
					verifyButton.setEnabled(true);
				}
			}
		};
		Thread thread=new Thread(){
			public void run() {
				handler.sendEmptyMessage(60);
			};
		};
		thread.start();
	}
}
