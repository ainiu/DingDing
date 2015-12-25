package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.ShareConstants;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.adapter.LoginShareGridViewtAdapter;
import com.ghsh.code.bean.TShare;
import com.ghsh.code.bean.TShareLoginBean;
import com.ghsh.code.bean.TUsers;
import com.ghsh.code.exception.AppViewException;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.LoginModel;
import com.ghsh.code.volley.model.ShareModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.listaner.NumberCharKeyListener;
import com.ghsh.share.qq.QQLoginListener;
import com.ghsh.share.qq.QqShare;
import com.ghsh.share.sina.SinaWeiboLoginListener;
import com.ghsh.share.sina.SinaWeiboShare;
import com.ghsh.share.weixin.WeiXinShare;
import com.ghsh.share.weixin.WeixinLoginListener;
import com.ghsh.util.MenberUtils;
/**
 * 登录页面
 * */
public class LoginActivity extends BaseActivity implements OnClickListener,DResponseListener,OnItemClickListener {
	private TextView titleView;
	private TextView registerView,forgetPwdView;//注册,忘记密码
	private Button loginButton;//登录
	private EditText mobileView,passwordView;
	private GridView shareGridView;
	private View shareTitlelayout;
	private LoginShareGridViewtAdapter adapter=new LoginShareGridViewtAdapter(this,new ArrayList<TShare>());;
	private LoginModel loginModel;
	private ShareModel shareModel;
	private DProgressDialog progressDialog;
	private SinaWeiboShare sinaWeiboShare;
	private QqShare qqShare;
	private WeiXinShare weiXinShare;
	private Bundle savedInstanceState;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		this.savedInstanceState=savedInstanceState;
		this.initView();
		this.initListener();
		loginModel=new LoginModel(this);
		loginModel.addResponseListener(this);
		
		shareModel=new ShareModel(this);
		shareModel.addResponseListener(new ShareResponse());
	}

	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.login_title);
		
		registerView = (TextView)this. findViewById(R.id.login_register);
		registerView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		forgetPwdView= (TextView)this. findViewById(R.id.login_forget_pwd);
		forgetPwdView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		mobileView= (EditText) this.findViewById(R.id.login_mobile);
		passwordView= (EditText) this.findViewById(R.id.login_password);
		loginButton = (Button) this.findViewById(R.id.login_button);
		
		shareTitlelayout= this.findViewById(R.id.login_share_login_title_layout);
		shareGridView=(GridView) this.findViewById(R.id.login_share_gridView);
		shareGridView.setAdapter(adapter);
		List<TShare> shareList=ShareConstants.getLoginList();
		if(shareList!=null&&shareList.size()!=0){
			shareTitlelayout.setVisibility(View.VISIBLE);
			adapter.setList(shareList);
			adapter.notifyDataSetChanged();
		}
	}
	
	private void initListener() {
		loginButton.setOnClickListener(this);
		registerView.setOnClickListener(this);
		forgetPwdView.setOnClickListener(this);
		shareGridView.setOnItemClickListener(this);
		//设置只能输入0-9，a-z，A-Z
//		userNameView.setKeyListener(new NumberCharKeyListener());
		passwordView.setKeyListener(new NumberCharKeyListener());
		//设置输入最大长度
		mobileView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
		passwordView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
	}
	
	@Override
	public void onClick(View v) {
		if(v==registerView){
			//跳转注册页面
			Intent intent=new Intent(this,RegisterMobileActivity.class);
			this.startActivity(intent);
			this.finish();
		}else if(v==loginButton){
			//正常登录
			String mobile = mobileView.getText().toString().trim().toLowerCase();
			String password = passwordView.getText().toString().trim();
			if("".equals(mobile)) {
				this.showShortToast(R.string.login_tip_mobile_empty);
				return;
			}
			if("".equals(password)) {				
				this.showShortToast(R.string.login_tip_password_empty);
				return;
			}
			if(password.length()<3) {				
				this.showShortToast(R.string.login_tip_password_length);
				return;
			}
			progressDialog.show();
			loginModel.login(mobile, password);
		}else if(v==forgetPwdView){
			//忘记密码
			Intent intent=new Intent(this,ForgetPwdActivity.class);
			this.startActivity(intent);
		}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		try {
			TShare share=adapter.getItem(position);
			TShare.ShareBean shareBean=share.getShareBean();
			if(shareBean==null){
				this.showShortToast("分享失败，参数不能为空");
				return;
			}
			TShare.ShareConfig shareConfig=shareBean.shareConfig;
			if(shareConfig==null){
				this.showShortToast("分享失败，参数不能为空");
				return;
			}
			if(share.getId().equals("1")){
				//微信登录
				if(weiXinShare==null){
					weiXinShare=new WeiXinShare(this,shareConfig.APP_ID);
				}
				progressDialog.show();
				weiXinShare.login(new WeixinLoginListener(this,this,shareConfig.APP_ID,shareConfig.APP_Secret));
			}if(share.getId().equals("2")){
				//QQ登录
				if(qqShare==null){
					qqShare=new QqShare(this,shareConfig.APP_ID);
				}
				qqShare.login(new QQLoginListener(this,this,qqShare.getTencent()));
			}else if(share.getId().equals("3")){
				//新浪微博登录
				if(sinaWeiboShare==null){
					sinaWeiboShare=new SinaWeiboShare(this,shareConfig.APP_KEY);
					sinaWeiboShare.init(savedInstanceState);
				}
				sinaWeiboShare.authLogin(new SinaWeiboLoginListener(this,this));
			}else if(share.getId().equals("4")){
				//支付宝登录
				
			}
		} catch (Throwable e) {
			AppViewException.onViewException(e);
		}
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if(sinaWeiboShare!=null){
			sinaWeiboShare.handleWeiboResponse(intent);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==Activity.RESULT_OK){
			if(requestCode==1){
				this.finish();
			}
		}
		if(qqShare!=null&&requestCode==ShareConstants.REQUEST_CODE_QQ){
			qqShare.onActivityResult(requestCode, resultCode, data);
		}
		if(sinaWeiboShare!=null&&requestCode==ShareConstants.REQUEST_CODE_SINA_WEIBO){
			sinaWeiboShare.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		progressDialog.dismiss();
		if(error!=null){
			showShortToast(error.getMessage());
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_USER_LOGIN){
				//正常登录成功
				MenberUtils.login(this, (TUsers)bean.getObject());
				this.finish();
				return;
			}else if(bean.getType()==DVolleyConstans.METHOD_USER_LOGIN_AUTH){
				//授权成功--开始登陆
				TShareLoginBean shareLoginBean=(TShareLoginBean)bean.getObject();
				progressDialog.show();
				shareModel.shareLogin(shareLoginBean.getOpenID(),shareLoginBean.getType(),shareLoginBean.getNickName(),shareLoginBean.getUserPortrait());
				return;
			}
		}
		showShortToast(message);


	}
	class ShareResponse implements DResponseListener{
		@Override
		public void onMessageResponse(ReturnBean bean, int result,String message, Throwable error) {
			progressDialog.dismiss();
			if(error!=null){
				LoginActivity.this.showShortToast(error.getMessage());
				return;
			}
			if(result==DVolleyConstans.RESULT_OK){
				if(bean.getType()==DVolleyConstans.METHOD_USER_LOGIN_SHARE){
					//第三方登录
					TUsers users=(TUsers)bean.getObject();
					if(users!=null&&users.getUserID()!=null&&!users.getUserID().equals("")){
						MenberUtils.login(LoginActivity.this, users);
						LoginActivity.this.finish();
					}else{
						//登录成功，未绑定帐号
						Intent intent=new Intent(LoginActivity.this,LoginBindAccountActivity.class);
						intent.putExtra("shareOpenID", users.getShareOpenID());
						intent.putExtra("shareType", users.getShareType());
						intent.putExtra("shareNickName", users.getShareNickName());
						intent.putExtra("sharePortrait", users.getSharePortrait());
						LoginActivity.this.startActivity(intent);
						LoginActivity.this.finish();
					}
					return;
				}
			}
			LoginActivity.this.showShortToast(message);
		}
	}
}
