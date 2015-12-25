package com.ghsh.code.bean;

import java.util.ArrayList;
import java.util.List;

public class TNewsHomeBean {

	private  List<TNewsCategory> categoryList=new ArrayList<TNewsCategory>();
	
	private  List<TNews> newsList=new ArrayList<TNews>();

	public List<TNewsCategory> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<TNewsCategory> categoryList) {
		this.categoryList = categoryList;
	}

	public List<TNews> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<TNews> newsList) {
		this.newsList = newsList;
	}
}
