package com.ghsh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.bean.TUsers;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.UserModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.util.MenberUtils;
/**
 * 我的资料
 * */
public class MyInformationActivity extends BaseActivity implements OnClickListener,DResponseListener {
	private TextView titleView;
	private Button headButton,modifyPwdButton;//头像，修改密码
	private ImageView headImageView;
	private UserModel userModel;
	private DProgressDialog progressDialog;
	private TextView usernameView,realNameView,mobileView,birthdayView,emailView,regtimeView,lasttimeView;
	private Button realNameButton,birthdayButton,mobileButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!this.checkLogin()){
			return;
		}
		setContentView(R.layout.activity_myinformation);
		this.initView();
		this.initListener();
		userModel=new UserModel(this);
		userModel.addResponseListener(this);
		progressDialog.show();
		userModel.getUserInfo(MenberUtils.getUserID(this));
	}

	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.myInformation_title);
		headButton= (Button) this.findViewById(R.id.myinformation_head_button);
		
		usernameView=(TextView) this.findViewById(R.id.myinformation_username);
		realNameView=(TextView) this.findViewById(R.id.myinformation_realName);
		mobileView=(TextView) this.findViewById(R.id.myinformation_mobile);
		birthdayView=(TextView) this.findViewById(R.id.myinformation_birthday);
		emailView=(TextView) this.findViewById(R.id.myinformation_email);
		regtimeView=(TextView) this.findViewById(R.id.myinformation_regtime);
		lasttimeView=(TextView) this.findViewById(R.id.myinformation_lasttime);
		headImageView= (ImageView) this.findViewById(R.id.myinformation_head_image);
		modifyPwdButton=(Button) this.findViewById(R.id.myInformation_modify_pwd);
		
		realNameButton=(Button) this.findViewById(R.id.myinformation_realName_button);
		birthdayButton=(Button) this.findViewById(R.id.myinformation_birthday_button);
		mobileButton=(Button) this.findViewById(R.id.myinformation_mobile_button);
	}
	
	private void initListener() {
		headButton.setOnClickListener(this);
		modifyPwdButton.setOnClickListener(this);
		realNameButton.setOnClickListener(this);
		birthdayButton.setOnClickListener(this);
		mobileButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if(v==headButton){
			//选择头像
			Intent intent=new Intent(this,UploadImageActivity.class);
			this.startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.push_buttom_in, R.anim.push_buttom_out);
		}else if(v==modifyPwdButton){
			//修改密码
			Intent intent=new Intent(this,MyModifyPwdActivity.class);
			this.startActivity(intent);
		}else if(v==realNameButton){
			//别名修改 
			Intent intent=new Intent(this, MyInformationModifyActivity.class);
			intent.putExtra("title", realNameButton.getText().toString());
			intent.putExtra("columnName", "realName");
			intent.putExtra("columnValue", realNameView.getText().toString());
			this.startActivityForResult(intent, 2);
		}else if(v==birthdayButton){
			//生日修改
			Intent intent=new Intent(this, MyInformationModifyActivity.class);
			intent.putExtra("title", birthdayButton.getText().toString());
			intent.putExtra("columnName", "birthday");
			intent.putExtra("columnValue", birthdayView.getText().toString());
			this.startActivityForResult(intent, 2);
		}else if(v==mobileButton){
			//修改手机号码
//			Intent intent=new Intent(this, MyInformationModifyActivity.class);
//			intent.putExtra("title", mobileButton.getText().toString());
//			intent.putExtra("columnName", "mobile");
//			intent.putExtra("columnValue", mobileView.getText().toString());
//			this.startActivityForResult(intent, 2);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1){
			//上传图片返回
			if(data!=null){
				String imageUrl=data.getStringExtra("imageUrl");
				if(imageUrl!=null){
					DVolley.getImage(imageUrl,headImageView,R.drawable.personal_no_active_user_icon,true);
				}
			}
		}else if(requestCode==2){
			//修改账号信息
			if(data==null){
				return;
			}
			String columnName=data.getStringExtra("columnName");
			String columnValue=data.getStringExtra("columnValue");
			if(columnValue==null||columnValue.equals("")){
				return;
			}
			if(columnName.equals("realName")){
				realNameView.setText(columnValue);
			}
			if(columnName.equals("mobile")){
				mobileView.setText(columnValue);
			}
			if(columnName.equals("birthday")){
				birthdayView.setText(columnValue);
			}
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
			if(bean.getType()==DVolleyConstans.METHOD_USER_GET_BASEINFO){
				TUsers users=(TUsers)bean.getObject();
				usernameView.setText(users.getUserName());
				if(users.getNickName().equals("")){
					realNameView.setText(R.string.notset);
				}else{
					realNameView.setText(users.getNickName());
				}
				if(users.getMobile().equals("")){
					mobileView.setText(R.string.notset);
				}else{
					mobileView.setText(users.getMobile());
				}
				if(users.getBirthDay().equals("")){
					birthdayView.setText(R.string.notset);
				}else{
					birthdayView.setText(users.getBirthDay());
				}
				if(users.getEmail().equals("")){
					emailView.setText(R.string.notset);
				}else{
					emailView.setText(users.getEmail());
				}
				if(users.getRegTime().equals("")){
					regtimeView.setText(R.string.notset);
				}else{
					regtimeView.setText(users.getRegTime());
				}
				if(users.getLastTime().equals("")){
					lasttimeView.setText(R.string.notset);
				}else{
					lasttimeView.setText(users.getLastTime());
				}
				DVolley.getImage(users.getPortraitURL(),headImageView,R.drawable.personal_no_active_user_icon,true);
				return;
			}
		}
		this.showShortToast(message);
	}
}
