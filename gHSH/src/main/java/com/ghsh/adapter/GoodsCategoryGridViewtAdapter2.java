
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.code.bean.TGoodsCategory;
import com.ghsh.code.volley.DVolley;
import com.ghsh.R;

public class GoodsCategoryGridViewtAdapter2 extends AbstractBaseAdapter<TGoodsCategory> {

	public GoodsCategoryGridViewtAdapter2(Context context, List<TGoodsCategory> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		TGoodsCategoryHolder holder;
		final TGoodsCategory category=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_goodscategory_gridview_item2, null);
			holder = new TGoodsCategoryHolder();
			holder.nameView=(TextView)convertView.findViewById(R.id.goodscategory_item_name);
			holder.imageView=(ImageView)convertView.findViewById(R.id.goodscategory_item_image);
			convertView.setTag(holder);
		} else {
			holder = (TGoodsCategoryHolder) convertView.getTag();
		}
		holder.nameView.setText(category.getName());
		DVolley.getImage(category.getImagePath(),holder.imageView,R.drawable.default_image);
		return convertView;
	}

	class TGoodsCategoryHolder {
		TextView nameView;
		ImageView imageView;
	}
}
