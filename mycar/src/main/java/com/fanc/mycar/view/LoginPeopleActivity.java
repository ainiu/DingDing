package com.fanc.mycar.view;

/**登录*/
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fanc.mycar.R;

public class LoginPeopleActivity extends Activity implements View.OnClickListener{

	private EditText name,passed;

	private Button loginButton,regisButton,xiuButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_people);
		setView();
		initLinstener();
	}

	private void initLinstener() {
		regisButton.setOnClickListener(this);
		xiuButton.setOnClickListener(this);
		loginButton.setOnClickListener(this);
	}

	private void setView() {
		name=(EditText)findViewById(R.id.userName);
		passed=(EditText)findViewById(R.id.userPassed);
		loginButton=(Button)findViewById(R.id.button1);
		regisButton=(Button)findViewById(R.id.button2);
		xiuButton=(Button)findViewById(R.id.button3);
	}
	public void doClick(View v){
		this.finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.button2:
				Intent intent1=new Intent().setClass(this,RegisPeopleActivity.class);                //注册
				this.startActivity(intent1);
			break;

		}
	}
}
