package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.entity.EachBaen;
import com.baidu.dingding.until.LogUtil;

import java.util.List;

public class SousuoAdapter extends AbstractBaseAdapter {

	public SousuoAdapter(Context context, List beanList) {
		super(context, beanList);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		EachBaen eachBaen = (EachBaen) beanList.get(position);
		if(eachBaen==null){
			return convertView;
		}
		LogUtil.i("店铺适配器设置值","");
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
				convertView = mInflater
						.inflate(R.layout.souye_each_list_item, null);
			viewHolder.webView = (WebView) convertView
					.findViewById(R.id.soushuo_name);
			viewHolder.networkImageView = (NetworkImageView) convertView
					.findViewById(R.id.soushuo_img);
			viewHolder.primalPrice = (TextView) convertView
					.findViewById(R.id.soushuo_primalPrice);
			viewHolder.originPlace = (TextView) convertView
					.findViewById(R.id.soushuo_originPlace);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.networkImageView
				.setDefaultImageResId(R.drawable.wang_18);
		viewHolder.networkImageView.setErrorImageResId(R.drawable.ding_42);
		viewHolder.networkImageView.setImageUrl(eachBaen.getLogPath(),
				imageLoader);
		viewHolder.webView.loadDataWithBaseURL(null, eachBaen.getName(),
				"text/html", "UTF-8", null);
		viewHolder.name.setText(eachBaen.getName());
		viewHolder.primalPrice.setText("￥"+eachBaen.getPrimalPrice());
		viewHolder.originPlace.setText(eachBaen.getOriginPlace());
		return convertView;
	}

	class ViewHolder {
		TextView primalPrice, originPlace,name;
		WebView webView;
		NetworkImageView networkImageView;
	}
}
