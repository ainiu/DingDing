
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ghsh.Constants;
import com.ghsh.code.bean.TMyCoupon;
import com.ghsh.R;

public class MyCouponGridViewtAdapter extends AbstractBaseAdapter<TMyCoupon> {

	public MyCouponGridViewtAdapter(Context context, List<TMyCoupon> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		MyCouponHolder holder;
		final TMyCoupon coupon=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_mycoupon_gridview_item, null);
			holder = new MyCouponHolder();
			holder.statusView=(TextView)convertView.findViewById(R.id.mycopount_item_status);
			holder.couponvalueView=(TextView)convertView.findViewById(R.id.mycopount_item_couponvalue);
			holder.unitView=(TextView)convertView.findViewById(R.id.mycopount_item_couponvalue_unit);
			holder.nameView=(TextView)convertView.findViewById(R.id.mycopount_item_name);
			holder.descView=(TextView)convertView.findViewById(R.id.mycopount_item_desc);
			holder.timeView=(TextView)convertView.findViewById(R.id.mycopount_item_time);
			convertView.setTag(holder);
		} else {
			holder = (MyCouponHolder) convertView.getTag();
		}
		
		holder.couponvalueView.setText(coupon.getCouponValue());
		holder.statusView.setText(Constants.COUPON_STATUS_1_MAP().get(coupon.getStatus()));
		holder.nameView.setText(coupon.getMinAmount());
		holder.descView.setText(coupon.getSendType());
		holder.timeView.setText(coupon.getTime());
		String status=coupon.getStatus();
		if(status.equals(Constants.COUPON_STATUS_2)||status.equals(Constants.COUPON_STATUS_3)||status.equals(Constants.COUPON_STATUS_4)||status.equals(Constants.COUPON_STATUS_6)){
			int color=context.getResources().getColor(R.color.font_color_999999);
			holder.statusView.setBackgroundColor(color);
			holder.couponvalueView.setTextColor(color);
			holder.unitView.setTextColor(color);
			holder.timeView.setBackgroundColor(color);
		}else{
			int color=context.getResources().getColor(R.color.coupont_text_color);
			holder.statusView.setBackgroundColor(color);
			holder.couponvalueView.setTextColor(color);
			holder.unitView.setTextColor(color);
			holder.timeView.setBackgroundColor(color);
		}
		return convertView;
	}

	public void receiveCoupon(String couponID){
		for(TMyCoupon coupon:list){
			if(coupon.getCouponID().equals(couponID)){
				coupon.setStatus(Constants.COUPON_STATUS_2);
			}
		}
		this.notifyDataSetChanged();
	}
	
	class MyCouponHolder {
		TextView statusView;
		TextView couponvalueView,unitView;
		
		TextView nameView;
		TextView descView;
		TextView timeView;
	}
	
}
