package com.ghsh.activity.goodscategory;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ghsh.activity.GoodsListActivity;
import com.ghsh.adapter.GoodsCategoryListViewtAdapter1;
import com.ghsh.adapter.GoodsCategoryListViewtAdapter2;
import com.ghsh.code.bean.TGoodsCategory;
import com.ghsh.view.util.ViewLeftRightMove;
import com.ghsh.view.util.ViewUtils;
import com.ghsh.R;
/**
 * 分类--列表
 * */
public class GoodsCategoryListView extends GoodsCategoryViewAbstract{
	private ListView listView1,listView2;
	private GoodsCategoryListViewtAdapter1 adapter1;
	private GoodsCategoryListViewtAdapter2 adapter2;
	/**滑动效果*/
	private ViewLeftRightMove viewLeftRightMove;
	public GoodsCategoryListView(Activity activity,List<TGoodsCategory> categoryList){
		super(activity, categoryList);
		this.initView();
		this.initListener();
	}
	
	private void initView(){
		adapter1=new GoodsCategoryListViewtAdapter1(activity, new ArrayList<TGoodsCategory>());
		adapter2=new GoodsCategoryListViewtAdapter2(activity, new ArrayList<TGoodsCategory>());
		
		listView1=(ListView)activity.findViewById(R.id.goodscategory_listView1);
		listView1.setAdapter(adapter1);
		listView1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);// 一定要设置这个属性，否则ListView不会刷新  
		
		listView2=(ListView)activity.findViewById(R.id.goodscategory_listView2);
		listView2.setAdapter(adapter2);
		
		View listviewLayout=activity.findViewById(R.id.listview_layout);
		listviewLayout.setVisibility(View.VISIBLE);
	}
	private void initListener() {
		listView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				TGoodsCategory goodsCategory=adapter1.getItem(position);
				List<TGoodsCategory> list=goodsCategory.getItemsList();
				if(list.size()==0){
					Intent intent=new Intent(activity,GoodsListActivity.class);
					intent.putExtra("categoryID", goodsCategory.getCategoryID());
					activity.startActivity(intent);
					GoodsCategoryListView.this.moveView(-1);
				}else{
					GoodsCategoryListView.this.moveView(position);
					listView1.setSelection(position);
				}
				adapter2.setList(list);
				adapter2.notifyDataSetChanged();
			}
		});
		listView2.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				TGoodsCategory goodsCategory=adapter2.getItem(position);
				Intent intent=new Intent(activity,GoodsListActivity.class);
				intent.putExtra("categoryID", goodsCategory.getCategoryID());
				activity.startActivity(intent);
			}
		});
		viewLeftRightMove=new ViewLeftRightMove(activity, listView1, listView2){
			@Override
			protected void touchCallback(boolean isMove) {
				GoodsCategoryListView.this.moveView(-1);
			}
		};
	}
	/**移动*/
	private void moveView(int position){
		adapter1.setCurrentRow(position);
		if(position<0){
			//还原
			listView1.setDivider(activity.getResources().getDrawable(R.drawable.line_dotted));
			listView1.setDividerHeight(2);
			listView1.setBackgroundDrawable(null);
			viewLeftRightMove.moveView(false);
		}else{
			//左移动
//			R.drawable.category_list_left_bg
			listView1.setDivider(null);
			listView1.setDividerHeight(2);
			listView1.setBackgroundColor(activity.getResources().getColor(R.color.goods_category_list_left_bg_color));
			viewLeftRightMove.moveView(true);
		}
	}
	@Override
	public void notifyDataSetChanged(){
		adapter1.setList(categoryList);
		adapter1.notifyDataSetChanged();
	}
	@Override
	public boolean isEmptyData() {
		return adapter1.getList().size()==0;
	}
}
