package com.ghsh.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.bean.TRegion;
import com.ghsh.code.bean.TSendInfo;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.MySendInfoModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.services.UpdateService;
import com.ghsh.util.MenberUtils;
import com.ghsh.util.RegexpUtil;
import com.ghsh.view.util.DAlertUtil;
import com.ghsh.R;

/**
 * 收货地址 修改
 * */
public class MySendInfoAddActivity extends BaseActivity implements OnClickListener,DResponseListener {
	private TextView titleView;
	private Button addButton;
	private EditText consigneeView,mobileView,postCodeView,cityView,addressView;
	private MySendInfoModel sendInfoModel;
	private DProgressDialog progressDialog;
	private TRegion provinceRegion;
	private TRegion cityRegion;
	private TRegion areaRegion;
	private TSendInfo sendInfo;
	private boolean isOne=false;//是否是添加第一个
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mysendinfo_modify);
		this.initView();
		this.initListener();
		isOne=getIntent().getBooleanExtra("isOne", false);
		sendInfoModel=new MySendInfoModel(this);
		sendInfoModel.addResponseListener(this);
	}

	private void initView() {
		progressDialog=new DProgressDialog(this);
		
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.mySendinfo_add_title);
		
		addButton=(Button)this.findViewById(R.id.header_button_view);
		addButton.setText(R.string.save);
		addButton.setVisibility(View.VISIBLE);
		addButton.setTag(true);//true为添加
		
		consigneeView=(EditText)this.findViewById(R.id.mysendinfo_consignee);
		mobileView=(EditText)this.findViewById(R.id.mysendinfo_mobile);
		postCodeView=(EditText)this.findViewById(R.id.mysendinfo_postCode);
		cityView=(EditText)this.findViewById(R.id.mysendinfo_city);
		cityView.setFocusable(false);
		addressView=(EditText)this.findViewById(R.id.mysendinfo_address);
		
		Intent intent=getIntent();
		sendInfo=(TSendInfo)intent.getSerializableExtra("sendInfo");
		if(sendInfo!=null){
			consigneeView.setText(sendInfo.getConsignee());
			mobileView.setText(sendInfo.getMobile());
			postCodeView.setText(sendInfo.getZipcode());
			cityView.setText(sendInfo.getRegionName());
			addressView.setText(sendInfo.getAddress());
			
			provinceRegion=new TRegion(sendInfo.getProvince());
			cityRegion=new TRegion(sendInfo.getCity());
			areaRegion=new TRegion(sendInfo.getDistrict());
			
			addButton.setTag(false);
			addButton.setText(R.string.modify);
		}
	}

	private void initListener() {
		addButton.setOnClickListener(this);
		cityView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MySendInfoAddActivity.this,RegionActivity.class);
				MySendInfoAddActivity.this.startActivityForResult(intent, 1);
			}
		});
		consigneeView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(25)});
		mobileView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
		postCodeView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
		addressView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(255)});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if(data!=null){
				String regionText=data.getStringExtra("regionText");
				this.provinceRegion=(TRegion)data.getSerializableExtra("province");
				this.cityRegion=(TRegion)data.getSerializableExtra("city");
				this.areaRegion=(TRegion)data.getSerializableExtra("area");
				cityView.setText(regionText);
			}
		}
	}
	@Override
	public void onClick(View v) {
		if(v==addButton){
			//添加收货地址
			String consignee=consigneeView.getText().toString().trim();
			String mobile=mobileView.getText().toString().trim();
			String zipcode=postCodeView.getText().toString().trim();
			String regionName=cityView.getText().toString().trim();
			String address=addressView.getText().toString().trim();
			if(consignee.equals("")){
				this.showShortToast(R.string.mySendinfo_add_tip_consignee_empty);
				return;
			}
			
			if(mobile.equals("")){
				this.showShortToast(R.string.mySendinfo_add_tip_mobile_empty);
				return;
			}
			if (!RegexpUtil.checkMobNumber(mobile)) {
				this.showShortToast(R.string.mySendinfo_add_tip_mobile_error);
				return;
			}
			if (!zipcode.equals("")) {
				if (!RegexpUtil.checkCodeNumber(zipcode)) {
					this.showShortToast(R.string.mySendinfo_add_tip_zipcode);
					return;
				}
			}
			if(regionName.equals("")){
				this.showShortToast(R.string.mySendinfo_add_tip_city_empty);
				return;
			}
			if(address.equals("")){
				this.showShortToast(R.string.mySendinfo_add_tip_address_empty);
				return;
			}
			progressDialog.show();
			if(sendInfo==null){
				sendInfo=new TSendInfo();
			}
			sendInfo.setConsignee(consignee);
			sendInfo.setMobile(mobile);
			sendInfo.setZipcode(zipcode);
			sendInfo.setAddress(address);
			sendInfo.setRegionName(regionName);
			sendInfo.setProvince(provinceRegion.getRegioID());
			sendInfo.setCity(cityRegion.getRegioID());
			sendInfo.setDistrict(areaRegion.getRegioID());
			if((Boolean)addButton.getTag()){
				//添加
				if(isOne){
					sendInfo.setDefault(true);
					sendInfoModel.addSendInfo(MenberUtils.getUserID(this),sendInfo);
					return;
				}
				DAlertUtil.alertOKAndCel(this,R.string.mySendinfo_alter_message_default , new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//确定
						sendInfo.setDefault(true);
						sendInfoModel.addSendInfo(MenberUtils.getUserID(MySendInfoAddActivity.this),sendInfo);
					}
				},new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//取消
						sendInfo.setDefault(false);
						sendInfoModel.addSendInfo(MenberUtils.getUserID(MySendInfoAddActivity.this),sendInfo);
					}
				}).show();
			}else{
				//修改
				sendInfoModel.modifySendInfo(MenberUtils.getUserID(this),sendInfo);
			}
		}
	}

	@Override
	public void onMessageResponse(ReturnBean bean,int result, String message,Throwable error) {
		progressDialog.dismiss();
		if(error!=null){
			this.showShortToast(error.getMessage());
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_MODIFY){
				//修改
				Intent intent = new Intent();
				intent.putExtra("sendInfo", sendInfo);
				setResult(Activity.RESULT_OK, intent);
			}else if(bean.getType()==DVolleyConstans.METHOD_ADD){
				//添加
				Intent intent = new Intent();
				sendInfo.setSendInfoID(bean.getObject()+"");
				intent.putExtra("sendInfo", sendInfo);
				setResult(Activity.RESULT_OK, intent);
			}
	        finish();
		}
		this.showShortToast(message);
	}
}
