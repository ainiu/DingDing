package com.fanc.mycar.view;



import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.fanc.mycar.R;

public class HomeActivity extends Activity {
   private LinearLayout groupView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
		
	}

	private void initView() {
		groupView=(LinearLayout)findViewById(R.id.linearLayout02);
		View view;
		view=LayoutInflater.from(HomeActivity.this).inflate(R.layout.children_list, null);
		groupView.addView(view);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
