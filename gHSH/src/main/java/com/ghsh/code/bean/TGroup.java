package com.ghsh.code.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TGroup implements Serializable{
	private static final long serialVersionUID = 1L;

	private String groupID;
	
	private String goodsID;
	
	private String groupName;
	
	private String groupDesc;
	
	private String shopPrice;
	
	private String marketPrice;
	
	private String groupImage;
	
	private List<TGoodsAttr> goodsAttrList=new ArrayList<TGoodsAttr>();
	
	private List<TPic> picList=new ArrayList<TPic>();
	
	
	private long countdownTotalTime;//倒计时总时长
	
	private int countdownDay;//倒计时--天
	
	private int countdownHour;//倒计时--时
	
	private int countdownMin;//倒计时--分
	
	private int countdownSec;//倒计时--秒

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getShopPrice() {
		return shopPrice;
	}

	public void setShopPrice(String shopPrice) {
		this.shopPrice = shopPrice;
	}

	public String getGroupImage() {
		return groupImage;
	}

	public void setGroupImage(String groupImage) {
		this.groupImage = groupImage;
	}

	public int getCountdownDay() {
		return countdownDay;
	}

	public void setCountdownDay(int countdownDay) {
		this.countdownDay = countdownDay;
	}

	public int getCountdownHour() {
		return countdownHour;
	}

	public void setCountdownHour(int countdownHour) {
		this.countdownHour = countdownHour;
	}

	public int getCountdownMin() {
		return countdownMin;
	}

	public void setCountdownMin(int countdownMin) {
		this.countdownMin = countdownMin;
	}

	public int getCountdownSec() {
		return countdownSec;
	}

	public void setCountdownSec(int countdownSec) {
		this.countdownSec = countdownSec;
	}

	public long getCountdownTotalTime() {
		return countdownTotalTime;
	}

	public void setCountdownTotalTime(long countdownTotalTime) {
		this.countdownTotalTime = countdownTotalTime;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public List<TGoodsAttr> getGoodsAttrList() {
		return goodsAttrList;
	}

	public void setGoodsAttrList(List<TGoodsAttr> goodsAttrList) {
		this.goodsAttrList = goodsAttrList;
	}

	public List<TPic> getPicList() {
		return picList;
	}

	public void setPicList(List<TPic> picList) {
		this.picList = picList;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
}
