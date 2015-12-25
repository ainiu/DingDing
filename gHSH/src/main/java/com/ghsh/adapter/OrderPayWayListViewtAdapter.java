
package com.ghsh.adapter;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ghsh.code.bean.TShipping;
import com.ghsh.R;

public class OrderPayWayListViewtAdapter extends AbstractBaseAdapter<TShipping> {
	private Listener listener;
	public OrderPayWayListViewtAdapter(Context context, List<TShipping> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		OrderPayWayHolder holder;
		final TShipping shipping=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_order_payway_listview_item, null);
			holder = new OrderPayWayHolder();
			holder.nameView=(TextView)convertView.findViewById(R.id.shipping_item_name);
			holder.priceView=(TextView)convertView.findViewById(R.id.shipping_item_price);
			holder.checkBox=(CheckBox)convertView.findViewById(R.id.shipping_item_checkBox);
			convertView.setTag(holder);
		} else {
			holder = (OrderPayWayHolder) convertView.getTag();
		}
		holder.nameView.setText(shipping.getShippingName());
//		if(shipping.getFirstPrice().equals("")||new BigDecimal(shipping.getFirstPrice()).doubleValue()==0.0){
//			holder.priceView.setText(R.string.order_payway_price);
//		}else{
//			holder.priceView.setText("￥"+shipping.getFirstPrice());
//		}
		holder.checkBox.setChecked(shipping.isSelected());
		holder.checkBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setSelected(shipping);
				if(listener!=null){
					listener.onCheckBox(shipping);
				}
			}
		});
		return convertView;
	}
	public void setSelected(TShipping shipping){
		for(TShipping p:list){
			p.setSelected(false);
		}
		shipping.setSelected(true);
		this.notifyDataSetChanged();
	}
	/**
	 * 选中
	 * */
	public void setSelectedShippingByID(String shippingID){
		if(shippingID==null||shippingID.equals("")){
			return;
		}
		for(TShipping s:list){
			if(s.getShippingID().equals(shippingID)){
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
		public void onCheckBox(TShipping shipping);
	}
	class OrderPayWayHolder {
		TextView nameView;
		TextView priceView;
		CheckBox checkBox;
	}
}
