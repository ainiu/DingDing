
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.code.bean.TCoupon;
import com.ghsh.code.bean.TMyBonus;

public class OrderBonusListViewtAdapter extends AbstractBaseAdapter<TMyBonus> {
	private Listener listener;
	public OrderBonusListViewtAdapter(Context context, List<TMyBonus> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		OrderPayWayHolder holder;
		final TMyBonus myBonus=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_order_bonus_listview_item, null);
			holder = new OrderPayWayHolder();
			holder.nameView=(TextView)convertView.findViewById(R.id.bonus_item_name);
			holder.priceView=(TextView)convertView.findViewById(R.id.bonus_item_price);
			holder.checkBox=(CheckBox)convertView.findViewById(R.id.bonus_item_checkBox);
			convertView.setTag(holder);
		} else {
			holder = (OrderPayWayHolder) convertView.getTag();
		}
		if(myBonus.getTypeMoney()==null||myBonus.getTypeMoney().equals("")){
			myBonus.setTypeMoney("0.00");
		}
		holder.nameView.setText(myBonus.getTypeName());
		holder.priceView.setText("￥"+myBonus.getTypeMoney());
		holder.checkBox.setChecked(myBonus.isSelected());
		holder.checkBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setSelected(myBonus);
				if(listener!=null){
					listener.onCheckBox(myBonus);
				}
			}
		});
		return convertView;
	}
	public void setSelected(TMyBonus myBonus){
		for(TMyBonus p:list){
			p.setSelected(false);
		}
		myBonus.setSelected(true);
		this.notifyDataSetChanged();
	}
	/**
	 * 选中
	 * */
	public void setSelectedBonusByID(String bonusID){
		if(bonusID==null||bonusID.equals("")){
			return;
		}
		for(TMyBonus s:list){
			if(s.getBonusID().equals(bonusID)){
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
		public void onCheckBox(TMyBonus myBonus);
	}
	class OrderPayWayHolder {
		TextView nameView;
		TextView priceView;
		CheckBox checkBox;
	}
}
