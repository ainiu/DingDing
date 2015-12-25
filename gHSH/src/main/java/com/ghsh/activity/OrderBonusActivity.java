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

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.adapter.OrderBonusListViewtAdapter;
import com.ghsh.code.bean.TMyBonus;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.MyBonusModel;
import com.ghsh.dialog.DProgressDialog;
/**
 * 订单-选择红包
 * */
public class OrderBonusActivity extends BaseActivity implements OrderBonusListViewtAdapter.Listener{
	private TextView titleView;
	private MyBonusModel myBonusModel;
	private DProgressDialog progressDialog; 
	private ListView listView;
	private OrderBonusListViewtAdapter adapter=new OrderBonusListViewtAdapter(this,new ArrayList<TMyBonus>());
	private String bonusID;
	private String money="0.00";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_bonus);
		this.initView();
		this.initListener();
		bonusID=getIntent().getStringExtra("bonusID");
		money=getIntent().getStringExtra("money");
		myBonusModel=new MyBonusModel(this);
		myBonusModel.addResponseListener(new BonusResponse(this));
		progressDialog.show();
		myBonusModel.findOrderBonusList(this.getUserID(),money);
	}
	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.order_bonus_title);
		listView=(ListView) this.findViewById(R.id.content_view);
		listView.setAdapter(adapter);
	}

	private void initListener() {
		adapter.addListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				TMyBonus myBonus=adapter.getItem(position);
				Intent intent = new Intent();
				intent.putExtra("myBonus", myBonus);
				setResult(Activity.RESULT_OK, intent);
		        finish();
			}
		});
	}

 
	class BonusResponse extends DResponseAbstract{
		public BonusResponse(Activity activity) {
			super(activity);
		}
		@Override
		public void onResponseStart() {
			progressDialog.dismiss();
		}
		@Override
		protected void onResponseSuccess(ReturnBean bean, String message) {
			if(bean.getType()==DVolleyConstans.METHOD_QUERYALL){
				adapter.setList((List<TMyBonus>)bean.getObject());
				adapter.setSelectedBonusByID(bonusID);
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
			myBonusModel.findOrderBonusList(OrderBonusActivity.this.getUserID(),money);
		}
	}
	
	@Override
	public void onCheckBox(TMyBonus myBonus) {
		Intent intent = new Intent();
		intent.putExtra("myBonus", myBonus);
		setResult(Activity.RESULT_OK, intent);
        finish();
	}
}
