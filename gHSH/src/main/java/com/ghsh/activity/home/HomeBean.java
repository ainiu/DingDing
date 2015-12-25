package com.ghsh.activity.home;

import java.io.Serializable;

public class HomeBean implements Serializable{

	private static final long serialVersionUID = 1L;

	public static  final int TYPE_0=0;//一张广告
	
	public static  final int TYPE_1=1;//多张广告
	
	public static  final int TYPE_2=2;//公告
	
	public static  final int TYPE_3=3;//功能按钮
	
	public static  final int TYPE_4=4;//三个分类商品
	
	public static  final int TYPE_5=5;//团购--限时促销
	
	public static  final int TYPE_6=6;//团购--限时促销-标题
	
	public static  final int TYPE_COUNT=7;//类型总数量
	
	private int itemType;
	
	private Object object;

	public HomeBean(int itemType,Object object){
		this.itemType=itemType;
		this.object=object;
	}
	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
}
