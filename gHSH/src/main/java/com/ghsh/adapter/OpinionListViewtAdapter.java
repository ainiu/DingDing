
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.code.bean.TFeedback;

public class OpinionListViewtAdapter extends AbstractBaseAdapter<TFeedback> {
	private Context context;
	public OpinionListViewtAdapter(Context context, List<TFeedback> list) {
		super(context, list);
		this.context = context;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		GoodsDetailsConsultHolder holder;
		final TFeedback feedback=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_opinion_listview_item, null);
			holder = new GoodsDetailsConsultHolder();
			holder.titleView=(TextView)convertView.findViewById(R.id.opinion_title);
			holder.usernameView=(TextView)convertView.findViewById(R.id.opinion_username);
			holder.addtimeView=(TextView)convertView.findViewById(R.id.opinion_addtime);
			holder.questionContentView=(TextView)convertView.findViewById(R.id.opinion_questionContent);
			holder.replyContentView=(TextView)convertView.findViewById(R.id.opinion_replyContent);
			convertView.setTag(holder);
		} else {
			holder = (GoodsDetailsConsultHolder) convertView.getTag();
		}
		holder.titleView.setText(feedback.getMsgTitle());
		holder.usernameView.setText(feedback.getUserName());
		holder.questionContentView.setText(feedback.getMsgContent());
		holder.addtimeView.setText(feedback.getMsgTime());
		List<TFeedback> itemList=feedback.getFeedbackList();
		if(itemList!=null&&itemList.size()!=0){
			for (TFeedback fb : itemList) {
				holder.replyContentView.setText(fb.getMsgContent());
			}
		}
		return convertView;
	}

	class GoodsDetailsConsultHolder {
		TextView usernameView;
		TextView addtimeView;
		TextView titleView;
		TextView questionContentView;
		TextView replyContentView;
	}
}
