package com.ghsh.view.banner;

import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ghsh.Constants;
import com.ghsh.code.bean.TPic;
import com.ghsh.code.volley.DVolley;
import com.ghsh.util.BroadcastUtil;
import com.ghsh.util.ImageUtil;
import com.ghsh.view.DToastView;
import com.ghsh.view.util.DAlertUtil;
import com.ghsh.R;

public class BannerActivityAdapter extends FragmentStatePagerAdapter{

	private List<TPic> list;
	private Context context;
	public BannerActivityAdapter(Context context,FragmentManager fm,List<TPic> list) {
		super(fm);
		this.list=list;
		this.context=context;
	}

	@Override
	public Fragment getItem(int index) {
		EnlargeFragment fragment = new EnlargeFragment();
		Bundle bundle = new Bundle();
		bundle.putString("imageUrl", list.get(index).getImageUrl());
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public int getCount() {
		return list.size();
	}
	
	public void setPicList(List<TPic> list){
		this.list=list;
	}
	public TPic getTPic(int index){
		return list.get(index);
	}
	/**
	 * 销毁position位置的界面
	 */
	@Override
	public void destroyItem(View view, int position, Object arg2) {
		((ViewPager) view).removeViewAt(position);
	}
	public static class EnlargeFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			final BannerActivityImageView  img = new BannerActivityImageView(getActivity());
			String imageUrl = getArguments().getString("imageUrl");
			DVolley.getImage(imageUrl,img,R.drawable.default_image);
			img.setTag(imageUrl);
			img.setOnItemLongClickListener(new BannerActivityImageView.OnItemLongClickListener() {
				@Override
				public void onItemLongClick(View view) {
					DAlertUtil.alertOKAndCel(getActivity(), R.string.goods_details_image_message_save, new android.content.DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
							img.setDrawingCacheEnabled(true);
							Bitmap bitmap=img.getDrawingCache();
							String filePath=Constants.getSDPicturesPath();
							if(bitmap!=null&&filePath!=null){
								filePath=filePath+System.currentTimeMillis()+".jpg";
								ImageUtil.saveBitmapToLocalFile(filePath, bitmap);
								DToastView.makeText(getActivity(), "图片保存成功\n"+filePath, Toast.LENGTH_SHORT).show();
								BroadcastUtil.sendAddPicBroadcast(getActivity(), filePath);
							}else{
								DToastView.makeText(getActivity(), "图片保存失败", Toast.LENGTH_SHORT).show();
							}
							img.setDrawingCacheEnabled(false);
						}
					}).show();
				}
			});
			return img;
		}
	}
}
