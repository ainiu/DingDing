package com.baidu.dingding.view;

import com.baidu.dingding.R;
import com.baidu.dingding.R.layout;
import com.baidu.dingding.R.menu;
import com.baidu.dingding.until.Consts;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

public class ShopinghelpActivity extends Activity {
	
	WebView webview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoping_help);
		//String shopingHelpURL = getIntent().getStringExtra("shopingHelpURL");
		webview=(WebView) findViewById(R.id.shopinghelp_web);
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webview.loadUrl(Consts.SHOPING_HELP_WANGYE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gong_gao, menu);
		return true;
	}

}
