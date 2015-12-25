package com.ghsh.code.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类
 * */
public class TGoodsCategory implements Serializable{

	private static final long serialVersionUID = 1L;

	private String categoryID;//分类ID
	
	private String name;//分类名称
	
	private String imagePath;//图片路径
	
	private String desc;//描述
	
	private List<TGoodsCategory> itemsList=new ArrayList<TGoodsCategory>();
	
	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<TGoodsCategory> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<TGoodsCategory> itemsList) {
		this.itemsList = itemsList;
	}

}
