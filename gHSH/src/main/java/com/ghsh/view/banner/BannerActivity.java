package com.ghsh.view.banner;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ghsh.code.bean.TPic;
import com.ghsh.R;

public class BannerActivity extends FragmentActivity implements OnPageChangeListener {

	private ArrayList<ImageView> dotaViews = new ArrayList<ImageView>();
	private ViewPager viewPager;
	private BannerActivityAdapter adapter;;
	private LinearLayout dotaLinearLayout;
	private int lastPositon;
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.setContentView(R.layout.activity_banner);
		this.initView();
		this.initListner();
		this.initData();
	}

	private void initView() {
		adapter = new BannerActivityAdapter(this,getSupportFragmentManager(),new ArrayList<TPic>());
		viewPager = (ViewPager)this.findViewById(R.id.banner_viewpager);
		viewPager.setAdapter(adapter);
		dotaLinearLayout = (LinearLayout)this.findViewById(R.id.banner_dotaLayout);
	}

	private void initListner() {
		viewPager.setOnPageChangeListener(this);
	}

	private void initData() {
		Intent intent = this.getIntent();
		Bundle bundle = intent.getBundleExtra("picList");
		if (bundle != null && bundle.size() != 0) {
			List<TPic> picList = new ArrayList<TPic>();
			for (String key : bundle.keySet()) {
				TPic pic = (TPic) bundle.getSerializable(key);
				picList.add(pic);
			}
			adapter.setPicList(picList);
			adapter.notifyDataSetChanged();
			for (int i = 0; i < picList.size(); i++) {
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
				params.gravity = Gravity.CENTER_VERTICAL;
				ImageView dotaImageView = this.createDotaView(i == 0);
				dotaLinearLayout.addView(dotaImageView, params);
				dotaViews.add(dotaImageView);
			}
		}
	}

	private ImageView createDotaView(boolean isFouse) {
		ImageView imageView = new ImageView(this);
		imageView.setClickable(true);
		imageView.setPadding(5, 5, 5, 5);
		if (isFouse) {
			imageView.setImageDrawable(getResources().getDrawable(this.getIndicatorFocusedIcon()));
		} else {
			imageView.setImageDrawable(getResources().getDrawable(this.getIndicatorUnfocusedIcon()));
		}
		return imageView;
	}


	@Override
	public void onPageSelected(int position) {
		if (position - 1 >= 0) {
			ImageView pointImage1 = dotaViews.get(position - 1);
			pointImage1.setImageDrawable(getResources().getDrawable(this.getIndicatorUnfocusedIcon()));
		}
		ImageView pointImage2 = dotaViews.get(position);
		pointImage2.setImageDrawable(getResources().getDrawable(this.getIndicatorFocusedIcon()));

		if (position + 1 < dotaViews.size()) {
			ImageView pointImage3 = dotaViews.get(position + 1);
			pointImage3.setImageDrawable(getResources().getDrawable(this.getIndicatorUnfocusedIcon()));
		}
		if (position != lastPositon) {
			View view = viewPager.findViewWithTag(adapter.getTPic(position).getImageUrl());
			if (view instanceof BannerActivityImageView) {
				BannerActivityImageView img = (BannerActivityImageView) view;
				img.resetImage();
			}
			lastPositon = position;
		}
	}
	

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	private int getIndicatorUnfocusedIcon() {
		return R.drawable.banner_indicator_unfocused;
	}
	private int getIndicatorFocusedIcon() {
		return R.drawable.banner_indicator_focused;
	}
}
