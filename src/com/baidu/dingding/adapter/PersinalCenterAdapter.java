package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.dingding.R;
import com.baidu.dingding.entity.PersinalCenterEntity;

import java.util.List;

public class PersinalCenterAdapter extends BaseAdapter{
	private Context context;
	private List<PersinalCenterEntity> list;
	private LayoutInflater inflater;

	public PersinalCenterAdapter(Context context,List<PersinalCenterEntity>list){
		this.context=context;
		this.list=list;
		this.inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	//��ؼ���ֵ
	@Override
	public View getView(int position, View convertView, ViewGroup view) {
		// TODO Auto-generated method stub
		ViewHold hold=null;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.payment_list, null);
			hold=new ViewHold();
//			hold.textView=(TextView)convertView.findViewById(id);
//			hold.textView2=(TextView)convertView.findViewById(id);
//			hold.textView3=(TextView)convertView.findViewById(id);
//			hold.textView4=(TextView)convertView.findViewById(id);
//			hold.textView5=(TextView)convertView.findViewById(id);
			convertView.setTag(hold);
		}
		hold=(ViewHold)convertView.getTag();
		PersinalCenterEntity p=list.get(position);
		hold.textView.setText(p.getDeliveredCount());
		hold.textView2.setText(p.getFinishedCount());
		hold.textView3.setText(p.getNonPaymentcount());
		hold.textView4.setText(p.getPaidCount());
		hold.textView5.setText(p.getReturnedCount());
		return convertView;
	}
	class ViewHold{
		TextView textView;
		TextView textView2;
		TextView textView3;
		TextView textView4;
		TextView textView5;
	}

}
