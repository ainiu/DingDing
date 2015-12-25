package com.ghsh.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class DViewPagerNoScroll extends ViewPager{
	public DViewPagerNoScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DViewPagerNoScroll(Context context) {
		super(context);
	}
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}
}
