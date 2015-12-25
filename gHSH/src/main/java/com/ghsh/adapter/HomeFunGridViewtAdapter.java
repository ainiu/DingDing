package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.home.HomeFunsBean;
import com.ghsh.code.volley.DVolley;

public class HomeFunGridViewtAdapter extends AbstractBaseAdapter<HomeFunsBean> {

	public HomeFunGridViewtAdapter(Context context, List<HomeFunsBean> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		HomeFunHolder holder;
		final HomeFunsBean fun = list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_home_funs_item, null);
			holder = new HomeFunHolder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.funs_item_image_view);
			holder.textView = (TextView) convertView.findViewById(R.id.funs_item_text_view);
			convertView.setTag(holder);
		} else {
			holder = (HomeFunHolder) convertView.getTag();
		}
		holder.textView.setText(fun.text);
		DVolley.getImage(fun.imageUrl, holder.imageView,R.drawable.default_image);
		
		return convertView;
	}

	class HomeFunHolder {
		ImageView imageView;
		TextView textView;
	}
}
