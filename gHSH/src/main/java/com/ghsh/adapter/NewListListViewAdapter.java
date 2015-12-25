
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.code.bean.TNews;
import com.ghsh.code.volley.DVolley;
import com.ghsh.R;

public class NewListListViewAdapter extends AbstractBaseAdapter<TNews> {

	public NewListListViewAdapter(Context context, List<TNews> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final MessageListViewHolder holder;
		final TNews news=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_news_list_listview_item, null);
			holder = new MessageListViewHolder();
			holder.imageView=(ImageView)convertView.findViewById(R.id.new_item_image);
			holder.titleView=(TextView)convertView.findViewById(R.id.news_item_title);
			holder.descView=(TextView)convertView.findViewById(R.id.news_item_desc);
			holder.timeView=(TextView)convertView.findViewById(R.id.news_item_time);
			convertView.setTag(holder);
		} else {
			holder = (MessageListViewHolder) convertView.getTag();
		}
		holder.titleView.setText(news.getTitle());
		holder.descView.setText(news.getSubTitle());
		holder.timeView.setText(news.getAddTime());
		DVolley.getImage(news.getImageUrl(),holder.imageView,R.drawable.default_image);
		return convertView;
	}

	class MessageListViewHolder {
		ImageView imageView;
		TextView titleView;
		TextView timeView;
		TextView descView;
	}
	
}
