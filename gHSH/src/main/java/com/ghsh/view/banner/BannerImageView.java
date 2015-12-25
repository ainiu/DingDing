package com.ghsh.view.banner;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.ghsh.code.bean.TPic;
import com.ghsh.code.volley.DVolley;
import com.ghsh.R;
/**
 * 显示图片的Banner
 * */
public class BannerImageView extends AbstractBannerView {
	
	private List<TPic> picList;
	private BannerImageView.Listener listener;
	public BannerImageView(Context context) {
		super(context);
	}

	public BannerImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BannerImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected int getIndicatorUnfocusedIcon() {
		return R.drawable.banner_indicator_unfocused;
	}

	@Override
	protected int getIndicatorFocusedIcon() {
		return R.drawable.banner_indicator_focused;
	}
	
	public void createPicView(List<TPic> picList) {
		this.picList=picList;
		if (picList == null || picList.size() == 0) {
			return;
		}
		List<View> viewList=new ArrayList<View>();
		for (int i = 0; i < picList.size(); i++) {
			final TPic bean = picList.get(i);
			ImageView imageView = new ImageView(mContext);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			if(bean.getImageRes()!=0){
				imageView.setImageResource(bean.getImageRes());
			}else{
				DVolley.getImage(bean.getImageUrl(),imageView,R.drawable.default_image);
			}
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(listener!=null){
						listener.OnClickPicView(bean);
					}
				}
			});
			viewList.add(imageView);
		}
		super.createView(viewList);
	}
	
	public void addListener(BannerImageView.Listener listener){
		this.listener=listener;
	}
	public static interface Listener{
		public void OnClickPicView(TPic pic);
	}
	public List<TPic> getPicList() {
		return picList;
	}
}
