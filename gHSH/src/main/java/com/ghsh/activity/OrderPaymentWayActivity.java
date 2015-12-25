package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.adapter.OrderPaymentWayListViewtAdapter;
import com.ghsh.code.bean.TPayment;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.PaymentModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.R;

/**
 * 订单--支付方式
 * */
public class OrderPaymentWayActivity extends BaseActivity implements OrderPaymentWayListViewtAdapter.Listener{
	private TextView titleView;
	private OrderPaymentWayListViewtAdapter adapter=new OrderPaymentWayListViewtAdapter(this,new ArrayList<TPayment>());
	private ListView listView;
	private DProgressDialog progressDialog;
	private PaymentModel paymentModel;
	private String paymentID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_paymentway);
		this.initView();
		this.initListener();
		paymentID=getIntent().getStringExtra("paymentID");
		paymentModel=new PaymentModel(this);
		paymentModel.addResponseListener(new PaymentResponse(this));
		progressDialog.show();
		paymentModel.findPaymentList();
	}

	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.order_paymentway_title);
		
		listView=(ListView)this.findViewById(R.id.content_view);
		listView.setAdapter(adapter);
	}

	private void initListener() {
		adapter.addListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				TPayment payment=adapter.getItem(position);
				Intent intent = new Intent();
				intent.putExtra("payment", payment);
				setResult(Activity.RESULT_OK, intent);
		        finish();
			}
		});
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
				adapter.setSelectedPaymentByID(paymentID);
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

	@Override
	public void onCheckBox(TPayment payment) {
		Intent intent = new Intent();
		intent.putExtra("payment", payment);
		setResult(Activity.RESULT_OK, intent);
        finish();
	}
}
