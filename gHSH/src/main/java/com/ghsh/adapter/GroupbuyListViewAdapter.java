
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.code.bean.TGroupbuy;
import com.ghsh.code.volley.DVolley;
import com.ghsh.R;

public class GroupbuyListViewAdapter extends AbstractBaseAdapter<TGroupbuy> {

	public GroupbuyListViewAdapter(Context context, List<TGroupbuy> list) {
		super(context, list);
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		GoodsListViewHolder holder;
		final TGroupbuy groupbuy=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_groupbuylist_listview_item, null);
			holder = new GoodsListViewHolder();
			holder.groupNameView=(TextView)convertView.findViewById(R.id.group_item_name);
			holder.groupDesView=(TextView)convertView.findViewById(R.id.group_item_desc);
			holder.groupPriceView=(TextView)convertView.findViewById(R.id.group_item_price);
			holder.groupSourcePriceView=(TextView)convertView.findViewById(R.id.group_item_source_price);
			holder.groupSaleNumber=(TextView)convertView.findViewById(R.id.group_item_sale_number);
			holder.groupImageView=(ImageView)convertView.findViewById(R.id.group_item_image);
			convertView.setTag(holder);
		} else {
			holder = (GoodsListViewHolder) convertView.getTag();
		}
		holder.groupNameView.setText(groupbuy.getGroupName());
		holder.groupDesView.setText(groupbuy.getGroupDesc());
		holder.groupPriceView.setText("￥"+groupbuy.getGoupPrice());
		holder.groupSourcePriceView.setText("￥"+groupbuy.getSourcePrice());
		holder.groupSourcePriceView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		holder.groupSaleNumber.setText("已售："+groupbuy.getSaleNumber());
		DVolley.getImage(groupbuy.getGroupImage(),holder.groupImageView,R.drawable.default_image);
		return convertView;
	}

	class GoodsListViewHolder {
		TextView groupNameView;
		TextView groupDesView;
		TextView groupPriceView;
		TextView groupSaleNumber;
		TextView groupSourcePriceView;
		ImageView groupImageView;
	}
	
}
