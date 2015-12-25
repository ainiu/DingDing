
package com.ghsh.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.code.bean.TOrderItem;
import com.ghsh.code.exception.AppViewException;
import com.ghsh.code.volley.DVolley;
import com.ghsh.util.BigDecimalUtils;

public class OrderDetailsListViewtAdapter extends AbstractBaseAdapter<TOrderItem> {

	public OrderDetailsListViewtAdapter(Context context, List<TOrderItem> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final OrderDetailsHolder holder;
		final TOrderItem orderItem=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_orderdetails_listview_item, null);
			holder = new OrderDetailsHolder();
			holder.goodsImageView=(ImageView)convertView.findViewById(R.id.orderdetalis_item_image);
			holder.goodsNameView=(TextView)convertView.findViewById(R.id.orderdetalis_item_name);
			holder.goodsPriceView=(TextView)convertView.findViewById(R.id.orderdetalis_item_price);
			holder.goodsNumberView=(TextView)convertView.findViewById(R.id.orderdetalis_item_number);
			holder.goodsAttrView=(TextView) convertView.findViewById(R.id.orderdetalis_item_goods_attr);
			convertView.setTag(holder);
		} else {
			holder = (OrderDetailsHolder) convertView.getTag();
		}
		holder.goodsNameView.setText(orderItem.getGoodsName());
		holder.goodsPriceView.setText(context.getResources().getString(R.string.orderdetails_text_goods_price)+"￥"+orderItem.getGoodsPrice());
		holder.goodsNumberView.setText(context.getResources().getString(R.string.orderdetails_text_goods_number)+orderItem.getGoodsNumber());
		holder.goodsAttrView.setText(orderItem.getGoodsAttr());
		if(orderItem.getGoodsImage()!=null&&!orderItem.getGoodsImage().equals("")){
			DVolley.getImage(orderItem.getGoodsImage(),holder.goodsImageView,R.drawable.default_image);
		}else{
			holder.goodsImageView.setImageResource(R.drawable.default_image);
		}
		return convertView;
	}
	
	public String getGoodsIDAndNumberJson(List<TOrderItem> list){
		JSONArray jsonArray=new JSONArray();
		try {
			for(TOrderItem item:list){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put(item.getGoodsID(), item.getGoodsNumber());
				jsonArray.put(jsonObject);
			}
		} catch (JSONException e) {
			AppViewException.onViewException(e);
		}
		return jsonArray.toString();
	}
	
	//获取购物车ID数组
	public List<String> getCartIDs(List<TOrderItem> list){
		List<String> ids=new ArrayList<String>();
		for(TOrderItem item:list){
			ids.add(item.getOrderItemID());
		}
		return ids;
	}
	class OrderDetailsHolder {
		ImageView goodsImageView;
		TextView goodsNameView;
		TextView goodsPriceView;
		TextView goodsNumberView;
		TextView goodsAttrView;
	}
}
