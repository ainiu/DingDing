package com.ghsh.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class GoodsDetailsMainPagerAdapter extends AbstractBaseFragmentPagerAdapter {
	
	public GoodsDetailsMainPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public GoodsDetailsMainPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
		super(fm,fragments);
	}
}
