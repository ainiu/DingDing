
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ghsh.code.bean.TCoupon;
import com.ghsh.R;

public class OrderCouponListViewtAdapter extends AbstractBaseAdapter<TCoupon> {
	private Listener listener;
	public OrderCouponListViewtAdapter(Context context, List<TCoupon> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		OrderPayWayHolder holder;
		final TCoupon coupon=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_order_coupon_listview_item, null);
			holder = new OrderPayWayHolder();
			holder.nameView=(TextView)convertView.findViewById(R.id.coupon_item_name);
			holder.priceView=(TextView)convertView.findViewById(R.id.coupon_item_price);
			holder.checkBox=(CheckBox)convertView.findViewById(R.id.coupon_item_checkBox);
			convertView.setTag(holder);
		} else {
			holder = (OrderPayWayHolder) convertView.getTag();
		}
		if(coupon.getPrice()==null||coupon.getPrice().equals("")){
			coupon.setPrice("0.00");
		}
		holder.nameView.setText(coupon.getName());
		holder.priceView.setText("￥"+coupon.getPrice());
		holder.checkBox.setChecked(coupon.isSelected());
		holder.checkBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setSelected(coupon);
				if(listener!=null){
					listener.onCheckBox(coupon);
				}
			}
		});
		return convertView;
	}
	public void setSelected(TCoupon coupon){
		for(TCoupon p:list){
			p.setSelected(false);
		}
		coupon.setSelected(true);
		this.notifyDataSetChanged();
	}
	/**
	 * 选中
	 * */
	public void setSelectedCouponByID(String couponID){
		if(couponID==null||couponID.equals("")){
			return;
		}
		for(TCoupon s:list){
			if(s.getCouponID().equals(couponID)){
				s.setSelected(true);
			}else{
				s.setSelected(false); 
			}
		}
		this.notifyDataSetChanged();
	}
	public void addListener(Listener listener){
		this.listener=listener;
	}
	public static interface Listener{
		public void onCheckBox(TCoupon coupon);
	}
	class OrderPayWayHolder {
		TextView nameView;
		TextView priceView;
		CheckBox checkBox;
	}
}
