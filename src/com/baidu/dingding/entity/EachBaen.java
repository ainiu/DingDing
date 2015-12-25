package com.baidu.dingding.entity;

import java.io.Serializable;

public class EachBaen implements Serializable{
	String goodsId;
	String categoryName;
	String storeNumber;
	String originPlace;
	String logPath;
	String name;
	String freight;
	String primalPrice;
	String isRcommend;

	public EachBaen() {
	}

	public EachBaen(String goodsId, String categoryName, String storeNumber, String originPlace, String logPath, String name, String freight, String primalPrice, String isRcommend) {
		this.goodsId = goodsId;
		this.categoryName = categoryName;
		this.storeNumber = storeNumber;
		this.originPlace = originPlace;
		this.logPath = logPath;
		this.name = name;
		this.freight = freight;
		this.primalPrice = primalPrice;
		this.isRcommend = isRcommend;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "EachBaen [goodsId=" + goodsId + ", storeNumber=" + storeNumber
				+ ", originPlace=" + originPlace + ", logPath=" + logPath
				+ ", name=" + name + ", freight=" + freight + ", primalPrice="
				+ primalPrice + ", isRcommend=" + isRcommend + "]";
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getOriginPlace() {
		return originPlace;
	}

	public void setOriginPlace(String originPlace) {
		this.originPlace = originPlace;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getPrimalPrice() {
		return primalPrice;
	}

	public void setPrimalPrice(String primalPrice) {
		this.primalPrice = primalPrice;
	}

	public String getIsRcommend() {
		return isRcommend;
	}

	public void setIsRcommend(String isRcommend) {
		this.isRcommend = isRcommend;
	}

}
