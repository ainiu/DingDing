package com.baidu.dingding;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.LoginModel;
import com.baidu.dingding.entity.User;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.MD5Util;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.MyView.dialog.MyProgressDialog;
import com.baidu.dingding.view.ProgrressDialog.ProgressDialog;
import com.baidu.dingding.view.RegisActivity;
import com.baidu.dingding.view.ZhaoHuiActivity;


public class MainActivity extends BaseActivity implements DResponseListener {

	private EditText eduserName, eduserPassword;
	private Button login, regist, forgetPassword;
	private String edName, edPassword;
	private ImageView imageView;
	private ProgressDialog progressDialog;
	private LoginModel loginModel;
	private Dialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		setInitialize();
		setLiseten();

	}

	private void initView() {
		progressDialog=new ProgressDialog(this);
		loginModel=new LoginModel(this);
		loginModel.addResponseListener(this);
		dialog = MyProgressDialog.createLoadingDialog(this, "官人稍等...");
	}

	private void setLiseten() {
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				edName = eduserName.getText().toString().toLowerCase();
				edPassword = eduserPassword.getText().toString().trim();

				if (edName.isEmpty()) {
					Toast.makeText(MainActivity.this, "帐号不能为空",
							Toast.LENGTH_LONG).show();
				}
				if (edPassword.isEmpty()) {
					Toast.makeText(MainActivity.this, "密码不能为空",
							Toast.LENGTH_LONG).show();
				}
				loginModel.findConsultList(edName, MD5Util.encodeString(edPassword));
				//progressDialog.show();
				dialog.show();

	}
});
	}

	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
		//progressDialog.dismiss();
		dialog.dismiss();
		if(error!=null){
			Toasttool.MyToast(this,error.getMessage());
			return;
		}
		if (result==DVolleyConstans.RESULT_FAIL){
			Toasttool.MyToast(this,message);
			return;
		}
		if(result== DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_USER_LOGIN){
				//正常登录成功
				SharedPreferencesUtils.clear(this);
				User user = (User) bean.getObject();
				SharedPreferencesUtils.put(this,"userNumber",user.getUsrNumber());
				LogUtil.i("user", SharedPreferencesUtils.get(this, "userNumber", ""));
				SharedPreferencesUtils.put(this, "token", user.getToken());
				LogUtil.i("user", SharedPreferencesUtils.get(this, "token", ""));
			//	Intent intent=new Intent();
				//Bundle bundle=new Bundle();
			//	bundle.putSerializable("login_user", user);
			//	intent.putExtras(bundle);
				//setResult(Activity.RESULT_OK, intent);
				this.finish();
			}
		}

	}




	private void setInitialize() {
		eduserName = (EditText) findViewById(R.id.editText_01);
		eduserPassword = (EditText) findViewById(R.id.editText_02);
		login = (Button) findViewById(R.id.main_button_01);
		regist = (Button) findViewById(R.id.main_button_02);
		forgetPassword = (Button) findViewById(R.id.main_button_03);
		imageView = (ImageView) findViewById(R.id.image_01);
		eduserName.setText(SharedPreferencesUtils.get(this,"userNumber","").toString());

	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.main_button_02:                                                    //注册
			Intent intent01 = new Intent(this, RegisActivity.class);
			startActivity(intent01);
			break;
		case R.id.main_button_03:                                                     //找回
			Intent intent03 = new Intent(this, ZhaoHuiActivity.class);
			startActivity(intent03);
			break;
		case R.id.image_01:
			finish();
		}

	}


}