package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.adapter.MySendInfoListViewtAdapter;
import com.ghsh.code.bean.TSendInfo;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.MySendInfoModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.view.util.DAlertUtil;
import com.ghsh.R;

/**
 * 收货地址列表
 * */
public class MySendInfoActivity extends BaseActivity implements OnClickListener,MySendInfoListViewtAdapter.Listener {
	private TextView titleView;
	private Button addButton;//添加按钮
	private ListView listView;
	private MySendInfoListViewtAdapter adapter=new MySendInfoListViewtAdapter(this,new ArrayList<TSendInfo>());
	private MySendInfoModel sendInfoModel;
	private DProgressDialog progressDialog;
	private boolean isSelect=false;//选择地址，还是编辑地址
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!this.checkLogin()){
			return;
		}
		setContentView(R.layout.activity_mysendinfo);
		this.initView();
		this.initListener();
		sendInfoModel=new MySendInfoModel(this);
		sendInfoModel.addResponseListener(new SendInfoResponse(this));
		progressDialog.show();
		sendInfoModel.findSendInfoList(this.getUserID());
		
		isSelect=getIntent().getBooleanExtra("isSelect", false);
	}

	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.mySendinfo_title);
		addButton=(Button)this.findViewById(R.id.header_button_view);
		addButton.setText(R.string.add);
		addButton.setVisibility(View.VISIBLE);
		
		listView=(ListView)this.findViewById(R.id.content_view);
		listView.setAdapter(adapter);
	}

	private void initListener() {
		addButton.setOnClickListener(this);
		adapter.addListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(isSelect){
					Intent intent = new Intent();
					intent.putExtra("sendInfo", adapter.getItem(position));
					setResult(Activity.RESULT_OK, intent);
			        finish();
				}else{
					Intent intent=new Intent(MySendInfoActivity.this,MySendInfoAddActivity.class);
					intent.putExtra("sendInfo", adapter.getItem(position));
					MySendInfoActivity.this.startActivityForResult(intent, 1);
				}
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		if(v==addButton){
			//添加收货地址
			Intent intent=new Intent(this,MySendInfoAddActivity.class);
			intent.putExtra("isOne", adapter.getList().size()==0?true:false);
			this.startActivityForResult(intent, 1);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			progressDialog.show();
			sendInfoModel.findSendInfoList(this.getUserID());
		}
	}
	@Override
	public void deleteSendInfo(final TSendInfo sendInfo) {
		DAlertUtil.alertOKAndCel(this, R.string.mySendinfo_alter_message_delete, new android.content.DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				progressDialog.show();
				sendInfoModel.delSendInfo(sendInfo.getSendInfoID(),getUserID());
			}
		}).show();
	}
	@Override
	public void modifySendInfoDefault(TSendInfo sendInfo) {
		progressDialog.show();
		sendInfoModel.modifyDefault(getUserID(),sendInfo.getSendInfoID());
	}
	@Override
	public void modifySendInfo(TSendInfo sendInfo) {
		Intent intent=new Intent(MySendInfoActivity.this,MySendInfoAddActivity.class);
		intent.putExtra("sendInfo", sendInfo);
		MySendInfoActivity.this.startActivityForResult(intent, 1);
	}
	class SendInfoResponse extends DResponseAbstract{
		public SendInfoResponse(Activity activity) {
			super(activity);
		}
		@Override
		public void onResponseStart() {
			progressDialog.dismiss();
		}
		@Override
		protected void onResponseSuccess(ReturnBean bean, String message) {
			if(bean.getType()==DVolleyConstans.METHOD_QUERYALL){
				//返回查询所有收货地址
				List<TSendInfo> list=(List<TSendInfo>)bean.getObject();
				adapter.setList(list);
				adapter.notifyDataSetChanged();
				return;
			}else if(bean.getType()==DVolleyConstans.METHOD_DELETE){
				//删除
				String sendInfoID=(String)bean.getObject();
				adapter.removeSendInfoByID(sendInfoID);
			}else if(bean.getType()==DVolleyConstans.METHOD_ADDRESS_MODIFY_DEFAULT){
				//修改默认地址
				String sendInfoID=(String)bean.getObject();
				adapter.modifyDefault(sendInfoID);
				MySendInfoActivity.this.showShortToast(message);
				return;
			}
		}
		@Override
		protected boolean isShowEmptyPageView() {
			return adapter.getList().size()==0;
		}
		@Override
		protected void onRefresh() {
			progressDialog.show();
			sendInfoModel.findSendInfoList(getUserID());
		}
	}
}
