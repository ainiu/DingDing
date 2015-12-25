
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

import com.ghsh.R;
import com.ghsh.activity.view.LeftArrowView;
import com.ghsh.code.bean.TGoodsCategory;
import com.ghsh.code.volley.DVolley;

public class GoodsCategoryListViewtAdapter1 extends AbstractBaseAdapter<TGoodsCategory> {

	private int currentRow=-1;//当前选择
	public GoodsCategoryListViewtAdapter1(Context context, List<TGoodsCategory> list) {
		super(context, list);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final TGoodsCategoryHolder holder;
		final TGoodsCategory category=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_goodscategory_listview_item1, null);
			holder = new TGoodsCategoryHolder();
			holder.imageView=(ImageView)convertView.findViewById(R.id.goodscategory_item_image);
			holder.nameView=(TextView)convertView.findViewById(R.id.goodscategory_item_name);
			holder.descView=(TextView)convertView.findViewById(R.id.goodscategory_item_desc);
			holder.arrowView=(LeftArrowView)convertView.findViewById(R.id.goodscategory_item_arrow);
			holder.beforeView=(ImageView)convertView.findViewById(R.id.goodscategory_item_before);
			convertView.setTag(holder);
		} else {
			holder = (TGoodsCategoryHolder) convertView.getTag();
		}
		holder.nameView.setText(category.getName());
		holder.descView.setText(category.getDesc());
//		holder.arrowView.setBackgroundColor(ThemeStyle.getAppBgColor(context));
		if(currentRow==position){
			//选中的价格
			holder.arrowView.setVisibility(View.VISIBLE);
			//context.getResources().getColor(R.color.goods_category_text_select_color)
//			holder.nameView.setTextColor(ThemeStyle.getAppGlobalColor(context));
		}else{
			holder.arrowView.setVisibility(View.GONE);
			holder.nameView.setTextColor(context.getResources().getColor(R.color.font_color_333333));
		}
		if(currentRow!=-1){
			holder.imageView.setVisibility(View.GONE);
			holder.descView.setVisibility(View.GONE);
			holder.beforeView.setVisibility(View.GONE);
			
			LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)holder.nameView.getLayoutParams();
			params.gravity=Gravity.RIGHT;
			params.height=(int)context.getResources().getDimension(R.dimen.height_goods_category_listview_1);
			holder.nameView.setLayoutParams(params);
			holder.nameView.setGravity(Gravity.CENTER);
			holder.nameView.setEms(4);
		}else{
			holder.imageView.setVisibility(View.VISIBLE);
			holder.descView.setVisibility(View.VISIBLE);
			holder.beforeView.setVisibility(View.VISIBLE);
			DVolley.getImage(category.getImagePath(),holder.imageView,R.drawable.default_image);
			LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)holder.nameView.getLayoutParams();
			params.gravity=Gravity.LEFT;
			params.height=LinearLayout.LayoutParams.WRAP_CONTENT;
			holder.nameView.setLayoutParams(params);
			holder.nameView.setGravity(Gravity.LEFT);
			holder.nameView.setEms(10);
		}
		return convertView;
	}
	
	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
		this.notifyDataSetChanged();
	}
	class TGoodsCategoryHolder {
		ImageView imageView;
		TextView nameView;
		TextView descView;
		LeftArrowView arrowView;
		ImageView beforeView;
	}
}
