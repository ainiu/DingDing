package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.code.bean.TWithdrawRecode;

public class UserAccountRecordListViewtAdapter extends AbstractBaseAdapter<TWithdrawRecode> {

	public UserAccountRecordListViewtAdapter(Context context, List<TWithdrawRecode> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		WithdrawRecordHolder holder;
		final TWithdrawRecode withdrawRecode = list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_user_account_record_listview_item, null);
			holder = new WithdrawRecordHolder();
			holder.titleView = (TextView) convertView.findViewById(R.id.withdraw_record_item_title);
			holder.timeView = (TextView) convertView.findViewById(R.id.withdraw_record_item_time);
			holder.moneyView = (TextView) convertView.findViewById(R.id.withdraw_record_item_money);
			holder.statusView = (TextView) convertView.findViewById(R.id.withdraw_record_item_status);
			convertView.setTag(holder);
		} else {
			holder = (WithdrawRecordHolder) convertView.getTag();
		}
		holder.titleView.setText(withdrawRecode.getTitle());
		holder.moneyView.setText(withdrawRecode.getMoney());
		holder.timeView.setText(withdrawRecode.getAddTime());
		holder.statusView.setText(withdrawRecode.getStatus());
		return convertView;
	}

	
	class WithdrawRecordHolder {
		TextView titleView;
		TextView timeView;
		
		TextView moneyView;
		TextView statusView;
	}
}
