package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.dingding.R;
import com.baidu.dingding.entity.FenLeiDianPu;

import java.util.List;

public class ChanPinMuAdapter extends BaseAdapter{
	private Context context;
	private List<FenLeiDianPu> list;
	private LayoutInflater inflater;

	private ViewHold hold=null;
	public ChanPinMuAdapter(Context context,List<FenLeiDianPu>list){
		this.context=context;
		this.list=list;
		inflater=LayoutInflater.from(context);
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





	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView=inflater.inflate(R.layout.fenlei_dianfu, null);
			hold=new ViewHold();
			hold.shopName=(TextView)convertView.findViewById(R.id.fenlei_dianfu_text_02);
			hold.logPath=(ImageView)convertView.findViewById(R.id.fenlei_dianfu_image_01);
			hold.contury=(TextView)convertView.findViewById(R.id.fenlei_dianfu_text_06);
			hold.authentication=(TextView)convertView.findViewById(R.id.fenlei_dianfu_text_08);
			hold.usrName=(TextView)convertView.findViewById(R.id.fenlei_dianfu_text_04);
			convertView.setTag(hold);
		}else{
			hold=(ViewHold)convertView.getTag();
		}
		FenLeiDianPu dian=list.get(position);
		hold.contury.setText(dian.getCountry());
		hold.authentication.setText(dian.getAuthentication());
		hold.shopName.setText(dian.getName());
		hold.usrName.setText(dian.getUsrName());
		hold.logPath.setTag(parent);
		return convertView;
	}

	class ViewHold{
		TextView shopName;
		ImageView logPath;
		TextView usrName;
		TextView contury;
		TextView authentication;
	}
}
