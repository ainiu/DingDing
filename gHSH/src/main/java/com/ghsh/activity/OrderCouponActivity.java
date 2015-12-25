package com.ghsh.activity;

import java.math.BigDecimal;
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
import com.ghsh.adapter.OrderCouponListViewtAdapter;
import com.ghsh.code.bean.TCoupon;
import com.ghsh.code.bean.TPayment;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.CouponModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.R;
/**
 * 订单-选择优惠劵
 * */
public class OrderCouponActivity extends BaseActivity implements OrderCouponListViewtAdapter.Listener{
	private TextView titleView;
	private CouponModel couponModel;
	private DProgressDialog progressDialog; 
	private ListView listView;
	private OrderCouponListViewtAdapter adapter=new OrderCouponListViewtAdapter(this,new ArrayList<TCoupon>());
	private String couponID;
	private String money="0.00";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_coupon);
		this.initView();
		this.initListener();
		couponID=getIntent().getStringExtra("couponID");
		money=getIntent().getStringExtra("money");
		couponModel=new CouponModel(this);
		couponModel.addResponseListener(new CouponResponse(this));
		progressDialog.show();
		couponModel.findOrderCouponList(this.getUserID(),money);
	}
	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.order_coupon_title);
		listView=(ListView) this.findViewById(R.id.content_view);
		listView.setAdapter(adapter);
	}

	private void initListener() {
		adapter.addListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				TCoupon coupon=adapter.getItem(position);
				Intent intent = new Intent();
				intent.putExtra("coupon", coupon);
				setResult(Activity.RESULT_OK, intent);
		        finish();
			}
		});
	}

 
	class CouponResponse extends DResponseAbstract{
		public CouponResponse(Activity activity) {
			super(activity);
		}
		@Override
		public void onResponseStart() {
			progressDialog.dismiss();
		}
		@Override
		protected void onResponseSuccess(ReturnBean bean, String message) {
			if(bean.getType()==DVolleyConstans.METHOD_QUERYALL){
				adapter.setList((List<TCoupon>)bean.getObject());
				adapter.setSelectedCouponByID(couponID);
				adapter.notifyDataSetChanged();
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
			couponModel.findOrderCouponList(OrderCouponActivity.this.getUserID(),money);
		}
	}
	
	@Override
	public void onCheckBox(TCoupon coupon) {
		Intent intent = new Intent();
		intent.putExtra("coupon", coupon);
		setResult(Activity.RESULT_OK, intent);
        finish();
		
	}
}
