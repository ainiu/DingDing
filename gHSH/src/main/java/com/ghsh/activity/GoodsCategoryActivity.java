package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.activity.goodscategory.GoodsCategoryListView;
import com.ghsh.activity.goodscategory.GoodsCategoryViewAbstract;
import com.ghsh.code.bean.TGoodsCategory;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.GoodsCategoryModel;
import com.ghsh.dialog.DProgressDialog;

/**
 * 商品分类
 * */
public class GoodsCategoryActivity extends BaseActivity{
	private TextView titleView;
	private GoodsCategoryModel goodsCategoryModel;
	private DProgressDialog progressDialog;
	private List<TGoodsCategory> categoryList=new ArrayList<TGoodsCategory>();
	/**样式*/
	private GoodsCategoryViewAbstract goodsCategoryViewAbstract;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goodscategory);
		this.initView();
		goodsCategoryModel=new GoodsCategoryModel(this);
		goodsCategoryModel.addResponseListener(new GoodsCategoryResponse(this));
	}

	private void initView() {
		super.setBackButtonVisible(View.GONE);
		super.setBackExit(true);
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.goods_category_title);
		
		//列表布局
		goodsCategoryViewAbstract=new GoodsCategoryListView(this, categoryList);
		
		//网格布局
//		goodsCategoryViewAbstract=new GoodsCategoryGridView(this, categoryList);
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		if(categoryList.size()==0){
			progressDialog.show();
			goodsCategoryModel.findGoodsCategoryList();
		}
	}
	class GoodsCategoryResponse extends DResponseAbstract{
		public GoodsCategoryResponse(Activity activity) {
			super(activity);
		}
		@Override
		public void onResponseStart() {
			progressDialog.dismiss();
		}
		@Override
		protected void onResponseSuccess(ReturnBean bean, String message) {
			if(bean.getType()==DVolleyConstans.METHOD_QUERYALL){
				//查询所有分类
				List<TGoodsCategory> list=(List<TGoodsCategory>)bean.getObject();
				categoryList.clear();
				categoryList.addAll(list);
				goodsCategoryViewAbstract.notifyDataSetChanged();
			}
		}
		@Override
		protected boolean isShowEmptyPageView() {
			return categoryList.size()==0;
		}
		@Override
		protected void onRefresh() {
			progressDialog.show();
			goodsCategoryModel.findGoodsCategoryList();
		}
	}
}
