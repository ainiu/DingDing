
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.ghsh.code.bean.TOrderItem;
import com.ghsh.code.volley.DVolley;
import com.ghsh.R;

public class CommentAddListViewViewtAdapter extends AbstractBaseAdapter<TOrderItem> {

	public CommentAddListViewViewtAdapter(Context context, List<TOrderItem> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final CommentAddHolder holder;
		final TOrderItem orderItem=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_comment_add_listeview_item, null);
			holder = new CommentAddHolder();
			holder.goodsImageView=(ImageView)convertView.findViewById(R.id.comment_item_image);
			holder.goodsNameView=(TextView)convertView.findViewById(R.id.comment_item_name);
			holder.goodsPriceView=(TextView)convertView.findViewById(R.id.comment_item_price);
			holder.goodsNumberView=(TextView)convertView.findViewById(R.id.comment_item_number);
			holder.inputView=(EditText)convertView.findViewById(R.id.comment_item_comment);
			holder.goodsAttrView=(TextView)convertView.findViewById(R.id.comment_item_goodsAttr);
			
			holder.ratingbar1=(RatingBar)convertView.findViewById(R.id.comment_item_ratingbar1);
			convertView.setTag(holder);
		} else {
			holder = (CommentAddHolder) convertView.getTag();
		}
		holder.goodsNameView.setText(orderItem.getGoodsName());
		holder.goodsPriceView.setText(context.getResources().getString(R.string.orderdetails_text_goods_price)+"￥"+orderItem.getGoodsPrice());
		holder.goodsNumberView.setText(context.getResources().getString(R.string.orderdetails_text_goods_number)+orderItem.getGoodsNumber());
		holder.goodsAttrView.setText(orderItem.getGoodsAttr());
		DVolley.getImage(orderItem.getGoodsImage(),holder.goodsImageView,R.drawable.default_image);
		holder.inputView.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				orderItem.setComment(holder.inputView.getText().toString().trim());
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		holder.ratingbar1.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,boolean fromUser) {
				int evaluation=(int)rating;
				orderItem.setEvaluation(evaluation);
			}
		});
		return convertView;
	}

	class CommentAddHolder {
		ImageView goodsImageView;
		TextView goodsNameView;
		TextView goodsPriceView;
		TextView goodsNumberView;
		TextView goodsAttrView;
		EditText inputView;
		RatingBar ratingbar1;
	}
}
