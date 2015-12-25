
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ghsh.code.bean.TConsult;
import com.ghsh.R;

public class GoodsDetailsConsultListViewtAdapter extends AbstractBaseAdapter<TConsult> {
	private Listener listener;
	private Context context;
	public GoodsDetailsConsultListViewtAdapter(Context context, List<TConsult> list) {
		super(context, list);
		this.context = context;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		GoodsDetailsConsultHolder holder;
		final TConsult consult=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_goodsdetails_consult_listview_item, null);
			holder = new GoodsDetailsConsultHolder();
			holder.usernameView=(TextView)convertView.findViewById(R.id.consult_username);
			holder.addtimeView=(TextView)convertView.findViewById(R.id.consult_addtime);
			holder.questionContentView=(TextView)convertView.findViewById(R.id.consult_questionContent);
			holder.replyContentView=(TextView)convertView.findViewById(R.id.consult_replyContent);
			holder.satisfiedView=(TextView)convertView.findViewById(R.id.consult_satisfied);
			holder.satisfiedNotView=(TextView)convertView.findViewById(R.id.consult_satisfied_not);
			convertView.setTag(holder);
		} else {
			holder = (GoodsDetailsConsultHolder) convertView.getTag();
		}
		if(consult.isAnonymous()||consult.getUserName().equals("")){
			holder.usernameView.setText(R.string.anonymous);
		}else{
			holder.usernameView.setText(consult.getUserName());
		}
		holder.questionContentView.setText(consult.getQuestionContent());
		holder.addtimeView.setText(consult.getAddTime());
		holder.replyContentView.setText(consult.getReplyContent());
		holder.satisfiedView.setText(context.getResources().getString(R.string.goods_details_consult_satisfied)+"("+consult.getSatisfied()+")");
		holder.satisfiedNotView.setText(context.getResources().getString(R.string.goods_details_consult_satisfied_not)+"("+consult.getSatisfiedNot()+")");
		
		holder.satisfiedView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.onSatisfiedClick(consult.getConsultID(),true);
				}
			}
		});
		holder.satisfiedNotView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.onSatisfiedClick(consult.getConsultID(),false);
				}
			}
		});
		return convertView;
	}

	public void modifySatisfied(String consultID,boolean flag){
		if(consultID==null || consultID.equals("")){
			return ;
		}
		for(TConsult consult:list){
			if(consult.getConsultID().equals(consultID)){
				if(flag){
					consult.setSatisfied(consult.getSatisfied()+1);
				}else{
					consult.setSatisfiedNot(consult.getSatisfiedNot()+1);
				}
				this.notifyDataSetChanged();
				return;
			}
		}
	}
	public void addListener(Listener listener){
		this.listener=listener;
	}
	class GoodsDetailsConsultHolder {
		TextView usernameView;
		TextView addtimeView;
		TextView questionContentView;
		TextView replyContentView;
		TextView satisfiedView,satisfiedNotView;
	}
	
	public interface Listener{
		//满意
		public void onSatisfiedClick(String consultID,boolean flag);
	}
}
