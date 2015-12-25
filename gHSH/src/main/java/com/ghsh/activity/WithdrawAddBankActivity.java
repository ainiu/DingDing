package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ghsh.Constants;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.bean.TBank;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.WithdrawModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.R;
/**
 * 添加银行卡
 * */
public class WithdrawAddBankActivity extends BaseActivity implements OnClickListener,DResponseListener{
	private TextView titleView;
	private DProgressDialog progressDialog;
	private Button alipayButton;
	private EditText cardView,nameView,mobileView,verifyView;
	private Spinner bankNameView;
	private ArrayAdapter<TBank> bankNameAdapter;
	private List<TBank> bankList=new ArrayList<TBank>();
	private WithdrawModel withdrawModel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!this.checkLogin()){
			return;
		}
		setContentView(R.layout.activity_withdraw_add_bank);
		this.initView();
		this.initListener();
		
		withdrawModel=new WithdrawModel(this);
		withdrawModel.addResponseListener(this);
		progressDialog.show();
		withdrawModel.findBankList();
	}
	
	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.withdraw_title_record_bank_add);
		
		nameView= (EditText) this.findViewById(R.id.withdraw_bank_add_name);
		cardView= (EditText) this.findViewById(R.id.withdraw_bank_add_card);
		bankNameView= (Spinner) this.findViewById(R.id.withdraw_bank_add_bankName);
		
		mobileView= (EditText) this.findViewById(R.id.withdraw_bank_add_mobile);
		verifyView= (EditText) this.findViewById(R.id.withdraw_bank_add_verify);
		
		alipayButton= (Button) this.findViewById(R.id.withdraw_alipay_button);
		
		bankList.add(new TBank(null,this.getResources().getString(R.string.withdraw_hint_record_bank_add_bankName)));
		bankNameAdapter=new ArrayAdapter<TBank>(this,android.R.layout.simple_spinner_item,bankList);
		bankNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		bankNameView.setAdapter(bankNameAdapter);
		
		verifyView.setVisibility(View.GONE);
	}
	
	private void initListener(){
		alipayButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v==alipayButton){
			String name=nameView.getText().toString();
			String bankSN=cardView.getText().toString();
			TBank bank=(TBank)bankNameView.getSelectedItem();
			String mobile=mobileView.getText().toString();
			if(name.equals("")||name.length()>30){
				this.showShortToast("姓名不能为空，且不能大于30个字符！");
				return;
			}
			if(bankSN.equals("")||bankSN.length()>30){
				this.showShortToast("卡号不能为空，且不能大于30个字符！");
				return;
			}
			if(bank==null||bank.getBankID()==null){
				this.showShortToast("请选择银行！");
				return;
			}
			if(mobile.equals("")||mobile.length()>30){
				this.showShortToast("手机不能为空，且不能大于30个字符！");
				return;
			}
			progressDialog.show();
			withdrawModel.addBankCard(this.getUserID(), bank.getBankID(), bankSN, name, mobile);
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
				bankList.addAll((List<TBank>)bean.getObject());
				bankNameAdapter.notifyDataSetChanged();
			}else if(bean.getType()==DVolleyConstans.METHOD_ADD){
				//添加卡号成功
				setResult(Activity.RESULT_OK);
				this.finish();
			}
		}
	}
}
