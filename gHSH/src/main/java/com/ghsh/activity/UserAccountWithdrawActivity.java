package com.ghsh.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.bean.TUsers;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.UserAccountModel;
import com.ghsh.code.volley.model.UserModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.util.BankUtils;
import com.ghsh.util.MenberUtils;
/**
 *  提现
 * */
public class UserAccountWithdrawActivity extends BaseActivity implements OnClickListener,DResponseListener{
	private TextView titleView;
	private DProgressDialog progressDialog;
	private Button addButton;//添加提现记录
	private UserAccountModel userAccountModel;
	private UserModel userModel;
	private EditText moneyView,truenameView,mobileView,banknameView,banknumView,descView;
	private TextView currentMoneyView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!this.checkLogin()){
			return;
		}
		setContentView(R.layout.activity_user_account_withdraw);
		this.initView();
		this.initListener();
		userAccountModel=new UserAccountModel(this);
		userAccountModel.addResponseListener(this);
		userModel=new UserModel(this);
		userModel.addResponseListener(this);
		progressDialog.show();
		userModel.getMoneyAndPointID(MenberUtils.getUserID(this));
	}
	
	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.withdraw_title);
		
		addButton = (Button) this.findViewById(R.id.header_button_view);
		addButton.setText(R.string.confirm);
		addButton.setVisibility(View.VISIBLE);
		
		moneyView= (EditText) this.findViewById(R.id.money_view);
		truenameView= (EditText) this.findViewById(R.id.truename_view);
		mobileView= (EditText) this.findViewById(R.id.mobile_view);
		banknameView= (EditText) this.findViewById(R.id.bankname_view);
		banknumView= (EditText) this.findViewById(R.id.banknum_view);
		descView= (EditText) this.findViewById(R.id.desc_view);
		
		currentMoneyView= (TextView) this.findViewById(R.id.current_money_view);
		
		truenameView.setText(BankUtils.getTrueName(this, MenberUtils.getUserID(this)));
		mobileView.setText(BankUtils.getMobile(this, MenberUtils.getUserID(this)));
		banknameView.setText(BankUtils.getBankName(this, MenberUtils.getUserID(this)));
		banknumView.setText(BankUtils.getBankNum(this, MenberUtils.getUserID(this)));
	}
	
	private void initListener(){
		addButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v==addButton){
			//添加提现记录
			String money=moneyView.getText().toString();
			String trueName=truenameView.getText().toString();
			String mobile=mobileView.getText().toString();
			String bankName=banknameView.getText().toString();
			String bankNum=banknumView.getText().toString();
			String desc=descView.getText().toString();
			if(money.equals("")){
				this.showShortToast("提现金额不能为空，请重新输入");
				return ;
			}
			if(trueName.equals("")){
				this.showShortToast("真实姓名不能为空，请重新输入");
				return ;
			}
			if(bankName.equals("")){
				this.showShortToast("开户行不能为空，请重新输入");
				return ;
			}
			if(bankNum.equals("")){
				this.showShortToast("银行账号不能为空，请重新输入");
				return ;
			}
			if(mobile.equals("")){
				this.showShortToast("手机号码不能为空，请重新输入");
				return ;
			}
			progressDialog.show();
			userAccountModel.addWithdraw(MenberUtils.getUserID(this), money, trueName, mobile, bankName, bankNum, desc);
			BankUtils.writeBank(this, MenberUtils.getUserID(this),trueName, mobile, bankName, bankNum);
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
				return ;
			}else if(bean.getType()==DVolleyConstans.METHOD_USER_MONEY_AND_POINT){
				//积分余额返回
				TUsers user=(TUsers)bean.getObject();
				if(user!=null){
					currentMoneyView.setText("余额："+user.getMoney());
				}
				return;
			}
		}
		this.showShortToast(message);
	}
}
