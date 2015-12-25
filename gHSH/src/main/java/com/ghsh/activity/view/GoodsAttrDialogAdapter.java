
package com.ghsh.activity.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.adapter.AbstractBaseAdapter;
import com.ghsh.code.bean.TGoodsAttr;

public class GoodsAttrDialogAdapter extends AbstractBaseAdapter<TGoodsAttr.TGoodsAttrItem> {

	private TGoodsAttr goodsAttr;
	public GoodsAttrDialogAdapter(Context context, List<TGoodsAttr.TGoodsAttrItem> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final TGoodsAttrItemHolder holder;
		final TGoodsAttr.TGoodsAttrItem goodsAttrItem=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_goodsdetails_attr_value, null);
			holder = new TGoodsAttrItemHolder();
			holder.goods_attr_text=(TextView)convertView.findViewById(R.id.goods_attr_text);
			holder.goods_attr_layout=convertView.findViewById(R.id.goods_attr_layout);
			convertView.setTag(holder);
		} else {
			holder = (TGoodsAttrItemHolder) convertView.getTag();
		}
		holder.goods_attr_text.setText(goodsAttrItem.attr_value+"\n+￥"+goodsAttrItem.attr_price+"元");
		if(goodsAttrItem.isSelected()){
			holder.goods_attr_layout.setBackgroundColor(context.getResources().getColor(R.color.button_goods_attr_select_bg_color));
		}else{
			holder.goods_attr_layout.setBackgroundColor(context.getResources().getColor(R.color.button_goods_attr_bg_color));
		}
		holder.goods_attr_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(goodsAttr.getAttrType()==1){
					//单选
					for(TGoodsAttr.TGoodsAttrItem item:list){
						item.setSelected(false);
					}
					goodsAttrItem.setSelected(true);
					notifyDataSetChanged();
				}else if(goodsAttr.getAttrType()==2){
					//多选
					goodsAttrItem.setSelected(!goodsAttrItem.isSelected());
					notifyDataSetChanged();
				}
			}
		});
		return convertView;
	}

	public TGoodsAttr getGoodsAttr() {
		return goodsAttr;
	}

	public void setGoodsAttr(TGoodsAttr goodsAttr) {
		this.goodsAttr = goodsAttr;
	}

	class TGoodsAttrItemHolder {
		TextView goods_attr_text;
		View goods_attr_layout;
	}
}
