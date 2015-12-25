package com.ghsh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.view.DToastView;
import com.ghsh.R;
/**
 * 提现到支付宝
 * */
public class WithdrawToAlipayActivity extends BaseActivity implements OnClickListener{
	private TextView titleView;
	private DProgressDialog progressDialog;
	private Button alipayButton;
	private EditText nameView,accountView,moneyView;
	private TextView userMoneyView;//余额
	private double money=0.00;//用户余额
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!this.checkLogin()){
			return;
		}
		setContentView(R.layout.activity_withdraw_to_alipay);
		Intent intent=getIntent();
		money=intent.getDoubleExtra("money", 0.00);
		this.initView();
		this.initListener();
	}
	
	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.withdraw_title_record_alipay);
		
		userMoneyView= (TextView) this.findViewById(R.id.withdraw_user_money);
		
		nameView= (EditText) this.findViewById(R.id.withdraw_alipay_name);
		accountView= (EditText) this.findViewById(R.id.withdraw_alipay_account);
		moneyView= (EditText) this.findViewById(R.id.withdraw_alipay_money);
		
		alipayButton= (Button) this.findViewById(R.id.withdraw_alipay_button);
		
		userMoneyView.setText(money+"元");
	}
	
	private void initListener(){
		alipayButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v==alipayButton){
			//申请提现
			if(money<10.00){
				this.showShortToast("余额不足，提现金额需要大于10元");
				return;
			}
			String name=nameView.getText().toString();
			String account=accountView.getText().toString();
			String money=moneyView.getText().toString();
			if(name.equals("")||name.length()>30){
				this.showShortToast("姓名不能为空，且不能大于30个字符！");
				return;
			}
			if(account.equals("")||account.length()>30){
				this.showShortToast("支付宝账号不能为空，且不能大于30个字符！");
				return;
			}
			if(money.equals("")||money.length()>30){
				this.showShortToast("金额不能为空，请重新输入！");
				return;
			}
		}
	}
}
