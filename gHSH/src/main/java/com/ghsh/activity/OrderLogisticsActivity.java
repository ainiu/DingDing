package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.adapter.OrderLogisticsListViewAdapter;
import com.ghsh.code.bean.TExpressinfo;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.OrderModel;
import com.ghsh.dialog.DProgressDialog;

/**
 * 查看物流----没有使用--可删除
 * */
public class OrderLogisticsActivity extends BaseActivity implements OnClickListener,DResponseListener{
	private TextView titleView;
	private DProgressDialog progressDialog;
	private TextView nameView,invoicenoView,statusView;
	private ListView listview;
	private OrderLogisticsListViewAdapter adapter=new OrderLogisticsListViewAdapter(this,new ArrayList<TExpressinfo.Info>());
	 private OrderModel orderModel = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_logistics);
		this.initView();
		Intent intent=this.getIntent();
        String orderID=intent.getStringExtra("orderID");
		orderModel = new OrderModel(this);
	    orderModel.addResponseListener(this);
	    progressDialog.show();
	    orderModel.getExpressInfo(orderID);
	}
	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.order_logistics_title);
		
		nameView= (TextView) this.findViewById(R.id.logistics_name_view);
		invoicenoView= (TextView) this.findViewById(R.id.logistics_invoiceno_view);
		statusView= (TextView) this.findViewById(R.id.logistics_status_view);
		
		listview= (ListView) this.findViewById(R.id.content_view);
		listview.setAdapter(adapter);
	}
	@Override
	public void onClick(View v) {
		
	}
	
	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		progressDialog.dismiss();
		if(error!=null){
			this.showShortToast(error.getMessage());
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_ORDER_QUERY_EXPRESS_INFO){
				TExpressinfo expressinfo=(TExpressinfo)bean.getObject();
				nameView.setText(expressinfo.getShippingName());
				invoicenoView.setText("运单编号："+expressinfo.getInvoiceNO());
				statusView.setText("物流状态："+expressinfo.getStateText());
				adapter.setList(expressinfo.getInfoList());
				adapter.notifyDataSetChanged();
				return;
			}
		}
		this.showShortToast(message);
	}
}
