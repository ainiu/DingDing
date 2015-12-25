package com.ghsh.activity.base;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.ghsh.activity.ConductActivity;

public abstract class BaseStartActivity extends BaseActivity{
	
	private View startView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startView = this.getStartContentView();
		setContentView(startView);
		this.initView();
	}
	
	private void initView(){
		AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(2000);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent(BaseStartActivity.this, BaseStartActivity.this.getGotoActivityClass());
				BaseStartActivity.this.startActivity(intent);
				BaseStartActivity.this.finish();
			} 
		});
		startView.setAnimation(animation);
		animation.startNow();
	}

	
	
	/**获取首页class*/
	protected abstract Class<?> getHomeActivityClass();
	/**获取启动页 view*/
	protected abstract View getStartContentView();
	/**获取主页class*/
	protected Class<?> getGotoActivityClass(){
//		return BaseMainTabActivity.class;
		return ConductActivity.class;
	};
}
