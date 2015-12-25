
package com.ghsh.adapter;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghsh.code.bean.TGoodsCategory;
import com.ghsh.code.volley.DVolley;
import com.ghsh.R;

public class GoodsCategoryGridViewtAdapter1 extends AbstractBaseAdapter<TGoodsCategory> {

	private int currentRow=-1;//当前选择
	public GoodsCategoryGridViewtAdapter1(Context context, List<TGoodsCategory> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final TGoodsCategoryHolder holder;
		final TGoodsCategory category=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_goodscategory_gridview_item1, null);
			holder = new TGoodsCategoryHolder();
			holder.layout=convertView.findViewById(R.id.goodscategory_item_layout);
			holder.imageView=(ImageView)convertView.findViewById(R.id.goodscategory_item_image);
			holder.nameView=(TextView)convertView.findViewById(R.id.goodscategory_item_name);
			holder.imageLayout=convertView.findViewById(R.id.goodscategory_item_image_layout);
			convertView.setTag(holder);
		} else {
			holder = (TGoodsCategoryHolder) convertView.getTag();
		}
		holder.nameView.setText(category.getName());
		if(currentRow==position){
			//选中
			holder.layout.setBackgroundColor(context.getResources().getColor(R.color.white));
			holder.nameView.setTextColor(context.getResources().getColor(R.color.font_color_red));
		}else{
			//不选中
			holder.layout.setBackgroundDrawable(null);
			holder.nameView.setTextColor(context.getResources().getColor(R.color.font_color_333333));
		}
		if(currentRow!=-1){
			//移动
			holder.imageLayout.setVisibility(View.GONE);
			LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)holder.nameView.getLayoutParams();
			params.height=100;
			params.gravity=Gravity.RIGHT;
			holder.nameView.setLayoutParams(params);
			holder.nameView.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
			holder.nameView.setEms(4);
		}else{
			//还原
			holder.layout.setBackgroundDrawable(null);
			holder.imageLayout.setVisibility(View.VISIBLE);
			DVolley.getImage(category.getImagePath(),holder.imageView,R.drawable.default_image);
			LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)holder.nameView.getLayoutParams();
			params.height=50;
			params.gravity=Gravity.CENTER;
			holder.nameView.setLayoutParams(params);
			holder.nameView.setGravity(Gravity.CENTER);
			holder.nameView.setEms(10);
		}
		return convertView;
	}
	
	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
		this.notifyDataSetChanged();
	}
	class TGoodsCategoryHolder {
		View layout;
		View imageLayout;
		ImageView imageView;
		TextView nameView;
	}
}
