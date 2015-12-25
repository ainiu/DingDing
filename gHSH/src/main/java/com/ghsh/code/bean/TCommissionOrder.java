package com.ghsh.code.bean;

import java.util.List;

public class TCommissionOrder {
	
	private String commissionID;
	private String user_name;
	private String order_sn;
	private String total_price;
	private String total_quantity;
	private String sub_money;
	private String sub_percentage;
	private List<TCommissionGoodInfo> goodInfos;
	/*private String goods_name;
	private String price;
	private String quantity;
	private String goods_image;*/
	
	
	public String getCommissionID() {
		return commissionID;
	}
	public List<TCommissionGoodInfo> getGoodInfos() {
		return goodInfos;
	}
	public void setGoodInfos(List<TCommissionGoodInfo> goodInfos) {
		this.goodInfos = goodInfos;
	}
	public void setCommissionID(String commissionID) {
		this.commissionID = commissionID;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getTotal_price() {
		return total_price;
	}
	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}
	public String getTotal_quantity() {
		return total_quantity;
	}
	public void setTotal_quantity(String total_quantity) {
		this.total_quantity = total_quantity;
	}
	public String getSub_money() {
		return sub_money;
	}
	public void setSub_money(String sub_money) {
		this.sub_money = sub_money;
	}
	public String getSub_percentage() {
		return sub_percentage;
	}
	public void setSub_percentage(String sub_percentage) {
		this.sub_percentage = sub_percentage;
	}
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
	
	public TCommissionOrder(String user_name, String order_sn,
			String total_price, String total_quantity, String sub_money,
			String sub_percentage) {
		super();
		this.user_name = user_name;
		this.order_sn = order_sn;
		this.total_price = total_price;
		this.total_quantity = total_quantity;
		this.sub_money = sub_money;
		this.sub_percentage = sub_percentage;
	}
	@Override
	public String toString() {
		return "TCommissionOrder [commissionID=" + commissionID
				+ ", user_name=" + user_name + ", order_sn=" + order_sn
				+ ", total_price=" + total_price + ", total_quantity="
				+ total_quantity + ", sub_money=" + sub_money
				+ ", sub_percentage=" + sub_percentage + ", goodInfos="
				+ goodInfos + "]";
	}
}
