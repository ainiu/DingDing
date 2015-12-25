package com.ghsh.activity.goodscategory;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.ghsh.activity.GoodsListActivity;
import com.ghsh.adapter.GoodsCategoryGridViewtAdapter1;
import com.ghsh.adapter.GoodsCategoryGridViewtAdapter2;
import com.ghsh.code.bean.TGoodsCategory;
import com.ghsh.view.util.ViewLeftRightMove;
import com.ghsh.view.util.ViewUtils;
import com.ghsh.R;
/**
 * 分类--列表
 * */
public class GoodsCategoryGridView extends GoodsCategoryViewAbstract{
	private GridView gridView1,gridView2;
	private GoodsCategoryGridViewtAdapter1 adapter1;
	private GoodsCategoryGridViewtAdapter2 adapter2;
	/**滑动效果*/
	private ViewLeftRightMove viewLeftRightMove;
	public GoodsCategoryGridView(Activity activity,List<TGoodsCategory> categoryList){
		super(activity, categoryList);
		this.initView();
		this.initListener();
	}
	
	private void initView(){
		adapter1=new GoodsCategoryGridViewtAdapter1(activity, new ArrayList<TGoodsCategory>());
		adapter2=new GoodsCategoryGridViewtAdapter2(activity, new ArrayList<TGoodsCategory>());
		
		gridView1=(GridView)activity.findViewById(R.id.goodscategory_gridView1);
		gridView1.setAdapter(adapter1);
		
		gridView2=(GridView)activity.findViewById(R.id.goodscategory_gridView2);
		gridView2.setAdapter(adapter2);
		
		View listviewLayout=activity.findViewById(R.id.gridView_layout);
		listviewLayout.setVisibility(View.VISIBLE);
	} 
	private void initListener() {
		gridView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				TGoodsCategory goodsCategory=adapter1.getItem(position);
				List<TGoodsCategory> list=goodsCategory.getItemsList();
				if(list.size()==0){
					Intent intent=new Intent(activity,GoodsListActivity.class);
					intent.putExtra("categoryID", goodsCategory.getCategoryID());
					activity.startActivity(intent);
					GoodsCategoryGridView.this.moveView(-1);
				}else{
					GoodsCategoryGridView.this.moveView(position);
					gridView1.setSelection(position);
				}
				adapter2.setList(list);
				adapter2.notifyDataSetChanged();
			}
		});
		gridView2.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				TGoodsCategory goodsCategory=adapter2.getItem(position);
				Intent intent=new Intent(activity,GoodsListActivity.class);
				intent.putExtra("categoryID", goodsCategory.getCategoryID());
				activity.startActivity(intent);
			}
		});
		viewLeftRightMove=new ViewLeftRightMove(activity, gridView1, gridView2){
			@Override
			protected void touchCallback(boolean isMove) {
				GoodsCategoryGridView.this.moveView(-1);
			}
		};
	}
	/**移动*/
	private void moveView(int position){
		adapter1.setCurrentRow(position);
		if(position<0){
			//还原
			gridView1.setVerticalSpacing(20);
			gridView1.setHorizontalSpacing(20);
			gridView1.setNumColumns(3);
			gridView1.setBackgroundDrawable(null);
			viewLeftRightMove.moveView(false);
		}else{
			//左移动
			gridView1.setVerticalSpacing(0);
			gridView1.setHorizontalSpacing(0);
			gridView1.setNumColumns(1);
			gridView1.setBackgroundColor(activity.getResources().getColor(R.color.goods_category_gridview1_color));
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
