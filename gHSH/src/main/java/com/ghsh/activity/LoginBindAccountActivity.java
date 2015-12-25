package com.ghsh.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.bean.TShareLoginBean;
import com.ghsh.code.bean.TUsers;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.RegisterOtherModel;
import com.ghsh.code.volley.model.ShareModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.listaner.NumberCharKeyListener;
import com.ghsh.util.MenberUtils;
/**绑定用户*/
public class LoginBindAccountActivity extends BaseActivity implements OnClickListener,DResponseListener {
	private TextView titleView;
	private ImageView headView;//头像
	private TextView nickNameView,bindaccountRegView;//昵称
	private Button bindAccountButton,loginButton;
	private EditText userNameView,passwordView;
	private ShareModel shareModel;
	private RegisterOtherModel registerOtherModel;
	private DProgressDialog progressDialog;
	private String shareOpenID,shareType,shareNickName,sharePortrait;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_bindaccount);
		this.initView();
		this.initListener();
		this.initData();
		shareModel=new ShareModel(this);
		shareModel.addResponseListener(this);
		registerOtherModel=new RegisterOtherModel(this);
		registerOtherModel.addResponseListener(this);
	}
	
	private void initView(){
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.bindaccount_title);
		
		headView=(ImageView)this.findViewById(R.id.bindaccount_image_view);
		nickNameView=(TextView)this.findViewById(R.id.bindaccount_nickName_view);
		bindAccountButton=(Button)this.findViewById(R.id.bind_button);
		loginButton=(Button)this.findViewById(R.id.login_button);
		userNameView=(EditText)this.findViewById(R.id.bindaccount_userName);
		passwordView=(EditText)this.findViewById(R.id.bindaccount_password);
		
		bindaccountRegView=(TextView)this.findViewById(R.id.bindaccount_reg);
		bindaccountRegView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	}

	private void initListener() {
		bindAccountButton.setOnClickListener(this);
		loginButton.setOnClickListener(this);
		bindaccountRegView.setOnClickListener(this);
		// 设置只能输入0-9，a-z，A-Z
		userNameView.setKeyListener(new NumberCharKeyListener());
		passwordView.setKeyListener(new NumberCharKeyListener());
		// 设置输入最大长度
		userNameView.setFilters(new InputFilter[] { new InputFilter.LengthFilter(30) });
		passwordView.setFilters(new InputFilter[] { new InputFilter.LengthFilter(30) });
	}
	
	private void initData(){
		Intent intent=this.getIntent();
		shareOpenID=intent.getStringExtra("shareOpenID");
		shareType=intent.getStringExtra("shareType");
		shareNickName=intent.getStringExtra("shareNickName");
		sharePortrait=intent.getStringExtra("sharePortrait");
		nickNameView.setText(shareNickName);
		DVolley.getImage(sharePortrait,headView,R.drawable.personal_no_active_user_icon,true);
	}

	@Override
	public void onClick(View v) {
		if(v==bindAccountButton){
			//绑定用户
			String userName=userNameView.getText().toString().trim();
			String password=passwordView.getText().toString().trim();
			if("".equals(userName)) {
				this.showShortToast(R.string.login_tip_mobile_empty);
				return;
			} 
			if("".equals(password)) {				
				this.showShortToast(R.string.login_tip_password_empty);
				return;
			}
			if(password.length()<6) {				
				this.showShortToast(R.string.login_tip_password_length);
				return;
			}
			progressDialog.show();
			shareModel.bindAccount(shareOpenID,shareType,shareNickName,sharePortrait, userName, password);
		}else if(v==loginButton){
			//立即登录
			registerOtherModel.registerOtherMenber(shareOpenID, shareType,shareNickName,sharePortrait);
		}else if(v==bindaccountRegView){
			//跳转注册
			Intent intent=new Intent(this,RegisterMobileActivity.class);
			this.startActivity(intent);
			this.finish();
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
			if(bean.getType()==DVolleyConstans.METHOD_USER_LOGIN_BINDACCOUNT){
				//绑定成功-登陆
				MenberUtils.login(this, (TUsers)bean.getObject());
				this.finish();
				return;
			}else if(bean.getType()==DVolleyConstans.METHOD_USER_REGISTER){
				//立即登录
				MenberUtils.login(this, (TUsers)bean.getObject());
				this.setResult(Activity.RESULT_OK);
				this.finish();
				return;
			}
		}
		this.showShortToast(message);
	}
	
}
