package com.ghsh.activity;

import android.view.View;

import com.ghsh.activity.base.BaseStartActivity;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.R;

public class StartActivity extends BaseStartActivity{
	
	@Override
	protected Class<?> getHomeActivityClass() {
		return HomeActivity.class;
	}

	@Override
	protected View getStartContentView() {
		return View.inflate(this, R.layout.activity_start, null);
	}
	
	
	protected Class<?> getGotoActivityClass(){
		return MainTabActivity.class;
	}

}
