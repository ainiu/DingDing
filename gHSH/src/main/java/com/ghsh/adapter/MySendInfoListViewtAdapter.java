
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ghsh.code.bean.TSendInfo;
import com.ghsh.R;

public class MySendInfoListViewtAdapter extends AbstractBaseAdapter<TSendInfo> {

	private Listener listener;
	public MySendInfoListViewtAdapter(Context context, List<TSendInfo> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		TSendInfoHolder holder;
		final TSendInfo sendInfo=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_mysendinfo_listview_item, null);
			holder = new TSendInfoHolder();
			holder.nameView=(TextView)convertView.findViewById(R.id.mysendinfo_item_name);
			holder.mobileView=(TextView)convertView.findViewById(R.id.mysendinfo_item_mobile);
			holder.addressView=(TextView)convertView.findViewById(R.id.mysendinfo_item_address);
			holder.postZipView=(TextView)convertView.findViewById(R.id.mysendinfo_item_postZip);
			holder.editView=(TextView)convertView.findViewById(R.id.mysendinfo_item_edit);
			holder.delView=(TextView)convertView.findViewById(R.id.mysendinfo_item_del);
			holder.checkBox=(CheckBox)convertView.findViewById(R.id.mysendinfo_item_checkBox);
			convertView.setTag(holder);
		} else {
			holder = (TSendInfoHolder) convertView.getTag();
		}
		holder.nameView.setText(sendInfo.getConsignee());
		holder.mobileView.setText(sendInfo.getMobile());
		holder.addressView.setText(context.getResources().getString(R.string.mySendinfo_text_address)+sendInfo.getRegionName()+sendInfo.getAddress());
		holder.postZipView.setText(sendInfo.getZipcode());
		holder.checkBox.setChecked(sendInfo.isDefault());
		holder.delView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.deleteSendInfo(sendInfo);
				}
			}
		});
		holder.editView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.modifySendInfo(sendInfo);
				}
			}
		});
		holder.checkBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.modifySendInfoDefault(sendInfo);
				}
			}
		});
		return convertView;
	}

	public void addListener(Listener listener){
		this.listener=listener;
	}
	
	public void removeSendInfoByID(String sendInfoID){
		if(sendInfoID==null||sendInfoID.equals("")){
			return;
		}
		for(TSendInfo sendInfo:list){
			if(sendInfo.getSendInfoID().equals(sendInfoID)){
				list.remove(sendInfo);
				this.notifyDataSetChanged();
				return;
			}
		}
	}
	public void modifyDefault(String sendInfoID){
		if(sendInfoID==null||sendInfoID.equals("")){
			return;
		}
		for(TSendInfo sendInfo:list){
			if(sendInfo.getSendInfoID().equals(sendInfoID)){
				sendInfo.setDefault(true);
			}else{
				sendInfo.setDefault(false);
			}
		}
		this.notifyDataSetChanged();
	}
	class TSendInfoHolder {
		TextView nameView;//收货人名称
		TextView mobileView;//手机
		TextView addressView;//地址
		TextView postZipView;//邮编
		TextView editView;//编辑
		TextView delView;//删除
		CheckBox checkBox;//设置默认地址
	}
	
	public static interface Listener{
		public void deleteSendInfo(TSendInfo sendInfo);
		public void modifySendInfo(TSendInfo sendInfo);
		public void modifySendInfoDefault(TSendInfo sendInfo);
	}
}
