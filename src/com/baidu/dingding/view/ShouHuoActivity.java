package com.baidu.dingding.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.baidu.dingding.R;
import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.adapter.AddressListAdapter;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.AddressListModel;
import com.baidu.dingding.entity.AddressEntity;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.ProgrressDialog.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class ShouHuoActivity extends BaseActivity implements DResponseListener,AddressListAdapter.Imgclickitemid{

	Button button_add;
	ListView listView;
	List<AddressEntity> addressEntityList=new ArrayList<AddressEntity>();
	AddressListAdapter addressListAdapter;
	AddressListModel addressListModel;
	ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shou_huo);
		initModel();
		initData();
		initView();
		initLenar();
	}

	String userNumber="";
	String token="";
	private void initData() {
		userNumber = (String) SharedPreferencesUtils.get(this, "userNumber", "");
		token= (String) SharedPreferencesUtils.get(this,"token","");
	}

	private void initModel() {
		addressListModel=new AddressListModel(this);
		addressListModel.addResponseListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		addressListModel.findConsultList(userNumber);
		progressDialog.show();
	}

	private void initLenar() {
		addressListAdapter.addListener(this);
		button_add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ShouHuoActivity.this, XinZengActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initView() {
		progressDialog=new ProgressDialog(this);
		button_add= (Button) this.findViewById(R.id.shouhuo_add);
		listView= (ListView) this.findViewById(R.id.shouhuo_listView_01);
		addressListAdapter=new AddressListAdapter(this,addressEntityList);
		listView.setAdapter(addressListAdapter);
	}

	public void doClick(View v){
		this.finish();
	}


	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
		LogUtil.i(" ’ªıµÿ÷∑", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
		progressDialog.dismiss();
		if (result == DVolleyConstans.RESULT_FAIL) {
			Toasttool.MyToastLong(this, message);
			return;
		}
		if (result == DVolleyConstans.RESULT_OK) {
			if (bean.getType() == DVolleyConstans.SHOUHUOADDRESS) {
				addressEntityList = (List<AddressEntity>) bean.getObject();
				addressListAdapter.setBeanList(addressEntityList);
				addressListAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void getPostion(int postion) {
		//Toasttool.MyToast(ShouHuoActivity.this,postion+"");
		LogUtil.i("ShouHuoActivity-->","ShouhuoAdressActivityPosition="+postion);
		Intent intent=new Intent(this,ShouHuoAdressActivity.class);
		AddressEntity addressEntity=addressEntityList.get(postion);
		intent.putExtra("receiveId", addressEntity.getReceiveId());
		startActivity(intent);
	}
}
