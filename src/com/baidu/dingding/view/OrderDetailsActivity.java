package com.baidu.dingding.view;

import com.baidu.dingding.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

public class OrderDetailsActivity extends Activity {
	private LinearLayout orderView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_details);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		orderView=(LinearLayout)findViewById(R.id.order_details_linearLayout_01);
		View view;
		view=LayoutInflater.from(OrderDetailsActivity.this).inflate(R.layout.order_details_list_view, null);
		orderView.addView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order_details, menu);
		return true;
	}

}
