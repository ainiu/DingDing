package com.baidu.dingding.adapter.adapterutil;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/12/15.
 */
public class ShopingPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> list;
    public ShopingPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public ShopingPagerAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list=list;
    }
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
