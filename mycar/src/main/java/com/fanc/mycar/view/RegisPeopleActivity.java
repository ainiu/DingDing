package com.fanc.mycar.view;
/**注册*/
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fanc.mycar.R;
import com.fanc.mycar.code.DResponseListener;
import com.fanc.mycar.code.DVolley;
import com.fanc.mycar.code.DVolleyConstans;
import com.fanc.mycar.code.ReturnBean;
import com.fanc.mycar.code.model.Register;
import com.fanc.mycar.code.model.Verification;
import com.fanc.mycar.util.LogUtil;


public class RegisPeopleActivity extends Activity implements DResponseListener {
	
	private EditText  userName,userPassed,caPTCHA;
	private Verification verification;
	private Register register;
	private Button yanZheng, wanCheng;
	private TimeCount time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regis_people);
		initModel();
		setView();
		initLinener();
	}

	private void initModel() {
		time=new TimeCount(60000,1000);
		verification=new Verification(this);
		verification.addResponseListener(this);

		register=new Register(this);
		register.addResponseListener(this);
	}

	private void initLinener() {
		//验证
		yanZheng.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setSignable();
			}
		});
		//注册
		wanCheng.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				initData();
			}
		});
	}
	private String verifition_str;
	private void initData() {
		verifition_str=caPTCHA.getText().toString();
		if(verifition_str.isEmpty()||token.isEmpty()){
			Toast.makeText(this,"请输入验证码",Toast.LENGTH_SHORT).show();
			return;
		}
		register.findConsultList(user_name, user_pwd, verifition_str, token);
	}

	/**判断是否满足注册要求*/
	private String user_name,user_pwd;

	private void setSignable() {
		user_name=userName.getText().toString();
		user_pwd=userPassed.getText().toString();
		if(user_name.isEmpty()||user_pwd.isEmpty()){
			Toast.makeText(RegisPeopleActivity.this,"手机或密码不能为空",Toast.LENGTH_SHORT).show();
			return;
		}
		/*if(user_name.length()>10||user_name.length()<7){
			Toast.makeText(RegisPeopleActivity.this,"手机号码不正确",Toast.LENGTH_SHORT).show();
			return;
		}*/
		if(user_pwd.length()<5||user_pwd.length()>11){
			Toast.makeText(RegisPeopleActivity.this,"密码长度6-12",Toast.LENGTH_SHORT).show();
			return;
		}

	    verification.findConsultList(user_name);
		time.start();
	}

	private void setView() {
		userName=(EditText)findViewById(R.id.zhuCeName);
		userPassed=(EditText)findViewById(R.id.zhuCePassed);
		caPTCHA=(EditText)findViewById(R.id.zheCeYan);
		wanCheng=(Button)findViewById(R.id.button11);
		yanZheng=(Button)findViewById(R.id.button12);
	}
   
	public void doClick(View v){
		this.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		DVolley.cancelAll();
	}
	String token;
	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
		//LogUtil.i("界面回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);

		//错误处理
		if (error != null) {
			Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
			return;
		}
		//成功处理
		if (result == DVolleyConstans.RESULT_OK) {
			if (bean.getType() == DVolleyConstans.METHOD_VERIFICATION) {
				 token = (String) bean.getObject();
			}
		}



	}
          /**验证码*/
	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			yanZheng.setBackgroundColor(Color.parseColor("#B6B6D8"));
			yanZheng.setClickable(false);
			yanZheng.setText(millisUntilFinished / 1000 + "秒后可重新发送");
		}

		@Override
		public void onFinish() {
			yanZheng.setText("重新获取验证码");
			yanZheng.setClickable(true);
			yanZheng.setBackgroundColor(Color.parseColor("#4EB84A"));
		}
	}

}
