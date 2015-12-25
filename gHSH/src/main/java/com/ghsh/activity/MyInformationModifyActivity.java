package com.ghsh.activity;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.UserModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.util.DateFormatUtil;
import com.ghsh.R;

/**
 * 修改用户信息
 * */
public class MyInformationModifyActivity extends BaseActivity implements OnClickListener ,DResponseListener {
	private TextView titleView;
	private DProgressDialog progressDialog;
	private Button okButton;
	private EditText commentView;
	private UserModel userModel;
	private String columnName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinformation_modify);
		this.initView();
		this.initListener();
		this.initData();
		userModel=new UserModel(this);
		userModel.addResponseListener(this);
	}
	
	private void initView() {
		progressDialog = new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		
		okButton = (Button) this.findViewById(R.id.header_button_view);
		okButton.setText(R.string.save);
		okButton.setVisibility(View.VISIBLE);
		
		commentView = (EditText) this.findViewById(R.id.myinformation_modify_comment);
	}
	
	private void initListener() {
		okButton.setOnClickListener(this);
	}
	private void initData(){
		Intent intent=this.getIntent();
		String title=intent.getStringExtra("title");
		columnName=intent.getStringExtra("columnName");
		String columnValue=intent.getStringExtra("columnValue");
		titleView.setText(title);
		if(columnName.equals("realName")){
			//修改昵称
		}else if(columnName.equals("mobile")){
			//修改手机号码
		}else if(columnName.equals("birthday")){
			//修改生日
			if(columnValue.equals("未设置")){
				columnValue=DateFormatUtil.formatB(new Date());
			}
			commentView.setFocusable(false);
			commentView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Calendar calendar=Calendar.getInstance();
					calendar.setTime(DateFormatUtil.formatB(commentView.getText().toString()));
					int year=calendar.get(Calendar.YEAR);
					int month=calendar.get(Calendar.MONTH);
					int day=calendar.get(Calendar.DAY_OF_MONTH);
					new DatePickerDialog(MyInformationModifyActivity.this,
							new DatePickerDialog.OnDateSetListener() {
								@Override
								public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
									commentView.setText(year + "-"+ (monthOfYear+1) + "-" + dayOfMonth );
								}
					}, year, month, day).show();
				}
			});
		}
		commentView.setText(columnValue);
	}
	@Override
	public void onClick(View v) {
		if(v==okButton){
			String value=commentView.getText().toString().trim();
			if(columnName.equals("realName")){
				//修改昵称
				if(value.equals("")||value.length()>30){
					this.showShortToast("昵称不能为空且不能大于30个字符，请重新输入");
					return;
				}
			}else if(columnName.equals("birthday")){
				//修改生日
				
			}
			progressDialog.show();
			userModel.modifyUserInfo(this.getUserID(), columnName, value);
		}
	}
	@Override
	public void onMessageResponse(ReturnBean bean, int result,String message, Throwable error) {
		progressDialog.dismiss();
		if(error!=null){
			this.showShortToast(error.getMessage());
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_USER_MODIFY_USERINFO){
				//修改用户信息返回
				this.showShortToast(message);
				Intent intent = new Intent();
				intent.putExtra("columnName", columnName);
				intent.putExtra("columnValue", commentView.getText().toString());
				setResult(Activity.RESULT_OK, intent);
				this.finish();
				return;
			}
		}
		this.showShortToast(message);
	}
}
