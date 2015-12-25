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
import com.ghsh.adapter.OrderPayWayListViewtAdapter;
import com.ghsh.code.bean.TShipping;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.ShippingModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.R;

/**
 * 订单--配送方式
 * */
public class OrderPayWayActivity extends BaseActivity implements DResponseListener,OrderPayWayListViewtAdapter.Listener{
	private TextView titleView;
	private ShippingModel shippingModel;
	private DProgressDialog progressDialog; 
	private ListView listView;
	private OrderPayWayListViewtAdapter adapter=new OrderPayWayListViewtAdapter(this,new ArrayList<TShipping>());
	private String shippingID,addressID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_payway);
		this.initView();
		this.initListener();
		shippingID=getIntent().getStringExtra("shippingID");
		addressID=getIntent().getStringExtra("addressID");
		shippingModel=new ShippingModel(this);
		shippingModel.addResponseListener(this);
		progressDialog.show();
		shippingModel.findOrderShippingList(addressID);
	}

	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.order_payway_title);
		listView=(ListView) this.findViewById(R.id.listView);
		listView.setAdapter(adapter);
	}

	private void initListener() {
		adapter.addListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				TShipping shipping=adapter.getItem(position);
				Intent intent = new Intent();
				intent.putExtra("shipping", shipping);
				setResult(Activity.RESULT_OK, intent);
		        finish();
			}
		});
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
				adapter.setList((List<TShipping>)bean.getObject());
				adapter.notifyDataSetChanged();
				adapter.setSelectedShippingByID(shippingID);
				return;
			}
		}
		this.showShortToast(message);
	}

	@Override
	public void onCheckBox(TShipping shipping) {
		Intent intent = new Intent();
		intent.putExtra("shipping", shipping);
		setResult(Activity.RESULT_OK, intent);
        finish();
	}
}
