package com.ghsh.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public abstract class AbstractBaseFragmentPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments;
	
	public AbstractBaseFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	public AbstractBaseFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
		super(fm);
		this.fragments=fragments;
	}

	@Override
	public Fragment getItem(int index) {
		return fragments.get(index);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
	
	/**
	 * 销毁position位置的界面
	 */
	@Override
	public void destroyItem(View view, int position, Object arg2) {
		((ViewPager) view).removeViewAt(position);
	}
}
