package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.adapter.OrderListListViewtAdapter;
import com.ghsh.code.bean.TOrder;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.OrderModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.pay.OrderPayUtil;
import com.ghsh.view.pullrefresh.DListView;
import com.ghsh.view.pullrefresh.base.DOnRefreshListener;
import com.ghsh.view.util.DAlertUtil;

/**
 * 待付款订单
 * */
public class OrderListActivity extends BaseActivity implements OrderListListViewtAdapter.Listener{
	private TextView titleView;
	private DListView refreshListView;
	private ListView listView;
	private OrderListListViewtAdapter adapter = new OrderListListViewtAdapter(this, new ArrayList<TOrder>());
	private OrderModel orderModel = null;
	private DProgressDialog progressDialog;
	private int orderType = 0;
	private int currentPage=1;
	private OrderPayUtil orderPayUtil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!this.checkLogin()) {
			return;
		}
		setContentView(R.layout.activity_orderlist);

		Intent intent = getIntent();
		orderType = intent.getIntExtra("orderType", 0);
		this.initView();
		this.initListener();
		orderModel = new OrderModel(this);
		orderModel.addResponseListener(new OrderResponse(this));
		orderPayUtil =new OrderPayUtil(this,progressDialog);
		OrderListActivity.this.resetData();
	}

	private void initView() {
		progressDialog = new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		switch (orderType) {
		case 0:
			adapter.setAllOrder(true);
			titleView.setText(R.string.personal_list_allorder);
			break;
		case 1:
			titleView.setText(R.string.personal_order_payment);
			break;
		case 2:
			titleView.setText(R.string.personal_order_delivery);
			break;
		case 3:
			titleView.setText(R.string.personal_order_receipt);
			break;
		case 4:
			titleView.setText(R.string.personal_order_evaluate);
			break;
		}

		refreshListView = (DListView) this.findViewById(R.id.content_view);
		refreshListView.setPullUpEnabled(true);
		listView = refreshListView.getRefreshableView();
		listView.setAdapter(adapter);
	}

	private void initListener() {
		adapter.addListener(this);
		refreshListView.setOnRefreshListener(new DOnRefreshListener() {
			@Override
			public void onPullDownToRefresh() {
				
			}
			@Override
			public void onPullUpToRefresh() {
				orderModel.findOrderList(getUserID(), orderType,currentPage);
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent=new Intent(OrderListActivity.this,OrderDetalisActivity.class);
				intent.putExtra("orderType", -1);
				intent.putExtra("orderID", adapter.getItem(position).getOrderID());
				intent.putExtra("orderSN", adapter.getItem(position).getOrderSN());
				OrderListActivity.this.startActivityForResult(intent, 1);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		orderPayUtil.onActivityResult(requestCode, resultCode, data);//支付回调
		if (resultCode == Activity.RESULT_OK) {
			if(requestCode==1){
				OrderListActivity.this.resetData();
			}
		}
	}

	@Override
	public void payCallback(final TOrder order) {
		orderPayUtil.payOrder(order.getOrderID(), null,new android.content.DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				OrderListActivity.this.finish();
			}
		});
	}
	
	@Override
	public void receiptCallback(final String orderID) {
		//确认收货
		DAlertUtil.alertOKAndCel(this, R.string.order_list_alter_message_receipt, new android.content.DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				progressDialog.show();
				orderModel.receiptOrder(orderID);
			}
		}).show();
	}

	@Override
	public void commentCallback(String orderID) {
		//评价
		Intent intent=new Intent(this,CommentAddActivity.class);
		intent.putExtra("orderID", orderID);
		this.startActivityForResult(intent, 1);
	}
	@Override
	public void remindCallback(String orderID, String orderSN) {
		this.showShortToast(R.string.fun_wait_development);
	}
	@Override
	public void streamCallback(String orderID, String orderSN) {
		Intent intent=new Intent(this,OrderLogisticsActivity.class);
		intent.putExtra("orderID", orderID);
		this.startActivity(intent);
	}
	private void resetData(){
		progressDialog.show();
		currentPage=1;
		adapter.clearAllList();
		orderModel.findOrderList(this.getUserID(), orderType,currentPage);
	}
	class OrderResponse extends DResponseAbstract{
		public OrderResponse(Activity activity) {
			super(activity,R.layout.empty_orderlist_pager);
		}
		@Override
		public void onResponseStart() {
			progressDialog.dismiss();
		}
		@Override
		protected void onResponseEnd(boolean isMoreData) {
			refreshListView.onStopUpRefresh(isMoreData);
		}
		@Override
		protected void onResponseSuccess(ReturnBean bean, String message) {
			if (bean.getType() == DVolleyConstans.METHOD_QUERYALL) {
				// 订单查询结果
				List<TOrder> list=(List<TOrder>) bean.getObject();
				if(list.size()==0&&adapter.getList().size()!=0){
					this.setMoreData(false);
					return;
				}
				adapter.addAllList(list);
				currentPage++;
			}else if (bean.getType() == DVolleyConstans.METHOD_ORDER_RECEIPT) {
				// 确认收货返回
				adapter.removeByOrderID(bean.getObject()+"");
				OrderListActivity.this.commentCallback(bean.getObject()+"");
			}
		}
		@Override
		protected boolean isShowEmptyPageView() {
			return adapter.getList().size()==0;
		}
		@Override
		protected void onRefresh() {
			OrderListActivity.this.resetData();
		}
	}
	
}
