package com.baidu.dingding.view;

import com.baidu.dingding.R;
import com.baidu.dingding.R.layout;
import com.baidu.dingding.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.GridView;
import android.widget.ImageView;

public class ChanPinMuActivity extends Activity {
	private ImageView imageView;
	private ImageView imageView2;
	private GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chan_pin_mu);
		InitView();
	}

	private void InitView() {
		// TODO Auto-generated method stub
		imageView=(ImageView)findViewById(R.id.chanpinmu_list_image_01);
		imageView2=(ImageView)findViewById(R.id.chanpin_list_image_02);
		gridView=(GridView)findViewById(R.id.gridView1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chan_pin_mu, menu);
		return true;
	}

}
