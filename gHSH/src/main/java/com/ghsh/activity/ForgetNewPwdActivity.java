package com.ghsh.activity;

import android.content.Intent;
import android.os.Bundle;
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
 * 修改新密码
 * */
public class ForgetNewPwdActivity extends BaseActivity implements OnClickListener,DResponseListener{
	private TextView titleView;
	private Button okButton;
	private EditText passwordView,password2View;
	private ForgetpwdModel forgetpwdModel;
	private DProgressDialog progressDialog;
	private String verify, mobile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgetpwd_new);
		this.initView();
		this.initLitenter();
		forgetpwdModel=new ForgetpwdModel(this);
		forgetpwdModel.addResponseListener(this);
		Intent intent=getIntent();
		verify=intent.getStringExtra("verify");
		mobile=intent.getStringExtra("mobile");
	}

	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText("设置新密码");
		
		passwordView= (EditText) this.findViewById(R.id.forgetpwd_password1);
		password2View= (EditText) this.findViewById(R.id.forgetpwd_password2);
		okButton=(Button) this.findViewById(R.id.forgetpwd_okButton);
	}
	private void initLitenter(){
		okButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if(v==okButton){
			//验证码验证
			String password1=passwordView.getText().toString().trim();
			String password2=password2View.getText().toString().trim();
			if(password1.equals("")){
				this.showShortToast("密码不能为空，请重新输入！");
				return ;
			}
			if(password2.equals("")){
				this.showShortToast("邮确认密码不能为空，请重新输入！");
				return ;
			}
			if(!password1.equals(password2)){
				this.showShortToast("密码和确认密码不一致，请重新输入！");
				return ;
			}
			progressDialog.show();
			forgetpwdModel.forgetpwdSetNewPwd( mobile, verify, password1);
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
			if(bean.getType()==DVolleyConstans.METHOD_USER_FORGETPWD_NEW){
				//修改密码返回
				this.showShortToast(message);
				this.finish();
				return;
			}
		}
		this.showShortToast(message);
	}
}
