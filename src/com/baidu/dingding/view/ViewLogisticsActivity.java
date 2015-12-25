package com.baidu.dingding.view;

import com.baidu.dingding.R;
import com.baidu.dingding.R.layout;
import com.baidu.dingding.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ViewLogisticsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_logistics);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_logistics, menu);
		return true;
	}

}
