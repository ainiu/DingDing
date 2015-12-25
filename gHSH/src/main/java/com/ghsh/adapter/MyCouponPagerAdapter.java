package com.ghsh.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class MyCouponPagerAdapter extends AbstractBaseFragmentPagerAdapter {
	
	public MyCouponPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public MyCouponPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
		super(fm,fragments);
	}
}
