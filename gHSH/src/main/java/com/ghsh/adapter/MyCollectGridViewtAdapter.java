package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.code.bean.TCollect;
import com.ghsh.code.volley.DVolley;
import com.ghsh.R;

public class MyCollectGridViewtAdapter extends AbstractBaseAdapter<TCollect> {

	private boolean isEdit = false;

	private CallBackListener listener;
	public MyCollectGridViewtAdapter(Context context, List<TCollect> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		MyCollectHolder holder;
		final TCollect collect = list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_mycollect_gridview_item, null);
			holder = new MyCollectHolder();
			holder.imageLayout = (FrameLayout) convertView.findViewById(R.id.collect_item_image_layout);
			holder.deleteButton = (Button) convertView.findViewById(R.id.collect_item_delete_btn);
			holder.goodsNameView = (TextView) convertView.findViewById(R.id.collect_item_name);
			holder.goodsPriceView = (TextView) convertView.findViewById(R.id.collect_item_price);
			holder.goodsImageView = (ImageView) convertView.findViewById(R.id.collect_item_image);
			holder.numberView = (TextView) convertView.findViewById(R.id.collect_item_number);
			convertView.setTag(holder);
		} else {
			holder = (MyCollectHolder) convertView.getTag();
		}
		holder.goodsNameView.setText(collect.getGoodsName());
		holder.goodsPriceView.setText("￥" + collect.getGoodsPrice());
		holder.numberView.setText(collect.getCollectNumber());
		DVolley.getImage(collect.getGoodsImage(), holder.goodsImageView,R.drawable.default_image);
		if (isEdit) {
			holder.deleteButton.setVisibility(View.VISIBLE);
			Animation adim = AnimationUtils.loadAnimation(context,R.anim.rotate);
			holder.imageLayout.startAnimation(adim);
			holder.deleteButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(listener!=null){
						listener.deleteCollect(collect.getGoodsID());
					}
				}
			});
		} else {
			holder.deleteButton.setVisibility(View.GONE);
			holder.imageLayout.clearAnimation();
		}
		return convertView;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
	public void removeByGoodsID(String goodsID){
		if(goodsID==null){
			return;
		}
		for(TCollect collect:list){
			if(collect.getGoodsID().equals(goodsID)){
				list.remove(collect);
				break;
			}
		}
		this.notifyDataSetChanged();
	}
	class MyCollectHolder {
		FrameLayout imageLayout;
		Button deleteButton;
		TextView goodsNameView;
		TextView goodsPriceView;
		ImageView goodsImageView;
		TextView numberView;
	}
	
	public void addCallBackListener(CallBackListener listener){
		this.listener=listener;
	}
	public interface CallBackListener{
		public void deleteCollect(String goodsID);
	}
}
