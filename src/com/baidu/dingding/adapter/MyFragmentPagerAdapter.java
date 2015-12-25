package com.baidu.dingding.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{

	 List<Fragment> list;

	public  MyFragmentPagerAdapter(FragmentManager fragmentManager ){
			super(fragmentManager);
	}

	public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> list) {
	        super(fm);  
	        this.list = list;  
	    }



	@Override
	    public int getCount() {  
	        return list.size();  
	    }  
	      
	    @Override  
	    public Fragment getItem(int arg0) {  
	        return list.get(arg0);  
	    }

}
