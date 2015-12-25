package com.ghsh.code.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品
 * */
public class TGoods implements Serializable {

	private static final long serialVersionUID = 1L;

	private String goodsID;// 商品ID

	private String name;// 商品名称

	private String categoryID;// 分类ID

	private String shopPrice;// 商品价格
	
	private String marketPrice;// 商品原价
	
	private String defaultImage;// 商品默认图片

	private String recommended;// 是否推荐

	private boolean isCollect;// 是否收藏

	private String commentsNumber;// 评论数量

	private String consultNumber;// 咨询数量

	private String collectsNumber;// 收藏数量

	private String salesNumber;// 销售数量
	
	private boolean isLevelDiscount;//是否有级别折扣

	private boolean isPromote;//是否促销
	
	private List<TPic> picList = new ArrayList<TPic>();// 商品图片列表
	
	private List<TGoodsAttr> goodsAttrList = new ArrayList<TGoodsAttr>();// 商品规格

	private int number = 1;// 数量
	
	
	//运费
	private String shopCity;//发货城市
	private String deliveryFree;//运费

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPromote() {
		return isPromote;
	}

	public void setPromote(boolean isPromote) {
		this.isPromote = isPromote;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}


	public String getDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}

	public String getRecommended() {
		return recommended;
	}

	public void setRecommended(String recommended) {
		this.recommended = recommended;
	}

	public String getSalesNumber() {
		return salesNumber;
	}

	public void setSalesNumber(String salesNumber) {
		this.salesNumber = salesNumber;
	}

	public List<TPic> getPicList() {
		return picList;
	}

	public void setPicList(List<TPic> picList) {
		this.picList = picList;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<TGoodsAttr> getGoodsAttrList() {
		return goodsAttrList;
	}

	public void setGoodsAttrList(List<TGoodsAttr> goodsAttrList) {
		this.goodsAttrList = goodsAttrList;
	}

	public boolean isCollect() {
		return isCollect;
	}

	public void setCollect(boolean isCollect) {
		this.isCollect = isCollect;
	}

	public String getCommentsNumber() {
		return commentsNumber;
	}

	public void setCommentsNumber(String commentsNumber) {
		this.commentsNumber = commentsNumber;
	}

	public String getConsultNumber() {
		return consultNumber;
	}

	public void setConsultNumber(String consultNumber) {
		this.consultNumber = consultNumber;
	}

	public String getCollectsNumber() {
		return collectsNumber;
	}

	public void setCollectsNumber(String collectsNumber) {
		this.collectsNumber = collectsNumber;
	}

	public boolean isLevelDiscount() {
		return isLevelDiscount;
	}

	public void setLevelDiscount(boolean isLevelDiscount) {
		this.isLevelDiscount = isLevelDiscount;
	}


	public String getShopCity() {
		return shopCity;
	}

	public void setShopCity(String shopCity) {
		this.shopCity = shopCity;
	}

	public String getDeliveryFree() {
		return deliveryFree;
	}

	public void setDeliveryFree(String deliveryFree) {
		this.deliveryFree = deliveryFree;
	}

	public String getShopPrice() {
		return shopPrice;
	}

	public void setShopPrice(String shopPrice) {
		this.shopPrice = shopPrice;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
}
