
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.code.bean.TExpressinfo;

public class OrderLogisticsListViewAdapter extends AbstractBaseAdapter<TExpressinfo.Info> {

	public OrderLogisticsListViewAdapter(Context context, List<TExpressinfo.Info> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ExpressinfoHolder holder;
		TExpressinfo.Info info=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_order_logistics_listview_item, null);
			holder = new ExpressinfoHolder();
			holder.nameView=(TextView)convertView.findViewById(R.id.logistics_item_name_view);
			holder.timeView=(TextView)convertView.findViewById(R.id.logistics_item_time_view);
			holder.statucView=(ImageView)convertView.findViewById(R.id.logistics_item_status_view);
			convertView.setTag(holder);
		} else {
			holder = (ExpressinfoHolder) convertView.getTag();
		}
		holder.nameView.setText(info.context);
		holder.timeView.setText(info.time);
		if(position==0){
			holder.statucView.setImageResource(R.drawable.logistics_status_enable_icon);
			holder.nameView.setTextColor(context.getResources().getColor(R.color.header_bg_color));
			holder.timeView.setTextColor(context.getResources().getColor(R.color.header_bg_color));
		}else{
			holder.statucView.setImageResource(R.drawable.logistics_status_icon);
			holder.nameView.setTextColor(context.getResources().getColor(R.color.font_color_666666));
			holder.timeView.setTextColor(context.getResources().getColor(R.color.font_color_999999));
		}
		return convertView;
	}
	
	class ExpressinfoHolder {
		ImageView statucView;
		TextView nameView;
		TextView timeView;
	}
	
}
