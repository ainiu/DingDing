package com.ghsh.activity.goodscategory;

import java.util.List;

import android.app.Activity;

import com.ghsh.code.bean.TGoodsCategory;

public abstract class GoodsCategoryViewAbstract {
	protected Activity activity;
	protected List<TGoodsCategory> categoryList;
	public GoodsCategoryViewAbstract(Activity activity,List<TGoodsCategory> categoryList){
		this.activity=activity;
		this.categoryList=categoryList;
	}
	
	public abstract void notifyDataSetChanged();
	
	public abstract boolean isEmptyData();//是否没有数据
}
