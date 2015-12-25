package com.ghsh.code.bean;

import java.io.Serializable;
/**
 * 配送方式
 * */
public class TShipping implements Serializable {
	private static final long serialVersionUID = 1L;
	private String shippingID;// ID
	private String shippingName;// 名称
	
	private boolean isSelected=false;//是否选择
	public String getShippingID() {
		return shippingID;
	}
	public void setShippingID(String shippingID) {
		this.shippingID = shippingID;
	}
	
	public String getShippingName() {
		return shippingName;
	}
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

}
