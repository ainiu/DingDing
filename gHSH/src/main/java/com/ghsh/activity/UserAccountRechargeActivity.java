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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.adapter.UserAccountRechargeListViewtAdapter;
import com.ghsh.code.bean.TPayment;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.PaymentModel;
import com.ghsh.code.volley.model.UserAccountModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.pay.OrderPayUtil;
import com.ghsh.util.MenberUtils;
/**
 *  充值
 * */
public class UserAccountRechargeActivity extends BaseActivity implements OnClickListener,DResponseListener{
	private TextView titleView;
	private DProgressDialog progressDialog;
	private Button addButton;//添加充值记录
	private UserAccountModel userAccountModel;
	private PaymentModel paymentModel;
	private EditText moneyView,descView;
	private UserAccountRechargeListViewtAdapter adapter=new UserAccountRechargeListViewtAdapter(this,new ArrayList<TPayment>());
	private ListView listView;
	private OrderPayUtil orderPayUtil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!this.checkLogin()){
			return;
		}
		setContentView(R.layout.activity_user_account_recharge);
		this.initView();
		this.initListener();
		userAccountModel=new UserAccountModel(this);
		userAccountModel.addResponseListener(this);
		orderPayUtil =new OrderPayUtil(this,progressDialog);
		paymentModel=new PaymentModel(this);
		paymentModel.addResponseListener(new PaymentResponse(this));
		progressDialog.show();
		paymentModel.findPaymentList();
	}
	
	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.recharge_title);
		
		addButton = (Button) this.findViewById(R.id.header_button_view);
		addButton.setText(R.string.confirm);
		addButton.setVisibility(View.VISIBLE);
		
		moneyView= (EditText) this.findViewById(R.id.recharge_money_view);
		descView= (EditText) this.findViewById(R.id.recharge_desc_view);
		
		listView= (ListView) this.findViewById(R.id.recharge_listView);
		listView.setAdapter(adapter);
	}
	
	private void initListener(){
		addButton.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				adapter.setSelected(adapter.getItem(position));
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		orderPayUtil.onActivityResult(requestCode, resultCode, data);//支付回调
	}

	@Override
	public void onClick(View v) {
		if(v==addButton){
			//添加提现记录
			String money=moneyView.getText().toString();
			String desc=descView.getText().toString();
			if(money.equals("")){
				this.showShortToast("提现金额不能为空，请重新输入");
				return ;
			}
			TPayment payment=adapter.getSelected();
			if(payment==null){
				this.showShortToast("请选择支付方式");
				return ;
			}
			progressDialog.show();
			userAccountModel.addRecharge(MenberUtils.getUserID(this), money,desc,payment.getPaymentID());
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
				orderPayUtil.payRecharge(bean, result,message,error);
				return ;
			}
		}
		this.showShortToast(message);
	}
	
	class PaymentResponse extends DResponseAbstract{
		public PaymentResponse(Activity activity) {
			super(activity);
		}
		@Override
		public void onResponseStart() {
			progressDialog.dismiss();
		}
		@Override
		protected void onResponseSuccess(ReturnBean bean, String message) {
			if(bean.getType()==DVolleyConstans.METHOD_QUERYALL){
				adapter.setList((List<TPayment>)bean.getObject());
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
			paymentModel.findPaymentList();
		}
	}
}
