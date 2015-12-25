
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ghsh.Constants;
import com.ghsh.R;
import com.ghsh.code.bean.TMyBonus;
import com.ghsh.code.bean.TMyCoupon;

public class MyBonusGridViewtAdapter extends AbstractBaseAdapter<TMyBonus> {

	public MyBonusGridViewtAdapter(Context context, List<TMyBonus> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		MyCouponHolder holder;
		final TMyBonus bonus=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_mybonus_gridview_item, null);
			holder = new MyCouponHolder();
			holder.statusView=(TextView)convertView.findViewById(R.id.mybonus_item_status);
			holder.moneyView=(TextView)convertView.findViewById(R.id.mybonus_item_money);
			holder.unitView=(TextView)convertView.findViewById(R.id.mybonus_item_couponvalue_unit);
			holder.nameView=(TextView)convertView.findViewById(R.id.mybonus_item_name);
			holder.descView=(TextView)convertView.findViewById(R.id.mybonus_item_desc);
			holder.timeView=(TextView)convertView.findViewById(R.id.mybonus_item_time);
			convertView.setTag(holder);
		} else {
			holder = (MyCouponHolder) convertView.getTag();
		}
		holder.statusView.setText(Constants.BONUS_STATUS_MAP().get(bonus.getStatus()));
		
		holder.moneyView.setText(bonus.getTypeMoney());
		
		holder.nameView.setText(bonus.getMinGoodsAmount());
		holder.descView.setText(bonus.getTypeName());
		holder.timeView.setText(bonus.getTime());
		String status=bonus.getStatus();
		if(!status.equals(Constants.BONUS_STATUS_1)){
			int color=context.getResources().getColor(R.color.font_color_999999);
			holder.statusView.setBackgroundColor(color);
			holder.moneyView.setTextColor(color);
			holder.unitView.setTextColor(color);
			holder.timeView.setBackgroundColor(color);
		}else{
			int color=context.getResources().getColor(R.color.coupont_text_color);
			holder.statusView.setBackgroundColor(color);
			holder.moneyView.setTextColor(color);
			holder.unitView.setTextColor(color);
			holder.timeView.setBackgroundColor(color);
		}
		return convertView;
	}

	public void receiveCoupon(String couponID){
//		for(TMyCoupon coupon:list){
//			if(coupon.getCouponID().equals(couponID)){
//				coupon.setStatus(Constants.COUPON_STATUS_2);
//			}
//		}
//		this.notifyDataSetChanged();
	}
	
	class MyCouponHolder {
		TextView statusView;
		TextView moneyView,unitView;
		
		TextView nameView;
		TextView descView;
		TextView timeView;
	}
	
}
