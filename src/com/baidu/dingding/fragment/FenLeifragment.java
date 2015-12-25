package com.baidu.dingding.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.dingding.R;
import com.baidu.dingding.adapter.MyFragmentPagerAdapter;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class FenLeifragment extends Fragment {

	private View view;
	View view_visi,view2_gone;
	private boolean VIEW_TAG=true;
	private List<Fragment> fragmentList;
	private TextView tv_shanping,tv_dianpu,barText;
	private ViewPager viewPager;
	private int currIndex=0;//当前页卡编号

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(getActivity(), R.layout.activity_fen_lei, null);
		initView();
		initviewPager();
		return view;
	}

	private void initviewPager() {
		viewPager= (ViewPager) view.findViewById(R.id.fenlei_viewpager);
		fragmentList = new ArrayList<Fragment>();
		Fenleifargmentshanping sahngping= new Fenleifargmentshanping();
		fragmentList.add(sahngping);
		Fenleifargmentdianpu dianpu=new Fenleifargmentdianpu();
		fragmentList.add(dianpu);
		//给ViewPager设置适配器
		viewPager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager(), fragmentList));
		viewPager.setCurrentItem(currIndex);                             //设置当前显示标签页为第一页
		viewPager.addOnPageChangeListener(new MyOnPageChangeListener()); //页面变化时的监听器

	}

	/**viewpager监听*/
	public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			switch (arg0){
				case 0:
					viewTag(VIEW_TAG);
					break;
				case 1:
					viewTag(!VIEW_TAG);
					break;
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageSelected(int arg0) {
			currIndex = arg0;
	}

	}


	private void initView() {
		tv_shanping= (TextView) view.findViewById(R.id.tv_guid1);
		tv_dianpu= (TextView) view.findViewById(R.id.tv_guid2);
		view_visi=  view.findViewById(R.id.view);
		view2_gone=  view.findViewById(R.id.view1);
		tv_shanping.setOnClickListener(new txListener(0));
		tv_dianpu.setOnClickListener(new txListener(1));

	}

	public void viewTag(Boolean VIEW_TAG){
		if(VIEW_TAG){
			view_visi.setVisibility(View.VISIBLE);
			view2_gone.setVisibility(View.INVISIBLE);
		}else{
			view_visi.setVisibility(View.INVISIBLE);
			view2_gone.setVisibility(View.VISIBLE);
		}
	}
	/**TextView设置监听*/
	 class txListener implements View.OnClickListener{
		private int index=0;

		public txListener(int i) {
			index =i;
		}
		@Override
		public void onClick(View v) {
			try {
				switch (v.getId()) {
					case R.id.tv_guid1:
						viewTag(VIEW_TAG);
						break;
					case R.id.tv_guid2:
						viewTag(!VIEW_TAG);
						break;
				}
			}catch (Exception e){
				LogUtil.i("监听出错","");
				ExceptionUtil.handleException(e);
			}
			viewPager.setCurrentItem(index);
		}
	}

}
