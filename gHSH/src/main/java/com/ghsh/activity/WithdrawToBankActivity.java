package com.ghsh.activity;

import java.math.BigDecimal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.bean.TBankAccount;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.UserModel;
import com.ghsh.code.volley.model.WithdrawModel;
import com.ghsh.dialog.DProgressDialog;
/**
 * 提现到银行卡
 * */
public class WithdrawToBankActivity extends BaseActivity implements OnClickListener,DResponseListener{
	private TextView titleView;
	private DProgressDialog progressDialog;
	private Button alipayButton;
	private EditText cardView,bankNameView,nameView,mobileView,moneyView;
	private TextView userMoneyView;//余额
	private BigDecimal money=new BigDecimal("0.00");//用户余额
	private TBankAccount bankAccount;
	private WithdrawModel withdrawModel;
	private UserModel userModel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!this.checkLogin()){
			return;
		}
		setContentView(R.layout.activity_withdraw_to_bank);
		Intent intent=getIntent();
		bankAccount=(TBankAccount)intent.getSerializableExtra("bankAccount");
		this.initView();
		this.initListener();
		
		withdrawModel=new WithdrawModel(this);
		withdrawModel.addResponseListener(this);
		userModel=new UserModel(this);
		userModel.addResponseListener(this);
		progressDialog.show();
		userModel.getMoneyAndPointID(this.getUserID());
	}
	
	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.withdraw_title_record_bank);
		
		userMoneyView= (TextView) this.findViewById(R.id.withdraw_user_money);
		
		cardView= (EditText) this.findViewById(R.id.withdraw_bank_card);
		bankNameView= (EditText) this.findViewById(R.id.withdraw_bank_bankName);
		nameView= (EditText) this.findViewById(R.id.withdraw_bank_name);
		mobileView= (EditText) this.findViewById(R.id.withdraw_bank_mobile);
		moneyView= (EditText) this.findViewById(R.id.withdraw_bank_money);
		
		
		alipayButton= (Button) this.findViewById(R.id.withdraw_alipay_button);
		
		if(bankAccount!=null){
			cardView.setText(bankAccount.getBankSN());
			bankNameView.setText(bankAccount.getBankName());
			nameView.setText(bankAccount.getName());
			mobileView.setText(bankAccount.getMobile());
		}
	}
	
	private void initListener(){
		alipayButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if(v==alipayButton){
			//申请提现
			if(money.doubleValue()<10.00){
				this.showShortToast("余额不足，提现金额需要大于10元");
				return;
			}
			String moneyStr=moneyView.getText().toString();
			if(moneyStr.equals("")||moneyStr.length()>30){
				this.showShortToast("金额不能为空，请重新输入！");
				return;
			}
			try {
				BigDecimal jiner=new BigDecimal(moneyStr);
				if(jiner.doubleValue()>money.doubleValue()){
					this.showShortToast("提现金额不能大于余额，请重新输入！");
					return;
				}
			} catch (Exception e) {
				this.showShortToast("金额格式错误，请重新输入！");
				return;
			}
			progressDialog.show();
			withdrawModel.addUserBankCard(this.getUserID(), bankAccount.getId(), moneyStr);
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
			if(bean.getType()==DVolleyConstans.METHOD_ADD){
				this.showShortToast(message);
				this.finish();
			}else if(bean.getType()==DVolleyConstans.METHOD_USER_MONEY_AND_POINT){
//				TMenber menber=(TMenber)bean.getObject();
//				if(menber!=null){
//					money=menber.getMoney();
//					userMoneyView.setText(menber.getMoney()+"元");
//				}
				return;
			}
		}
	}
}
