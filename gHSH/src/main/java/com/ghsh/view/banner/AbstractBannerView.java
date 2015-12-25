package com.ghsh.view.banner;

import java.util.ArrayList;
import java.util.List;

import com.ghsh.R;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
/**
 * 自定义--显示view
 * */
public abstract class AbstractBannerView extends RelativeLayout implements OnPageChangeListener{

	protected Context mContext;
	private ArrayList<ImageView> dotaViews = new ArrayList<ImageView>();
	private ArrayList<View> views = new ArrayList<View>();
	private ViewPager viewPager;
	private AbstractBannerViewAdapter adapter = new AbstractBannerViewAdapter(views);
	private LinearLayout dotaLinearLayout;
	private int mCurrentItem = 0;
	private Runnable viewPagerAction;
	public AbstractBannerView(Context context) {
		super(context);
		this.mContext = context;
		this.initView();
		this.initListner();
	}

	public AbstractBannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.initView();
		this.initListner();
	}

	public AbstractBannerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		this.initView();
		this.initListner();
	}

	private void initView() {
		viewPager = new ViewPager(mContext);
		viewPager.setAdapter(adapter);
		RelativeLayout.LayoutParams viewPagerLinearLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
		this.addView(viewPager, viewPagerLinearLayoutParams);
		dotaLinearLayout = new LinearLayout(this.mContext);
		dotaLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		RelativeLayout.LayoutParams dotaLinearLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		dotaLinearLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		dotaLinearLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		dotaLinearLayoutParams.setMargins(0, 0, 0, 15);
		this.addView(dotaLinearLayout, dotaLinearLayoutParams);
	}

	private void initListner() {
		viewPager.setOnPageChangeListener(this);
		viewPagerAction = new Runnable() {
			@Override
			public void run() {
				if (views.size() != 0) {
					if(mCurrentItem>=(views.size() - 1)){
						mCurrentItem = 0;
					}else{
						mCurrentItem++;
					}
					viewPager.setCurrentItem(mCurrentItem);
				}
				viewPager.postDelayed(viewPagerAction, 3000);
			}
		};
	}
	
	public void setAutoPay(boolean isAutoPay){
		if(isAutoPay){
			viewPager.postDelayed(viewPagerAction, 3000);
		}
	}
	
	public void createView(List<View> viewList) {
		if (viewList == null || viewList.size() == 0) {
			return;
		}
		this.reset();
		for (int i = 0; i < viewList.size(); i++) {
			final View view = viewList.get(i);
			views.add(view); 
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.CENTER_VERTICAL;
			ImageView dotaImageView = this.createDotaView(i == 0);
			dotaLinearLayout.addView(dotaImageView, params);
			dotaViews.add(dotaImageView);
		}
		adapter.notifyDataSetChanged();
		mCurrentItem=0;
		viewPager.setCurrentItem(mCurrentItem);
	}

	protected void reset(){
		views.clear();
		dotaLinearLayout.removeAllViews();
		dotaViews.clear();
	}
	private ImageView createDotaView(boolean isFouse) {
		ImageView imageView = new ImageView(this.mContext);
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
		if(dotaViews.size()==0){
			return;
		}
		int count=dotaViews.size();
		for(int i=0;i<count;i++){
			ImageView pointImage=dotaViews.get(i);
			if(i==position){
				pointImage.setImageDrawable(getResources().getDrawable(this.getIndicatorFocusedIcon()));
			}else{
				pointImage.setImageDrawable(getResources().getDrawable(this.getIndicatorUnfocusedIcon()));
			}
		}
		this.mCurrentItem=position;
	}

	/**获取没有聚焦的ICON*/
	protected abstract int getIndicatorUnfocusedIcon();
	/**获取聚焦的ICON*/
	protected abstract int getIndicatorFocusedIcon();
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}
}
