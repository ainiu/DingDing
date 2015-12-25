
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.code.bean.TNewsCategory;
import com.ghsh.code.volley.DVolley;
import com.ghsh.R;

public class NewHomeGridViewAdapter extends AbstractBaseAdapter<TNewsCategory> {

	public NewHomeGridViewAdapter(Context context, List<TNewsCategory> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final NewsHolder holder;
		final TNewsCategory newsCategory=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_news_home_gridview_item, null);
			holder = new NewsHolder();
			holder.imageView=(ImageView)convertView.findViewById(R.id.news_item_image);
			holder.titleView=(TextView)convertView.findViewById(R.id.news_item_title);
			convertView.setTag(holder);
		} else {
			holder = (NewsHolder) convertView.getTag();
		}
		holder.titleView.setText(newsCategory.getName());
		DVolley.getImage(newsCategory.getImageUrl(),holder.imageView,R.drawable.default_image);
		return convertView;
	}

	class NewsHolder {
		ImageView imageView;
		TextView titleView;
	}
}
