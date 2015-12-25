package com.baidu.dingding.view;

import com.baidu.dingding.R;
import com.baidu.dingding.R.layout;
import com.baidu.dingding.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;

public class GongGaoActivity extends Activity {
	
	WebView webview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gong_gao);
		String gonggaoURL = getIntent().getStringExtra("gonggaoURL");
		webview=(WebView) findViewById(R.id.gonggao_web);
		webview.loadUrl(gonggaoURL);
	}



}
