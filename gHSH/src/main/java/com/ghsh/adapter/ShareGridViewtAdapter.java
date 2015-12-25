
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.code.bean.TShare;
import com.ghsh.R;

public class ShareGridViewtAdapter extends AbstractBaseAdapter<TShare> {

	public ShareGridViewtAdapter(Context context, List<TShare> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		TGoodsCategoryHolder holder;
		final TShare share=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_share_gridview_item, null);
			holder = new TGoodsCategoryHolder();
			holder.imageView=(ImageView)convertView.findViewById(R.id.item_image);
			holder.nameView=(TextView)convertView.findViewById(R.id.item_name);
			convertView.setTag(holder);
		} else {
			holder = (TGoodsCategoryHolder) convertView.getTag();
		}
		holder.imageView.setImageResource(share.getImageResID());
		holder.nameView.setText(share.getText());
		return convertView;
	}

	class TGoodsCategoryHolder {
		TextView nameView;
		ImageView imageView;
	}
}
