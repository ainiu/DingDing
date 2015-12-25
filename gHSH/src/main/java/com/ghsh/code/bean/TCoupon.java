package com.ghsh.code.bean;

import java.io.Serializable;


/**
 * 优惠劵
 * */
public class TCoupon implements Serializable{
	private static final long serialVersionUID = 1L;

	private String couponID;//优惠劵编号
	
	private String price;//价格
	
	private String name;//名称
	
	private boolean isSelected;//是否选中

	public String getCouponID() {
		return couponID;
	}

	public void setCouponID(String couponID) {
		this.couponID = couponID;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
