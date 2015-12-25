
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ghsh.code.bean.TMessage;
import com.ghsh.view.util.ViewUtils;
import com.ghsh.R;

public class MessageListViewAdapter extends AbstractBaseAdapter<TMessage> {

	public MessageListViewAdapter(Context context, List<TMessage> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final MessageListViewHolder holder;
		final TMessage message=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_message_listview_item, null);
			holder = new MessageListViewHolder();
			holder.titleView=(TextView)convertView.findViewById(R.id.meaasge_item_title);
			holder.timeView=(TextView)convertView.findViewById(R.id.meaasge_item_time);
			holder.descShortView=(TextView)convertView.findViewById(R.id.meaasge_item_desc_short);
			holder.descLongView=(TextView)convertView.findViewById(R.id.meaasge_item_desc_long);
			holder.iconView=(Button)convertView.findViewById(R.id.meaasge_item_icon);
			convertView.setTag(holder);
		} else {
			holder = (MessageListViewHolder) convertView.getTag();
		}
		holder.titleView.setText(message.getTitle());
		holder.timeView.setText(message.getTime());
		holder.descShortView.setText(message.getDesc());
		holder.descLongView.setText(message.getDesc());
		holder.iconView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(holder.descShortView.getVisibility()==View.VISIBLE){
					holder.descLongView.setVisibility(View.VISIBLE);
					holder.descShortView.setVisibility(View.GONE);
					ViewUtils.setButtonDrawables(context, holder.iconView, R.drawable.measge_up_icon, 3);
				}else{
					holder.descLongView.setVisibility(View.GONE);
					holder.descShortView.setVisibility(View.VISIBLE);
					ViewUtils.setButtonDrawables(context, holder.iconView, R.drawable.measge_down_icon, 3);
				}
			}
		});
		return convertView;
	}

	public void deleteMsgByID(String msgID){
		if(msgID==null||msgID.equals("")){
			return;
		}
		for(TMessage key:list){
			if(key.getMeaasgeID().equals(msgID)){
				list.remove(key);
				break;
			}
		}
		this.notifyDataSetChanged();
	}
	class MessageListViewHolder {
		TextView titleView;
		TextView timeView;
		TextView descShortView,descLongView;
		Button iconView;
	}
	
}
