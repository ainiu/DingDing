package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.code.bean.TBankAccount;
import com.ghsh.R;

public class WithdrawListViewtAdapter extends AbstractBaseAdapter<TBankAccount> {


	public WithdrawListViewtAdapter(Context context, List<TBankAccount> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		WithdrawListViewHolder holder;
		final TBankAccount bankAccount = list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_withdraw_listview_item, null);
			holder = new WithdrawListViewHolder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.withdraw_item_image);
			holder.textView = (TextView) convertView.findViewById(R.id.withdraw_item_text);
			convertView.setTag(holder);
		} else {
			holder = (WithdrawListViewHolder) convertView.getTag();
		}
		holder.textView.setText(bankAccount.getName()+"     "+bankAccount.getBankSN());
		holder.imageView.setImageResource(bankAccount.getImageRes());
		return convertView;
	}

	
	class WithdrawListViewHolder {
		ImageView imageView;
		TextView textView;
	}
}
