
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ghsh.code.bean.TPayment;
import com.ghsh.R;

public class UserAccountRechargeListViewtAdapter extends AbstractBaseAdapter<TPayment> {
	public UserAccountRechargeListViewtAdapter(Context context, List<TPayment> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		PaymentWayHolder holder;
		final TPayment payment=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_user_account_recharge_listview_item, null);
			holder = new PaymentWayHolder();
			holder.nameView=(TextView)convertView.findViewById(R.id.paymentway_item_name);
			holder.checkBox=(CheckBox)convertView.findViewById(R.id.paymentway_item_checkBox);
			convertView.setTag(holder);
		} else {
			holder = (PaymentWayHolder) convertView.getTag();
		}
		holder.nameView.setText(payment.getPaymentName());
		holder.checkBox.setChecked(payment.isSelect());
		holder.checkBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UserAccountRechargeListViewtAdapter.this.setSelected(payment);
			}
		});
		return convertView;
	}

	public void setSelected(TPayment payment){
		if(payment==null||payment.equals("")){
			return;
		}
		for(TPayment p:list){
			if(p.getPaymentID().equals(payment.getPaymentID())){
				p.setSelect(true);
			}else{
				p.setSelect(false); 
			}
		}
		this.notifyDataSetChanged();
	}
	
	public TPayment getSelected(){
		for(TPayment p:list){
			if(p.isSelect()){
				return p;
			}
		}
		return null;
	}
	
	class PaymentWayHolder {
		TextView nameView;
		CheckBox checkBox;
	}
}
