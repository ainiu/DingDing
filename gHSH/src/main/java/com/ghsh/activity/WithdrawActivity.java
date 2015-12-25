package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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
import com.ghsh.adapter.WithdrawListViewtAdapter;
import com.ghsh.code.bean.TBankAccount;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.WithdrawModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.R;
/**
 * 提现
 * */
public class WithdrawActivity extends BaseActivity implements OnClickListener,DResponseListener{
	private TextView titleView;
	private Button recordButton;//提现记录
	private DProgressDialog progressDialog;
	private ListView listView;
	private WithdrawListViewtAdapter adapter=new WithdrawListViewtAdapter(this,new ArrayList<TBankAccount>());
	private Button addBank;//添加银行卡
	private WithdrawModel withdrawModel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!this.checkLogin()){
			return;
		}
		setContentView(R.layout.activity_withdraw);
		this.initView();
		this.initListener();
		withdrawModel=new WithdrawModel(this);
		withdrawModel.addResponseListener(this);
		progressDialog.show();
		withdrawModel.findUserCartList(this.getUserID());
	}
	
	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.withdraw_title);
		recordButton = (Button) this.findViewById(R.id.header_button_view);
		recordButton.setVisibility(View.VISIBLE);
		recordButton.setText(R.string.withdraw_text_record);
		
		listView=(ListView)this.findViewById(R.id.withdraw_listview);
		listView.addFooterView(this.getView(R.layout.activity_withdraw_bank_add_button));
//		TBankAccount bankAccount=new TBankAccount();
//		bankAccount.setId("");
//		bankAccount.setName("提现到支付宝");
//		bankAccount.setImageRes(R.drawable.withdraw_alipay_icon);
//		adapter.add(bankAccount);
		listView.setAdapter(adapter);
		
		addBank = (Button) this.findViewById(R.id.withdraw_bank_add_button);
	}
	
	private void initListener(){
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//				if(position==0){
//					//提现到支付宝
//					Intent intent=new Intent(WithdrawActivity.this,WithdrawToAlipayActivity.class);
//					WithdrawActivity.this.startActivity(intent);
//				}else{
					//提现到银行卡
					Intent intent=new Intent(WithdrawActivity.this,WithdrawToBankActivity.class);
					intent.putExtra("bankAccount", adapter.getItem(position));
					WithdrawActivity.this.startActivity(intent);
//				}
			}
		});
		addBank.setOnClickListener(this);
		recordButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v==addBank){
			//添加银行卡
			Intent intent=new Intent(WithdrawActivity.this,WithdrawAddBankActivity.class);
			this.startActivityForResult(intent, 100);
		}else if(v==recordButton){
			//提现记录
			Intent intent=new Intent(WithdrawActivity.this,UserAccountRecordActivity.class);
			this.startActivity(intent);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==Activity.RESULT_OK){
			if(requestCode==100){
				progressDialog.show();
				withdrawModel.findUserCartList(this.getUserID());
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
			if(bean.getType()==DVolleyConstans.METHOD_QUERYALL){
				List<TBankAccount> list=(List<TBankAccount>)bean.getObject();
				if(list==null){
					list=new ArrayList<TBankAccount>();
				}
//				TBankAccount bankAccount=new TBankAccount();
//				bankAccount.setId("");
//				bankAccount.setName("提现到支付宝");
//				bankAccount.setImageRes(R.drawable.withdraw_alipay_icon);
//				list.add(0,bankAccount);
				adapter.setList(list);
				adapter.notifyDataSetChanged();
			}
		}
	}
}
