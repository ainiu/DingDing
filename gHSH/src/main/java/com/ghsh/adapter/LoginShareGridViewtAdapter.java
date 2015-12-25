
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ghsh.code.bean.TShare;
import com.ghsh.R;

public class LoginShareGridViewtAdapter extends AbstractBaseAdapter<TShare> {

	public LoginShareGridViewtAdapter(Context context, List<TShare> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		TGoodsCategoryHolder holder;
		final TShare share=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_login_share_item, null);
			holder = new TGoodsCategoryHolder();
			holder.imageView=(ImageView)convertView.findViewById(R.id.login_share_item);
			convertView.setTag(holder);
		} else {
			holder = (TGoodsCategoryHolder) convertView.getTag();
		}
		holder.imageView.setImageResource(share.getImageResID());
		return convertView;
	}

	class TGoodsCategoryHolder {
		ImageView imageView;
	}
}
