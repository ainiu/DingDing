package com.ghsh.code.bean;

import java.io.Serializable;

public class TShoppingCart implements Serializable{

	private static final long serialVersionUID = 1L;

	private String shoppingID;//购物车ID
	
	private String goodsID;//商品ID
	
	private String groupID;//团购编号
	
	private String goodsName;//商品名称
	
	private String goodsPrice;//商品价格
	
	private String goodsNumber;//商品数量
	
	private String goodsImage;//商品图片
	
	private String goodsAttr;//商品属性
	
	private int stock;//商品库存
	
	private boolean isSelected;//是否选择
	
	private String comment;//留言

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getShoppingID() {
		return shoppingID;
	}

	public void setShoppingID(String shoppingID) {
		this.shoppingID = shoppingID;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public String getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getGoodsAttr() {
		return goodsAttr;
	}

	public void setGoodsAttr(String goodsAttr) {
		this.goodsAttr = goodsAttr;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
