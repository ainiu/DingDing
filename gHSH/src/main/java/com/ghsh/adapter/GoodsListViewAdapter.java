
package com.ghsh.adapter;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghsh.code.bean.TGoods;
import com.ghsh.code.volley.DVolley;
import com.ghsh.R;

public class GoodsListViewAdapter extends AbstractBaseAdapter<TGoods> {

	private int layout;//布局文件
	private Listener listener;
	private BigDecimal price,sourcePrice;
	public GoodsListViewAdapter(Context context, List<TGoods> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		GoodsListViewHolder holder;
		final TGoods goods=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(layout, null);
			holder = new GoodsListViewHolder();
			holder.goodsNameView=(TextView)convertView.findViewById(R.id.goods_item_name);
			holder.goodsPriceView=(TextView)convertView.findViewById(R.id.goods_item_price);
			holder.goodsImageView=(ImageView)convertView.findViewById(R.id.goods_item_image);
			holder.goodsPriceShop=(TextView)convertView.findViewById(R.id.goods_item_price_shop);
			convertView.setTag(holder);
		} else {
			holder = (GoodsListViewHolder) convertView.getTag();
		}
		holder.goodsNameView.setText(goods.getName());
		holder.goodsPriceView.setText("￥"+goods.getShopPrice());
		holder.goodsPriceShop.setText(goods.getMarketPrice());
		holder.goodsPriceShop.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		
		try {
			price=new BigDecimal(goods.getShopPrice());
			sourcePrice=new BigDecimal(goods.getMarketPrice());
			if(price.doubleValue()==sourcePrice.doubleValue()){
				holder.goodsPriceShop.setVisibility(View.GONE);
			}else{
				holder.goodsPriceShop.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			holder.goodsPriceShop.setVisibility(View.GONE);
		}
		
		DVolley.getImage(goods.getDefaultImage(),holder.goodsImageView,R.drawable.default_image);
		return convertView;
	}

	public void setLayout(int layout) {
		this.layout = layout;
	}

	public void addListener(Listener listener){
		this.listener=listener;
	}
	class GoodsListViewHolder {
		LinearLayout goodsPriceShopLayout;
		TextView goodsNameView;
		TextView goodsPriceView;
		TextView goodsPriceShop;
		ImageView goodsImageView;
	}
	
	public static interface Listener{
		//加入购物车
		public void addCart(TGoods goods);
	}
}
