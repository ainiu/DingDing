
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ghsh.code.bean.TComment;
import com.ghsh.R;

public class GoodsDetailsEvluateListViewtAdapter extends AbstractBaseAdapter<TComment> {
	private Context context;
	public GoodsDetailsEvluateListViewtAdapter(Context context, List<TComment> list) {
		super(context, list);
		this.context = context;
		System.out.println(context);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		GoodsDetailsEvluateHolder holder;
		final TComment comment=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_goodsdetails_evaluate_listview_item, null);
			holder = new GoodsDetailsEvluateHolder();
			holder.userNameView=(TextView)convertView.findViewById(R.id.comment_username);
			holder.addTimeView=(TextView)convertView.findViewById(R.id.comment_addtime);
			holder.commentView=(TextView)convertView.findViewById(R.id.comment_comment);
			holder.repleContentView=(TextView)convertView.findViewById(R.id.comment_reple_content);
			holder.scoreView=(RatingBar)convertView.findViewById(R.id.comment_score);
			convertView.setTag(holder);
		} else {
			holder = (GoodsDetailsEvluateHolder) convertView.getTag();
		}
		holder.userNameView.setText(comment.getUserName());
		holder.addTimeView.setText(comment.getAddTime());
		holder.commentView.setText(comment.getComment());
		holder.repleContentView.setText(comment.getRepleContent());
		if(comment.getEvaluation()==1){
			holder.scoreView.setRating(2);
		}else if(comment.getEvaluation()==2){
			holder.scoreView.setRating(3);
		}else{
			holder.scoreView.setRating(5);
		}
		return convertView;
	}

	class GoodsDetailsEvluateHolder {
		TextView userNameView;
		TextView addTimeView;
		TextView commentView;
		TextView repleContentView;
		RatingBar scoreView;
	}
}
