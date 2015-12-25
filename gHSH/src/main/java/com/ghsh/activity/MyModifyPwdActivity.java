package com.ghsh.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.UserModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.R;
/**
 * 修改密码
 * */
public class MyModifyPwdActivity extends BaseActivity implements OnClickListener,DResponseListener {
	private TextView titleView;
	private Button modifyButton;
	private EditText oldPwdView, newPwdView1, newPwdView2;
	private DProgressDialog progressDialog;
	private UserModel userModel=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mymodify_pwd);
		this.initView();
		this.initListener();
		userModel=new UserModel(this);
		userModel.addResponseListener(this);
	}

	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.mymodify_pwd_title);
		modifyButton=(Button)this.findViewById(R.id.header_button_view);
		modifyButton.setText(R.string.modify);
		modifyButton.setVisibility(View.VISIBLE);
		oldPwdView = (EditText) findViewById(R.id.modify_old_pwd);
		newPwdView1 = (EditText) findViewById(R.id.modify_new_pwd1);
		newPwdView2 = (EditText) findViewById(R.id.modify_new_pwd2);
	}

	private void initListener() {
		modifyButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v==modifyButton){
			String oldPwd=oldPwdView.getText().toString().trim();
			String newPwd1=newPwdView1.getText().toString().trim();
			String newPwd2=newPwdView2.getText().toString().trim();
			if(oldPwd.equals("")||oldPwd.length()<6||oldPwd.length()>25){
				this.showShortToast(R.string.mymodify_pwd_tip_old_pwd_not_null);
				return;
			}
			if(newPwd1.equals("")||newPwd1.length()<6||newPwd1.length()>25){
				this.showShortToast(R.string.mymodify_pwd_tip_new_pwd2_not_null);
				return;
			}
			if(newPwd2.equals("")||newPwd2.length()<6||newPwd2.length()>25){
				this.showShortToast(R.string.mymodify_pwd_tip_new_pwd2_not_null);
				return;
			}
			if(!newPwd2.equals(newPwd2)){
				this.showShortToast(R.string.mymodify_pwd_tip_new_pwd);
				return;
			}
			progressDialog.show();
			userModel.modifyPwd(this.getUserID(), oldPwd, newPwd1);
		}
	}

	
	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		progressDialog.dismiss();
		if (error != null) {
			this.showShortToast(error.getMessage());
			return;
		}
		if (result == DVolleyConstans.RESULT_OK) {
			if(bean.getType()==DVolleyConstans.METHOD_USER_MODIFY_PWD){
				finish();
			}
		}
		this.showShortToast(message);
	}
}
