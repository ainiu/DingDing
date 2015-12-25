package com.ghsh.code.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品收藏
 * */
public class TCollect {

	private String goodsID;//商品ID
	
	private String goodsName;//商品名称
	
	private String goodsImage;//商品图片
	
	private String goodsPrice;//商品价格
	
	private Date addTime;//收藏时间
	
	private String keyword;//收藏关键字
	
	private String collectNumber;//总收藏数量

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

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	public String getCollectNumber() {
		return collectNumber;
	}

	public void setCollectNumber(String collectNumber) {
		this.collectNumber = collectNumber;
	}
}
