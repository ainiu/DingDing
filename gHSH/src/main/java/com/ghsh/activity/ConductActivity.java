package com.ghsh.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.adapter.ConductAdapter;
import com.ghsh.util.SettingUtils;
import com.ghsh.R;

/**
 * 引导页
 * */
public class ConductActivity extends BaseActivity  implements OnPageChangeListener,OnTouchListener,OnGestureListener{

	private ArrayList<View> bannerViews = new ArrayList<View>();
	private ArrayList<ImageView> dotaViews = new ArrayList<ImageView>();
	private ViewPager viewPager;
	private ConductAdapter adapter = new ConductAdapter(bannerViews);
	private LinearLayout dotaLinearLayout;
	private int mCurrentItem=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boolean isConduct=SettingUtils.readeConduct(this);
		if(isConduct){
			this.stareMainActivity();
			return;
		}
		setContentView(R.layout.activity_conduct);
		this.initView();
		this.initListener();
		this.initData();
	}

	private void initView() {
		viewPager = (ViewPager) this.findViewById(R.id.conduct_viewpager);
		dotaLinearLayout = (LinearLayout) this.findViewById(R.id.conduct_dotaLayout);
		viewPager.setAdapter(adapter);
	}
	
	private void initListener(){
		viewPager.setOnPageChangeListener(this);
		viewPager.setOnTouchListener(this);
	}
	
	private void initData(){
		bannerViews.add(this.createBannerView(R.drawable.conduct_icon1,false));
		bannerViews.add(this.createBannerView(R.drawable.conduct_icon2,false));
		bannerViews.add(this.createBannerView(R.drawable.conduct_icon3,true));
		adapter.notifyDataSetChanged();
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_VERTICAL;
		
		ImageView dotaImageView1 = this.createDotaView(true);
		ImageView dotaImageView2 = this.createDotaView(false);
		ImageView dotaImageView3 = this.createDotaView(false);
		dotaLinearLayout.addView(dotaImageView1, params);
		dotaLinearLayout.addView(dotaImageView2, params);
		dotaLinearLayout.addView(dotaImageView3, params);
		dotaViews.add(dotaImageView1);
		dotaViews.add(dotaImageView2);
		dotaViews.add(dotaImageView3);
	}

	private View createBannerView(int resID,boolean isStart) {
		RelativeLayout relativeLayout = new RelativeLayout(this);
		relativeLayout.setBackgroundResource(resID);
		if(isStart){
			int h=(int)this.getResources().getDimension(R.dimen.height_conduct_button_bottomMargin);
			View view=this.getView(R.layout.activity_conduct_button);
			Button button=(Button)view.findViewById(R.id.conduct_button);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ConductActivity.this.stareMainActivity();
				}
			});
			RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
			buttonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
			buttonParams.setMargins(0, 0, 0, h);
			relativeLayout.addView(view, buttonParams);
		}
		return relativeLayout;
	}
	
	private ImageView createDotaView(boolean isFouse) {
		ImageView imageView = new ImageView(this);
		imageView.setClickable(true);
		imageView.setPadding(5, 5, 5, 5);
		if (isFouse) {
			imageView.setImageDrawable(getResources().getDrawable(R.drawable.conduct_indicator_focused));
		} else {
			imageView.setImageDrawable(getResources().getDrawable(R.drawable.conduct_indicator_unfocused));
		}
		return imageView;
	}

	@Override
	public void onPageSelected(int position) {
		if(dotaViews.size()==0){
			return;
		}
		int count=dotaViews.size();
		for(int i=0;i<count;i++){
			ImageView pointImage=dotaViews.get(i);
			if(i==position){
				pointImage.setImageDrawable(getResources().getDrawable(R.drawable.conduct_indicator_focused));
			}else{
				pointImage.setImageDrawable(getResources().getDrawable(R.drawable.conduct_indicator_unfocused));
			}
		}
		this.mCurrentItem=position+1;
	}

	private GestureDetector mygesture = new GestureDetector(this); 
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mygesture.onTouchEvent(event);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {		
		if (e1.getX() - e2.getX() > 120) {      
			if(mCurrentItem == adapter.getCount()) {
				this.stareMainActivity();
			}
        }
		return false;
	}
	
	private void stareMainActivity(){
		SettingUtils.writeConduct(this, true);
		Intent intent = new Intent(ConductActivity.this, MainTabActivity.class);
		ConductActivity.this.startActivity(intent);
		ConductActivity.this.finish();
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,float distanceY) {
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
	}
}
