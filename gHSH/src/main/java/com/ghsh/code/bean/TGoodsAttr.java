package com.ghsh.code.bean;

import java.util.ArrayList;
import java.util.List;

public class TGoodsAttr {

	private int attrType;//属性类型 1:单选 2：多选
	
	private String attrName;
	
	private List<TGoodsAttrItem> attrItemList=new ArrayList<TGoodsAttrItem>();
	
	
	public int getAttrType() {
		return attrType;
	}


	public void setAttrType(int attrType) {
		this.attrType = attrType;
	}


	public String getAttrName() {
		return attrName;
	}


	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}


	public List<TGoodsAttrItem> getAttrItemList() {
		return attrItemList;
	}


	public void setAttrItemList(List<TGoodsAttrItem> attrItemList) {
		this.attrItemList = attrItemList;
	}


	public static class TGoodsAttrItem{
		public String goods_attr_id;
		public String attr_id;
		public String attr_value;
		public String attr_price;
		public String attr_name;
		public int attr_type;
		
		private boolean isSelected;

		public boolean isSelected() {
			return isSelected;
		}

		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		}
		
	}
}
