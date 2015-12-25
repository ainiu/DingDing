package com.baidu.dingding.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.baidu.dingding.R;

public class ChanPinActivity extends Activity {
	private ImageView imageView;
	private ImageView imageView2;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chan_pin);
		//³õÊ¼»¯¿Ø¼þ
		InitView();
	}

	private void InitView() {
		// TODO Auto-generated method stub
		imageView=(ImageView)findViewById(R.id.chanpin_image_01);
		imageView2=(ImageView)findViewById(R.id.chanpin_image_02);
		listView=(ListView)findViewById(R.id.chanpin_listView_01);
	}
     
	public void doClick(View view){
		switch (view.getId()) {
		case R.id.chanpin_image_01:
			Intent intent2=new Intent();	
            startActivity(intent2);
			break;

		case R.id.chanpin_image_02:
			Intent intent=new Intent(this,ChanPinMuActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chan_pin, menu);
		return true;
	}

}
